package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayFire;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.renderer.ScreenEffectRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenEffectRenderer.class)
public abstract class MixinScreenEffectRenderer {
    @Inject(method = "renderFire", at = @At("HEAD"), cancellable = true)
    private static void onRenderFire(CallbackInfo ci) {
        if (Configs.disableFireOverlayRender.getBooleanValue()) {
            ci.cancel();
        }
    }
}
