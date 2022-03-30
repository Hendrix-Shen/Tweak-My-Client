package top.hendrixshen.tweakmyclient.mixin.feature.featureUnfocusedCPU;

import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Inject(
            method = "render",
            at = @At(
                    "HEAD"
            ),
            cancellable = true
    )
    private void onRenderHead(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        if (Configs.featureUnfocusedCPU && !TweakMyClient.getMinecraftClient().isWindowActive()) {
            ci.cancel();
        }
    }
}
