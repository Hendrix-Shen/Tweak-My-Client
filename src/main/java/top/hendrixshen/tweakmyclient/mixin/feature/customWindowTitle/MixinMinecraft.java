package top.hendrixshen.tweakmyclient.mixin.feature.customWindowTitle;

import top.hendrixshen.tweakmyclient.impl.feature.customWindowTitle.CustomWindowTitleHandler;

// CHECKSTYLE.OFF: ImportOrder
//#if MC > 11404
import top.hendrixshen.tweakmyclient.game.Configs;
//#endif
// CHECKSTYLE.ON: ImportOrder

import net.minecraft.client.Minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow
    private static int fps;

    //#if MC > 11404
    @Inject(method = "updateTitle", at = @At("HEAD"), cancellable = true)
    private void onUpdateTitle(CallbackInfo ci) {
        if (Configs.customWindowTitle.getBooleanValue()) {
            ci.cancel();
        }
    }
    //#endif

    // Expose FPS data
    @Inject(
            method = "runTick",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/Util;getMillis()J",
                            ordinal = 1
                    ),
                    to = @At(
                            value = "INVOKE",
                            //#if MC > 11802
                            //$$ target = "Ljava/lang/String;format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                            //#else
                            target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                            //#endif
                            remap = false
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    //#if MC > 11802
                    //$$ target = "Ljava/lang/String;format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    //#else
                    target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
                    //#endif
                    remap = false
            )
    )
    private void afterCalculateFPS(boolean bl, CallbackInfo ci) {
        CustomWindowTitleHandler.getInstance().updateFps(MixinMinecraft.fps);
    }
}
