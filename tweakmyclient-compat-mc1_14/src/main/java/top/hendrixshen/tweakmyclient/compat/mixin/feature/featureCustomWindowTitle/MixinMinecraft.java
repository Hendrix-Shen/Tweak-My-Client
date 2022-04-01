package top.hendrixshen.tweakmyclient.compat.mixin.feature.featureCustomWindowTitle;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.CustomWindowUtil;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow
    private static int fps;

    @Inject(
            method = "run",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;init()V",
                    shift = At.Shift.AFTER
            )
    )
    private void afterInit(CallbackInfo ci) {
        if (Configs.featureCustomWindowTitle) {
            CustomWindowUtil.updateTitle();
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
        if (Configs.featureCustomWindowTitle) {
            CustomWindowUtil.updateTitle();
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
        CustomWindowUtil.updateFPS(fps);
    }
}
