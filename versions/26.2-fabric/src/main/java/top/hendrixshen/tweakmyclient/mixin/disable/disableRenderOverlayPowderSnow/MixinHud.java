package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayPowderSnow;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.gui.Hud;
import net.minecraft.resources.Identifier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.sugar.Local;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc26.1: subproject 1.16.5 (main project) [dummy]</li>
 * <li>mc26.2+        : subproject 26.2       &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(Hud.class)
public abstract class MixinHud {
    @Shadow
    @Final
    private static Identifier POWDER_SNOW_OUTLINE_LOCATION;

    @Inject(
            method = "extractTextureOverlay",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void onRenderPowderSnowOverlay(CallbackInfo ci, @Local(argsOnly = true) Identifier identifier) {
        if (Configs.disablePowderSnowOverlayRender.getBooleanValue()
                && identifier.equals(MixinHud.POWDER_SNOW_OUTLINE_LOCATION)) {
            ci.cancel();
        }
    }
}
