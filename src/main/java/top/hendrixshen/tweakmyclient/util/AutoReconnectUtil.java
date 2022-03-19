package top.hendrixshen.tweakmyclient.util;

import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import top.hendrixshen.tweakmyclient.TweakMyClient;

public class AutoReconnectUtil {
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
        ServerData serverInfo = AutoReconnectUtil.getLastServer();
        if (lastServer != null) {
            ConnectScreen.startConnecting(screen, TweakMyClient.getMinecraftClient(), ServerAddress.parseString(serverInfo.ip), serverInfo);
        }
    }

    public static String getTranslationKey(Component component) {
        return component instanceof TranslatableComponent ? ((TranslatableComponent) component).getKey() : "";
    }
}
