package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayPumpkin;

import top.hendrixshen.magiclib.api.compat.minecraft.resources.ResourceLocationCompat;
import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.gui.Hud;
import net.minecraft.resources.Identifier;

import org.spongepowered.asm.mixin.Mixin;
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
    @Inject(method = "extractTextureOverlay(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/resources/Identifier;F)V", at = @At("HEAD"), cancellable = true)
    private void onRenderPumpkinOverlay(CallbackInfo ci, @Local(argsOnly = true) Identifier identifier) {
        if (Configs.disablePumpkinOverlayRender.getBooleanValue()
                && identifier.equals(ResourceLocationCompat.withDefaultNamespace("misc/pumpkinblur"))) {
            ci.cancel();
        }
    }
}
