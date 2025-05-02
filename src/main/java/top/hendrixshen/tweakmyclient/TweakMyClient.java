package top.hendrixshen.tweakmyclient;

import net.fabricmc.api.ClientModInitializer;
import top.hendrixshen.magiclib.MagicLib;
import top.hendrixshen.magiclib.api.event.minecraft.MinecraftListener;
import top.hendrixshen.tweakmyclient.game.MalilibStuffsInitializer;
import top.hendrixshen.tweakmyclient.impl.feature.customWindowTitle.CustomWindowTitleHandler;

public class TweakMyClient implements ClientModInitializer, MinecraftListener {
    private static final TweakMyClient instance = new TweakMyClient();

    @Override
    public void onInitializeClient() {
        MagicLib.getInstance().getEventManager().register(MinecraftListener.class, TweakMyClient.instance);
        MalilibStuffsInitializer.init();
    }

    @Override
    public void postInit() {
        CustomWindowTitleHandler.getInstance().onConfigUpdate();
    }
}
