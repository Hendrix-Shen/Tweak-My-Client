package top.hendrixshen.tweakmyclient.event;

import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.hotkeys.KeyCallbackToggleBoolean;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundChatPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import top.hendrixshen.magiclib.untils.language.I18n;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;
import top.hendrixshen.tweakmyclient.config.ConfigCategory;
import top.hendrixshen.tweakmyclient.config.ConfigGui;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.helper.TargetBlockPositionPrintMode;
import top.hendrixshen.tweakmyclient.util.CustomWindowUtil;
import top.hendrixshen.tweakmyclient.util.InfoUtil;
import top.hendrixshen.tweakmyclient.util.InventoryUtil;

import java.awt.*;

import static top.hendrixshen.tweakmyclient.TweakMyClient.cm;

public class CallBacks {
    public static boolean getTargetBlockPositionCallback(KeyAction keyAction, IKeybind keybind) {
        Minecraft minecraft = TweakMyClient.getMinecraftClient();
        Entity cameraEntity = minecraft.cameraEntity;
        MultiPlayerGameMode multiPlayerGameMode = minecraft.gameMode;
        if (cameraEntity != null && multiPlayerGameMode != null) {
            HitResult hitResult = cameraEntity.pick(Configs.targetBlockMaxTraceDistance.getDoubleValue(), minecraft.getFrameTime(), false);
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
                String str = Configs.targetBlockPositionFormat.getStringValue();
                str = str.replace("{X}", String.format("%d", blockPos.getX()));
                str = str.replace("{Y}", String.format("%d", blockPos.getY()));
                str = str.replace("{Z}", String.format("%d", blockPos.getZ()));
                if (minecraft.player != null) {
                    switch ((TargetBlockPositionPrintMode) Configs.targetBlockPositionPrintMode.getOptionListValue()) {
                        case PUBLIC:
                            minecraft.player.chat(str);
                            break;
                        case PRIVATE:
                            minecraft.player.connection.handleChat(new ClientboundChatPacket(new TextComponent(str), ChatType.CHAT, minecraft.player.getUUID()));
                            break;
                    }
                }
            }
        }
        return true;
    }

    public static boolean memoryCleanerCallback(KeyAction keyAction, IKeybind keybind) {
        class CleanerThread implements Runnable {
            public CleanerThread() {
            }

            @Override
            public void run() {
                TweakMyClient.getLogger().info(String.format("[%s]: Memory cleaner thread started!", TweakMyClientReference.getModName()));
                System.gc();
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException interruptedException) {
                    // ignored
                }
                System.gc();
                TweakMyClient.getLogger().info(String.format("[%s]: Memory cleaner thread finished!", TweakMyClientReference.getModName()));
            }
        }
        Runnable runnable = new CleanerThread();
        Thread gcThread = new Thread(runnable, "MemoryCleaner GC Thread");
        gcThread.setDaemon(true);
        gcThread.start();
        return true;
    }

    public static void debugModeCallBack(ConfigBoolean configBoolean) {
        cm.setHideDebug(!configBoolean.getBooleanValue());
        ConfigGui.getInstance().reDraw();
    }

    public static void displayDevelopmentOnlyConfigCallBack(ConfigBoolean configBoolean) {
        cm.setHideDevOnly(!configBoolean.getBooleanValue());
        ConfigGui.getInstance().reDraw();
    }

    public static void displayDisabledConfigConfigCallBack(ConfigBoolean configBoolean) {
        cm.setHideDisabled(!configBoolean.getBooleanValue());
        ConfigGui.getInstance().reDraw();
    }

    public static void displayUnSupportMinecraftConfigCallBack(ConfigBoolean configBoolean) {
        cm.setHideUnmatchedMinecraftVersion(!configBoolean.getBooleanValue());
        ConfigGui.getInstance().reDraw();
    }

    public static boolean syncInventoryCallback(KeyAction keyAction, IKeybind keybind) {
        InventoryUtil.refreshInventory();
        return true;
    }

    public static boolean syncBlocksCallback(KeyAction keyAction, IKeybind keybind) {
        Minecraft minecraftClient = TweakMyClient.getMinecraftClient();
        ClientPacketListener clientPlayNetworkHandler = minecraftClient.getConnection();
        assert minecraftClient.player != null;
        assert clientPlayNetworkHandler != null;
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

    public static class KeyCallbackToggleBooleanConfigWithMessage extends KeyCallbackToggleBoolean {
        private final String translatedName;

        public KeyCallbackToggleBooleanConfigWithMessage(IConfigBoolean config, String translatedName) {
            super(config);
            this.translatedName = translatedName;
        }

        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key) {
            super.onKeyAction(action, key);
            InfoUtil.printBooleanConfigToggleMessage(translatedName, this.config.getBooleanValue());
            return true;
        }

    }

    public static void disableRenderToastCallback(ConfigBoolean configBoolean) {
        if (configBoolean.getBooleanValue()) {
            TweakMyClient.getMinecraftClient().getToasts().clear();
        }
    }

    public static void featureCustomWindowTitleCallback(ConfigBoolean configBoolean) {
        CustomWindowUtil.rebuildCache(CustomWindowUtil.TitleType.TITLE);
        CustomWindowUtil.rebuildCache(CustomWindowUtil.TitleType.TITLE_WITH_ACTIVITY);
        if (!configBoolean.getBooleanValue()) {
            TweakMyClient.getMinecraftClient().updateTitle();
        }
    }

    public static boolean openConfigGuiCallback(KeyAction keyAction, IKeybind keybind) {
        GuiBase.openGui(new ConfigGui(TweakMyClientReference.getModId(), ConfigCategory.GENERIC, cm));
        return true;
    }
}
