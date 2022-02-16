package top.hendrixshen.TweakMyClient.mixin.feature.featureCustomWindowTitle;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.util.CustomWindowUtils;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow
    public abstract void updateTitle();

    @Shadow
    private static int fps;

    @Inject(
            method = "createTitle",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onCreateTitle(CallbackInfoReturnable<String> cir) {
        if (Configs.Feature.FEATURE_CUSTOM_WINDOW_TITLE.getBooleanValue()) {
            cir.setReturnValue(CustomWindowUtils.getWindowTitle());
        }
    }

    @Inject(
            method = "run",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;runTick(Z)V"
            )
    )
    private void onRunTick(CallbackInfo ci) {
        if (Configs.Feature.FEATURE_CUSTOM_WINDOW_TITLE.getBooleanValue()) {
            CustomWindowUtils.updatePlaceholders();
            this.updateTitle();
        }
    }

    // Expose FPS data
    @Inject(
            method = "runTick",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/Util;getMillis()J"
                    ),
                    to = @At(
                            value = "INVOKE",
                            target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                            remap = false
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;"
            )
    )
    private void afterCalculateFPS(boolean bl, CallbackInfo ci) {
        CustomWindowUtils.updateFPS(fps);
    }
}
