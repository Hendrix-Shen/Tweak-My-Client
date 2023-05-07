package top.hendrixshen.tweakmyclient.event;

import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import sun.misc.Unsafe;
import top.hendrixshen.magiclib.compat.minecraft.api.network.chat.ComponentCompatApi;
import top.hendrixshen.magiclib.malilib.impl.ConfigOption;
import top.hendrixshen.magiclib.util.InfoUtil;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.TweakMyClientConfigGui;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.helper.BreakAnimationMode;
import top.hendrixshen.tweakmyclient.util.CustomWindowUtil;
import top.hendrixshen.tweakmyclient.util.InventoryUtil;

import java.lang.reflect.Field;

public class CallBacks {
    private static Unsafe UNSAFE = null;

    static {
        try {
            Class<?> unsafe = Class.forName("sun.misc.Unsafe");
            Field field = unsafe.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            CallBacks.UNSAFE = (Unsafe) field.get(null);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            TweakMyClientReference.getLogger().error("Cannot access unsafe class, disabled some feature!");
        }
    }

    public static boolean getTargetBlockPositionCallback(KeyAction keyAction, IKeybind keybind) {
        Minecraft minecraft = TweakMyClient.getMinecraftClient();
        Entity cameraEntity = minecraft.cameraEntity;
        MultiPlayerGameMode multiPlayerGameMode = minecraft.gameMode;

        if (cameraEntity != null && multiPlayerGameMode != null) {
            HitResult hitResult = cameraEntity.pick(Configs.targetBlockMaxTraceDistance, minecraft.getFrameTime(), false);

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
                String str = Configs.targetBlockPositionFormat;
                str = str.replace("{X}", String.format("%d", blockPos.getX()));
                str = str.replace("{Y}", String.format("%d", blockPos.getY()));
                str = str.replace("{Z}", String.format("%d", blockPos.getZ()));

                if (minecraft.player != null) {
                    switch (Configs.targetBlockPositionPrintMode) {
                        case PUBLIC:
                            InfoUtil.sendChat(str);
                            break;
                        case PRIVATE:
                            InfoUtil.displayChatMessage(ComponentCompatApi.literal(str));
                            break;
                    }
                }
            }
        }
        return true;
    }

    public static boolean memoryCleanerCallback(KeyAction keyAction, IKeybind keybind) {
        class CleanerThread implements Runnable {
            @Override
            public void run() {
                TweakMyClientReference.getLogger().info(String.format("[%s]: Memory cleaner thread started!", TweakMyClientReference.getModName()));
                System.gc();

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException ignore) {
                }

                System.gc();
                TweakMyClientReference.getLogger().info(String.format("[%s]: Memory cleaner thread finished!", TweakMyClientReference.getModName()));
            }
        }

        Runnable runnable = new CleanerThread();
        Thread gcThread = new Thread(runnable, "MemoryCleaner GC Thread");
        gcThread.setDaemon(true);
        gcThread.start();
        return true;
    }

    public static void debugExperimentalModeCallBack(ConfigOption option) {
        reDrawConfigGui(option);
    }

    public static void debugModeCallBack(ConfigOption option) {
        Configurator.setLevel(TweakMyClientReference.getModIdentifier(), Configs.debugMode ? Level.DEBUG : Level.INFO);
        reDrawConfigGui(option);
    }

    public static boolean syncInventoryCallback(KeyAction keyAction, IKeybind keybind) {
        InventoryUtil.refreshInventory();
        return true;
    }

    public static boolean syncBlocksCallback(KeyAction keyAction, IKeybind keybind) {
        Minecraft minecraftClient = TweakMyClient.getMinecraftClient();
        ClientPacketListener clientPlayNetworkHandler = minecraftClient.getConnection();

        if (minecraftClient.player == null || clientPlayNetworkHandler == null) {
            return true;
        }

        BlockPos blockPos = minecraftClient.player.blockPosition();
        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();

        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                for (int k = -3; k <= 3; k++) {
                    clientPlayNetworkHandler.send(new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.ABORT_DESTROY_BLOCK, new BlockPos(x + i, y + j, z + k), Direction.UP));
                }
            }
        }

        return true;
    }

    public static void disableRenderToastCallback(ConfigOption option) {
        if (Configs.disableRenderToast) {
            TweakMyClient.getMinecraftClient().getToasts().clear();
        }
    }

    public static void featureCustomBlockHitBoxOverlayFillCallBack(ConfigOption option) {
        if (!(Configs.featureCustomBlockHitBoxOverlayFill && Configs.featureCustomBlockHitBoxOverlayOutline)) {
            TweakMyClientReference.getConfigHandler().configManager.setValue("breakAnimationMode", BreakAnimationMode.NONE);
            reDrawConfigGui(option);
        }
    }

    public static void featureCustomBlockHitBoxOverlayOutlineCallBack(ConfigOption option) {
        if (!(Configs.featureCustomBlockHitBoxOverlayFill && Configs.featureCustomBlockHitBoxOverlayOutline)) {
            TweakMyClientReference.getConfigHandler().configManager.setValue("breakAnimationMode", BreakAnimationMode.NONE);
            reDrawConfigGui(option);
        }
    }

    public static void featureCustomWindowTitleCallback(ConfigOption option) {
        if (Configs.featureCustomWindowTitle) {
            //#if MC >= 11500
            CustomWindowUtil.rebuildCache(CustomWindowUtil.TitleType.TITLE);
            CustomWindowUtil.rebuildCache(CustomWindowUtil.TitleType.TITLE_WITH_ACTIVITY);
            //#else
            //$$ CustomWindowUtil.rebuildCache();
            //#endif
        } else {
            CustomWindowUtil.reSetTitle();
        }
    }

    public static boolean openConfigGuiCallback(KeyAction keyAction, IKeybind keybind) {
        TweakMyClientConfigGui tweakMyClientConfigGui = TweakMyClientConfigGui.getInstance();
        //#if MC > 11903 && MC < 12000
        tweakMyClientConfigGui.setParent(TweakMyClient.getMinecraftClient().screen);
        //#else
        //$$ tweakMyClientConfigGui.setParentGui(TweakMyClient.getMinecraftClient().screen);
        //#endif
        TweakMyClient.getMinecraftClient().setScreen(tweakMyClientConfigGui);
        return true;
    }

    private static void reDrawConfigGui(ConfigOption option) {
        if (option != null) {
            TweakMyClientConfigGui.getInstance().reDraw();
        }
    }

    public static void customWindowTitleEnableActivityCallback(ConfigOption option) {
        CallBacks.featureCustomWindowTitleCallback(option);
        CallBacks.reDrawConfigGui(option);
    }

    public static boolean expNullPointerExceptionTestCallback(KeyAction keyAction, IKeybind iKeybind) {
        if (Configs.debugMode && Configs.debugExperimentalMode) {
            throw new NullPointerException("Test NullPointerException!");
        }

        return true;
    }

    public static boolean expUnsafeIllegalAllocateTestCallback(KeyAction keyAction, IKeybind iKeybind) {
        if (Configs.debugMode && Configs.debugExperimentalMode && CallBacks.UNSAFE != null) {
            CallBacks.UNSAFE.putAddress(0, 0);
        }

        return true;
    }
}
