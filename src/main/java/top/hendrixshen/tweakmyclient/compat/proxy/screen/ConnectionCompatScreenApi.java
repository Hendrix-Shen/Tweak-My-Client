package top.hendrixshen.tweakmyclient.compat.proxy.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;

public abstract class ConnectionCompatScreenApi {
    protected static ConnectionCompatScreenApi INSTANCE;

    public static ConnectionCompatScreenApi getInstance() {
        return INSTANCE;
    }

    public abstract void startConnect(Screen parent, Minecraft minecraft, ServerData serverData);
}
