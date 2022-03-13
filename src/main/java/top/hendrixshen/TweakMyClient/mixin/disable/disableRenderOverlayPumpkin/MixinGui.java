package top.hendrixshen.TweakMyClient.mixin.disable.disableRenderOverlayPumpkin;

import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(Gui.class)
public abstract class MixinGui {
    @Shadow
    @Final
    private static ResourceLocation PUMPKIN_BLUR_LOCATION;

    @Inject(
            method = "renderTextureOverlay",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onRenderPumpkinOverlay(ResourceLocation resourceLocation, float f, CallbackInfo ci) {
        if (Configs.Disable.DISABLE_RENDER_OVERLAY_PUMPKIN.getBooleanValue() && resourceLocation == PUMPKIN_BLUR_LOCATION) {
            ci.cancel();
        }
    }
}
