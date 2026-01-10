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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.expression.Definition;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.expression.Expression;

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

    @Definition(id = "fps", field = "Lnet/minecraft/client/Minecraft;fps:I")
    @Definition(id = "frames", field = "Lnet/minecraft/client/Minecraft;frames:I")
    @Expression("fps = this.frames")
    @Inject(
            method = "runTick",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private void exposeFps(boolean renderLevel, CallbackInfo ci) {
        CustomWindowTitleHandler.getInstance().updateFps(MixinMinecraft.fps);
    }
}
