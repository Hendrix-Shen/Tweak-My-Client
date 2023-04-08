package top.hendrixshen.tweakmyclient.mixin.feature.featureAutoReconnect;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
//#if MC < 11900
//$$ import net.minecraft.client.multiplayer.ServerData;
//$$ import org.spongepowered.asm.mixin.injection.At;
//$$ import org.spongepowered.asm.mixin.injection.Inject;
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//$$ import top.hendrixshen.tweakmyclient.config.Configs;
//$$ import top.hendrixshen.tweakmyclient.util.AutoReconnectUtil;
//#endif

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    //#if MC < 11900
    //$$ @Inject(
    //$$         method = "setCurrentServer",
    //$$         at = @At(
    //$$                 "HEAD"
    //$$         )
    //$$ )
    //$$ private void setCurrentServerEntry(ServerData serverData, CallbackInfo ci) {
    //$$     AutoReconnectUtil.setLastServer(serverData);
    //$$ }
    //#endif
}
