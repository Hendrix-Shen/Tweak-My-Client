package top.hendrixshen.TweakMyClient.util;

import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import top.hendrixshen.TweakMyClient.TweakMyClient;

public class AutoReconnectUtils {
    public static int ReconnectTimer;
    private static ServerInfo lastServer;
    public static int reAuthenticateButtonOffsetY;

    public static ServerInfo getLastServer() {
        return lastServer;
    }

    public static void setLastServer(ServerInfo serverInfo) {
        lastServer = serverInfo;
    }

    public static void reconnect(Screen screen) {
        ServerInfo serverInfo = AutoReconnectUtils.getLastServer();
        if (lastServer != null) {
            ConnectScreen.connect(screen, TweakMyClient.minecraftClient, ServerAddress.parse(serverInfo.address), serverInfo);
        }
    }

    public static String getTranslationKey(Text component) {
        return component instanceof TranslatableText ? ((TranslatableText) component).getKey() : "";
    }
}
