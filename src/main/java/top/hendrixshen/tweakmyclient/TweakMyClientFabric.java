//#if FABRIC

package top.hendrixshen.tweakmyclient;

import net.fabricmc.api.ClientModInitializer;

public class TweakMyClientFabric implements ClientModInitializer {
    private static final TweakMyClientFabric instance = new TweakMyClientFabric();

    @Override
    public void onInitializeClient() {
        TweakMyClient.getInstance().onInitializeClient();
    }
}
//#endif
