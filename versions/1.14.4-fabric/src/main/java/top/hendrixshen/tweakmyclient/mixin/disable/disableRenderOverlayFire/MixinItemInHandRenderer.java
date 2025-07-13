package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayFire;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.renderer.ItemInHandRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class MixinItemInHandRenderer {
    @Inject(method = "renderFire", at = @At("HEAD"), cancellable = true)
    private void onRenderFireOverlay(CallbackInfo ci) {
        if (Configs.disableFireOverlayRender.getBooleanValue()) {
            ci.cancel();
        }
    }
}
