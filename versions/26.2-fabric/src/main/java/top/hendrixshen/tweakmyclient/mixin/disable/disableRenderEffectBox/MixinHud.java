package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderEffectBox;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.gui.Hud;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc26.1: subproject 1.16.5 (main project) [dummy]</li>
 * <li>mc26.2+        : subproject 26.2        &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(Hud.class)
public abstract class MixinHud {
    @Inject(method = "extractEffects", at = @At("HEAD"), cancellable = true)
    private void onRender(CallbackInfo ci) {
        if (Configs.disableEffectBoxRender.getBooleanValue()) {
            ci.cancel();
        }
    }
}
