package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayFire;

import org.spongepowered.asm.mixin.Mixin;

//#if MC > 11404
import top.hendrixshen.magiclib.compat.preprocess.api.DummyClass;
//#else
//$$ import net.minecraft.client.renderer.ItemInHandRenderer;
//$$ import org.spongepowered.asm.mixin.injection.At;
//$$ import org.spongepowered.asm.mixin.injection.Inject;
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//$$ import top.hendrixshen.tweakmyclient.config.Configs;
//#endif

//#if MC > 11404
@Mixin(DummyClass.class)
//#else
//$$ @Mixin(ItemInHandRenderer.class)
//#endif
public class MixinItemInHandRenderer {
    //#if MC < 11500
    //$$ @Inject(
    //$$         method = "renderFire",
    //$$         at = @At(
    //$$                 value = "HEAD"
    //$$         ),
    //$$         cancellable = true
    //$$ )
    //$$ private void onRenderFireOverlay(CallbackInfo ci) {
    //$$     if (Configs.disableRenderOverlayFire) {
    //$$         ci.cancel();
    //$$     }
    //$$ }
    //#endif
}
