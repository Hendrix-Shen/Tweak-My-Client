package top.hendrixshen.tweakmyclient.mixin.feature.autoReconnect;

import org.spongepowered.asm.mixin.Mixin;

//#if MC > 11902
//$$ import net.minecraft.client.gui.screens.ConnectScreen;
//$$ import net.minecraft.client.Minecraft;
//$$ import net.minecraft.client.gui.screens.Screen;
//$$ import net.minecraft.client.multiplayer.ServerData;
//$$ import net.minecraft.client.multiplayer.resolver.ServerAddress;
//$$ import org.spongepowered.asm.mixin.injection.At;
//$$ import org.spongepowered.asm.mixin.injection.Inject;
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//$$ import top.hendrixshen.tweakmyclient.config.Configs;
//$$ import top.hendrixshen.tweakmyclient.impl.feature.featureAutoReconnect.AutoReconnectUtil;
//#else
import top.hendrixshen.magiclib.api.preprocess.DummyClass;
//#endif

//#if MC > 11902
//$$ @Mixin(ConnectScreen.class)
//#else
@Mixin(DummyClass.class)
//#endif
public abstract class MixinConnectScreen {
    //#if MC > 11902
    //$$ @Inject(
    //$$         method = "startConnecting",
    //$$         at = @At(
    //$$                 value = "HEAD"
    //$$         )
    //$$ )
    //#if MC > 11904
    //$$ private static void onStartConnecting(Screen screen, Minecraft minecraft, ServerAddress serverAddress, ServerData serverData, boolean bl, CallbackInfo ci) {
    //$$     AutoReconnectUtil.setLastQuickPlay(bl);
    //#else
    //$$ private static void onStartConnecting(Screen screen, Minecraft minecraft, ServerAddress serverAddress, ServerData serverData, CallbackInfo ci) {
    //#endif
    //$$     AutoReconnectUtil.setLastServer(serverData);
    //$$ }
    //#endif
}
