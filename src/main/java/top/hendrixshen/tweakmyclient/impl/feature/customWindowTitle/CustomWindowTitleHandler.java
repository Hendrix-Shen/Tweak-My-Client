package top.hendrixshen.tweakmyclient.impl.feature.customWindowTitle;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.lwjgl.glfw.GLFW;
import top.hendrixshen.magiclib.api.compat.minecraft.client.MinecraftCompat;
import top.hendrixshen.magiclib.api.event.minecraft.MinecraftListener;
import top.hendrixshen.magiclib.api.i18n.I18n;
import top.hendrixshen.tweakmyclient.game.Configs;
import top.hendrixshen.tweakmyclient.util.CollectionUtil;

import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;

import net.fabricmc.loader.impl.FabricLoaderImpl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Supplier;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CustomWindowTitleHandler implements MinecraftListener {
    @Getter(lazy = true)
    private static final CustomWindowTitleHandler instance = new CustomWindowTitleHandler();

    private final Map<String, String> PLACEHOLDER_MAP = ImmutableMap.<String, String>builder()
            .put("{fabric_loader_version}", FabricLoaderImpl.VERSION)
            .put("{fabric_loader_asm_version}", String.valueOf(FabricLoaderImpl.ASM_VERSION))
            .put("{mc_protocol_version}", Integer.toString(
                    //#if MC > 11502
                    SharedConstants.getProtocolVersion()
                    //#else
                    //$$ SharedConstants.getCurrentVersion().getProtocolVersion()
                    //#endif
            ))
            .put("{mc_version}", SharedConstants.getCurrentVersion()
                            //#if MC >= 12106
                            //$$ .name()
                            //#else
                            .getName()
                    //#endif
            )
            .put("{tmc_version}", top.hendrixshen.tweakmyclient.SharedConstants.getModVersion())
            .put("{tmc_version_type}", top.hendrixshen.tweakmyclient.SharedConstants.getModVersionType())
            .build();
    private final Map<String, Supplier<String>> PLACEHOLDER_MAP_DYNAMIC = ImmutableMap.<String, Supplier<String>>builder()
            // Generally this should not be changed,
            // with the introduction of IAS or similar modules,
            // there is a potential for usernames to be changed.
            .put("{mc_username}", () -> Minecraft.getInstance().getUser().getName())
            .put("{mc_activity}", this::getCurrentActivity)
            .put("{mc_activity_origin}", this::getCurrentActivityOrigin)
            .put("{mc_fps}", () -> String.valueOf(this.fps))
            .build();
    private final Minecraft mc = Minecraft.getInstance();
    private final TitleChanger titleChanger = new TitleChanger();
    private Thread workThread = null;
    private volatile boolean shouldChangerRun = false;
    private volatile String currentTitle = "";
    private volatile String currentTitleWithActivity = "";
    private int fps = 0;

    private void startTitleChangerThread() {
        if (this.workThread == null || !this.workThread.isAlive()) {
            this.workThread = new Thread(this.titleChanger);
            this.workThread.start();
        }
    }

    private void stopTitleChangerThread() {
        if (this.workThread != null && this.workThread.isAlive()) {
            this.titleChanger.stop();
            this.workThread.interrupt();
        }
    }

    private void updateTitleFromConfig() {
        if (Configs.customWindowTitleRandomly.getBooleanValue()) {
            this.currentTitle = CollectionUtil.selectRandom(Configs.customWindowTitleList.getStrings(), "");
            this.currentTitleWithActivity = CollectionUtil.selectRandom(Configs.customWindowTitleWithActivityList.getStrings(), "");
        } else {
            this.currentTitle = CollectionUtil.selectFirst(Configs.customWindowTitleList.getStrings(), "");
            this.currentTitleWithActivity = CollectionUtil.selectFirst(Configs.customWindowTitleWithActivityList.getStrings(), "");
        }

        String currentTitleTemp = this.currentTitle;
        String currentTitleWithActivityTemp = this.currentTitleWithActivity;

        for (Entry<String, String> entry : this.PLACEHOLDER_MAP.entrySet()) {
            currentTitleTemp = currentTitleTemp.replace(entry.getKey(), entry.getValue());
            currentTitleWithActivityTemp = currentTitleWithActivityTemp.replace(entry.getKey(), entry.getValue());
        }

        this.currentTitle = currentTitleTemp;
        this.currentTitleWithActivity = currentTitleWithActivityTemp;
    }

    public void onConfigUpdate() {
        this.updateTitleFromConfig();
    }

    public void updateFps(int fps) {
        this.fps = fps;
    }

    public String getCurrentTitle() {
        if (this.hasActivity()) {
            return this.currentTitleWithActivity;
        } else {
            return this.currentTitle;
        }
    }

    private String getCurrentActivityCode() {
        if (this.mc.getSingleplayerServer() != null && !this.mc.getSingleplayerServer().isPublished()) {
            return "title.singleplayer";
        } else if (
                //#if MC > 12001
                //$$ this.mc.getCurrentServer() != null && this.mc.getCurrentServer().isRealm()
                //#else
                this.mc.isConnectedToRealms()
                //#endif
        ) {
            return "title.multiplayer.realms";
        } else if (this.mc.getSingleplayerServer() == null && (this.mc.getCurrentServer() == null || !this.mc.getCurrentServer().isLan())) {
            return "title.multiplayer.other";
        } else {
            return "title.multiplayer.lan";
        }
    }

    private String getCurrentActivity() {
        return I18n.tr(this.getCurrentActivityCode());
    }

    private String getCurrentActivityOrigin() {
        return I18n.trByCode(I18n.DEFAULT_CODE, this.getCurrentActivityCode());
    }

    private boolean hasActivity() {
        ClientPacketListener clientPacketListener = this.mc.getConnection();
        return Configs.customWindowTitleActivitySupport.getBooleanValue() && clientPacketListener != null && clientPacketListener.getConnection().isConnected();
    }

    public void checkThread() {
        this.shouldChangerRun = Configs.customWindowTitle.getBooleanValue();

        if (this.shouldChangerRun) {
            this.startTitleChangerThread();
        } else {
            this.stopTitleChangerThread();
            //#if MC > 11404
            this.mc.updateTitle();
            //#else
            //$$ GLFW.glfwSetWindowTitle(MinecraftCompat.getInstance().getWindow().getWindow(), "Minecraft " + SharedConstants.getCurrentVersion().getName());
            //#endif
        }
    }

    @Override
    public void postInit() {
        this.checkThread();
    }

    private static class TitleChanger implements Runnable {
        public AtomicBoolean running = new AtomicBoolean(false);
        private long nextTickTime = 0;

        @Override
        public void run() {
            this.running.set(true);
            this.nextTickTime = System.currentTimeMillis();

            while (this.running.get()) {
                this.nextTickTime += 1000L;

                try {
                    CustomWindowTitleHandler.getInstance().getCurrentTitle();
                    GLFW.glfwSetWindowTitle(MinecraftCompat.getInstance().getWindow().getWindow(), this.getRealtimeTitle());
                    this.waitUntilNextTick();
                } catch (Exception e) {
                    top.hendrixshen.tweakmyclient.SharedConstants.getLogger().error("Exception in title changer", e);
                    this.stop();
                }
            }
        }

        public void stop() {
            this.running.set(false);
        }

        private String getRealtimeTitle() {
            String title = CustomWindowTitleHandler.getInstance().getCurrentTitle();

            for (Entry<String, Supplier<String>> entry : CustomWindowTitleHandler.getInstance().PLACEHOLDER_MAP_DYNAMIC.entrySet()) {
                title = title.replace(entry.getKey(), entry.getValue().get());
            }

            return title.replace("{mc_fps}", String.valueOf(CustomWindowTitleHandler.getInstance().fps));
        }

        private void waitUntilNextTick() {
            while (System.currentTimeMillis() < this.nextTickTime) {
                Thread.yield();
                LockSupport.parkNanos(100_000L);
            }
        }
    }
}
