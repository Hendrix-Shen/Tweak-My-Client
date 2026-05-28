package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayPowderSnow;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.sugar.Local;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc1.16: subproject 1.16.5 (main project) [dummy]</li>
 * <li>mc1.17+        : subproject 1.17.1        &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(Gui.class)
public abstract class MixinGui {
    @Shadow
    @Final
    private static ResourceLocation POWDER_SNOW_OUTLINE_LOCATION;

    @Inject(
            //#if MC >= 26.1
            //$$ method = "extractTextureOverlay",
            //#else
            method = "renderTextureOverlay",
            //#endif
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void onRenderPowderSnowOverlay(CallbackInfo ci, @Local(argsOnly = true) ResourceLocation resourceLocation) {
        if (Configs.disablePowderSnowOverlayRender.getBooleanValue()
                && resourceLocation.equals(MixinGui.POWDER_SNOW_OUTLINE_LOCATION)) {
            ci.cancel();
        }
    }
}
