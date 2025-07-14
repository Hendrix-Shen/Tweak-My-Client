package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayFire;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.renderer.ItemInHandRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 : subproject 1.14.4        &lt;--------</li>
 * <li>mc1.15+: subproject 1.16.5 (main project) [dummy]</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(ItemInHandRenderer.class)
public abstract class MixinItemInHandRenderer {
    @Inject(method = "renderFire", at = @At("HEAD"), cancellable = true)
    private void onRenderFireOverlay(CallbackInfo ci) {
        if (Configs.disableFireOverlayRender.getBooleanValue()) {
            ci.cancel();
        }
    }
}
