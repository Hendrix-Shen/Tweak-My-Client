package top.hendrixshen.tweakmyclient;

import lombok.Getter;
import top.hendrixshen.magiclib.MagicLib;
import top.hendrixshen.magiclib.api.event.minecraft.MinecraftListener;
import top.hendrixshen.tweakmyclient.game.MalilibStuffsInitializer;
import top.hendrixshen.tweakmyclient.impl.feature.customWindowTitle.CustomWindowTitleHandler;

public class TweakMyClient implements MinecraftListener {
    @Getter
    private static final TweakMyClient instance = new TweakMyClient();

    public void onInitializeClient() {
        MagicLib.getInstance().getEventManager().register(MinecraftListener.class, TweakMyClient.instance);
        MalilibStuffsInitializer.init();
    }

    @Override
    public void postInit() {
        CustomWindowTitleHandler.getInstance().onConfigUpdate();
    }
}
