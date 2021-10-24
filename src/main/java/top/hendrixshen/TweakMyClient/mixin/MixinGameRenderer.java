package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import top.hendrixshen.TweakMyClient.TweakMyClient;
import top.hendrixshen.TweakMyClient.config.Configs;

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
        if (Configs.Feature.FEATURE_UNFOCUSED_CPU.getBooleanValue() && !TweakMyClient.getMinecraftClient().isWindowActive()) {
            ci.cancel();
        }
    }

    @ModifyArgs(
            method = "getFov",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Mth;lerp(FFF)F"
            )
    )
    private void onGetFovFov(Args args) {
        if (Configs.Disable.DISABLE_FOV_AFFECTED_BY_SPEED.getBooleanValue()) {
            args.set(1, 1.0F);
            args.set(2, 1.0F);
        }
    }
}
