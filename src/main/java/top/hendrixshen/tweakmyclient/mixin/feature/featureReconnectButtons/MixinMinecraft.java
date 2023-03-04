package top.hendrixshen.tweakmyclient.mixin.feature.featureReconnectButtons;

import net.minecraft.client.Minecraft;
//#if MC <= 11802
//$$ import net.minecraft.client.multiplayer.ServerData;
//#endif
import org.spongepowered.asm.mixin.Mixin;
//#if MC <= 11802
//$$ import org.spongepowered.asm.mixin.injection.At;
//$$ import org.spongepowered.asm.mixin.injection.Inject;
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//$$ import top.hendrixshen.tweakmyclient.config.Configs;
//$$ import top.hendrixshen.tweakmyclient.util.AutoReconnectUtil;
//#endif

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    //#if MC <= 11802
    //$$ @Inject(
    //$$         method = "setCurrentServer",
    //$$         at = @At(
    //$$                 "HEAD"
    //$$         )
    //$$ )
    //$$ private void setCurrentServerEntry(ServerData serverData, CallbackInfo ci) {
    //$$     AutoReconnectUtil.reconnectTimer = Configs.autoReconnectTimer * 20;
    //$$     if (serverData != null) {
    //$$         AutoReconnectUtil.setLastServer(serverData);
    //$$    }
    //$$ }
    //#endif
}
