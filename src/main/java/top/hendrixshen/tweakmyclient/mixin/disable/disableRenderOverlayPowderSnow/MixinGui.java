package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayPowderSnow;

//#if MC < 11700
//$$ import net.minecraft.client.Minecraft;
//#endif
//#if MC >= 11700
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
//#endif
import org.spongepowered.asm.mixin.Mixin;
//#if MC >= 11700
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(Gui.class)
//#else
//$$ @Mixin(Minecraft.class)
//#endif
public abstract class MixinGui {
    //#if MC >= 11700
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
        if (Configs.disableRenderOverlayPowderSnow && resourceLocation.equals(POWDER_SNOW_OUTLINE_LOCATION)) {
            ci.cancel();
        }
    }
    //#endif
}
