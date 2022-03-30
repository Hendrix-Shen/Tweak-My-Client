package top.hendrixshen.tweakmyclient.compat.proxy.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import top.hendrixshen.tweakmyclient.TweakMyClient;

public class ConnectionCompatScreenImpl extends ConnectionCompatScreenApi {
    public static void initCompat() {
        INSTANCE = new ConnectionCompatScreenImpl();
    }

    @Override
    public void startConnect(Screen parent, Minecraft minecraft, ServerData serverData) {
        minecraft.setScreen(new ConnectScreen(parent, TweakMyClient.getMinecraftClient(), serverData));
    }
}
