package top.hendrixshen.TweakMyClient.util;

import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import top.hendrixshen.TweakMyClient.TweakMyClient;

public class AutoReconnectUtils {
    public static int ReconnectTimer;
    public static int reAuthenticateButtonOffsetY;
    private static ServerData lastServer;

    public static ServerData getLastServer() {
        return lastServer;
    }

    public static void setLastServer(ServerData serverInfo) {
        lastServer = serverInfo;
    }

    public static void reconnect(Screen screen) {
        ServerData serverInfo = AutoReconnectUtils.getLastServer();
        if (lastServer != null) {
            TweakMyClient.getMinecraftClient().setScreen(new ConnectScreen(screen, TweakMyClient.getMinecraftClient(), serverInfo));
        }
    }

    public static String getTranslationKey(Component component) {
        return component instanceof TranslatableComponent ? ((TranslatableComponent) component).getKey() : "";
    }
}
