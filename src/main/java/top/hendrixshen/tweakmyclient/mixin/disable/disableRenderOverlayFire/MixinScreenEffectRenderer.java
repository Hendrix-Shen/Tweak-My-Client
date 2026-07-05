package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayFire;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.renderer.ScreenEffectRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 : subproject 1.14.4 [dummy]</li>
 * <li>mc1.15+: subproject 1.16.5 (main project)        &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(ScreenEffectRenderer.class)
public abstract class MixinScreenEffectRenderer {
    @Inject(
            //#if MC >= 26.1
            //$$ method = "submitFire",
            //#else
            method = "renderFire",
            //#endif
            at = @At("HEAD"),
            cancellable = true
    )
    private static void onRenderFire(CallbackInfo ci) {
        if (Configs.disableFireOverlayRender.getBooleanValue()) {
            ci.cancel();
        }
    }
}
