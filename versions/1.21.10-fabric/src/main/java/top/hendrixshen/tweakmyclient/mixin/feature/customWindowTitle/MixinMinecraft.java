package top.hendrixshen.tweakmyclient.mixin.feature.customWindowTitle;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;

import net.minecraft.client.Minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import top.hendrixshen.tweakmyclient.game.Configs;
import top.hendrixshen.tweakmyclient.impl.feature.customWindowTitle.CustomWindowTitleHandler;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Shadow
    private static int fps;

    @Inject(method = "updateTitle", at = @At("HEAD"), cancellable = true)
    private void onUpdateTitle(CallbackInfo ci) {
        if (Configs.customWindowTitle.getBooleanValue()) {
            ci.cancel();
        }
    }

    // Expose FPS data
    @Definition(id = "fps", field = "Lnet/minecraft/client/Minecraft;fps:I")
    @Definition(id = "frames", field = "Lnet/minecraft/client/Minecraft;frames:I")
    @Expression("fps = this.frames")
    @Inject(
            method = "runTick",
            at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER)
    )
    private void afterCalculateFPS(boolean bl, CallbackInfo ci) {
        CustomWindowTitleHandler.getInstance().updateFps(MixinMinecraft.fps);
    }

}
