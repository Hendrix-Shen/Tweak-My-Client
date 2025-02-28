package top.hendrixshen.tweakmyclient;

import net.fabricmc.api.ClientModInitializer;
import top.hendrixshen.tweakmyclient.game.MalilibStuffsInitializer;

public class TweakMyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MalilibStuffsInitializer.init();
    }
}
