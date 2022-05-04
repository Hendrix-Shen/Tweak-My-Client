package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayFire;

//#if MC >= 11500
import net.minecraft.client.Minecraft;
//#else
//$$ import net.minecraft.client.renderer.ItemInHandRenderer;
//#endif
import org.spongepowered.asm.mixin.Mixin;
//#if MC < 11500
//$$ import org.spongepowered.asm.mixin.injection.At;
//$$ import org.spongepowered.asm.mixin.injection.Inject;
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//$$ import top.hendrixshen.tweakmyclient.config.Configs;
//#endif

//#if MC >= 11500
@Mixin(Minecraft.class)
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
