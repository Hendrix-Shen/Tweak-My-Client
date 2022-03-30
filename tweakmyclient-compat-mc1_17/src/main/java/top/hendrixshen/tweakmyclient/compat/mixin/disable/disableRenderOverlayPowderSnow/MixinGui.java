package top.hendrixshen.tweakmyclient.compat.mixin.disable.disableRenderOverlayPowderSnow;

import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(Gui.class)
public abstract class MixinGui {
    @Shadow
    @Final
    private static ResourceLocation POWDER_SNOW_OUTLINE_LOCATION;

    @Inject(
            method = "renderTextureOverlay",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onRenderPowderSnowOverlay(ResourceLocation resourceLocation, float f, CallbackInfo ci) {
        if (Configs.disableRenderOverlayPowderSnow && resourceLocation == POWDER_SNOW_OUTLINE_LOCATION) {
            ci.cancel();
        }
    }
}
