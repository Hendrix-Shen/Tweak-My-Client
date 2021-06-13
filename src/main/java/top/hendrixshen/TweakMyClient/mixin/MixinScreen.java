package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(Screen.class)
public class MixinScreen {
    @Inject(
            method = "renderBackground(Lnet/minecraft/client/util/math/MatrixStack;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/Screen;fillGradient(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"
            ),
            cancellable = true
    )
    private void onFillG(MatrixStack matrices, int vOffset, CallbackInfo ci) {
        if (Configs.Disable.DISABLE_GUI_SHADOW_LAYER.getBooleanValue()) {
            ci.cancel();
        }
    }

    @ModifyConstant(
            method = "renderBackground(Lnet/minecraft/client/util/math/MatrixStack;I)V",
            constant = @Constant(
                    intValue = -1072689136
            )

    )
    private int customStartColor(int startColor) {
        if (Configs.Feature.FEATURE_CUSTOM_GUI_BACKGROUND_COLOR.getBooleanValue()) {
            return Configs.Color.COLOR_GUI_START.getIntegerValue();
        }
        return startColor;
    }

    @ModifyConstant(
            method = "renderBackground(Lnet/minecraft/client/util/math/MatrixStack;I)V",
            constant = @Constant(
                    intValue = -804253680
            )

    )
    private int customStopColor(int startColor) {
        if (Configs.Feature.FEATURE_CUSTOM_GUI_BACKGROUND_COLOR.getBooleanValue()) {
            return Configs.Color.COLOR_GUI_STOP.getIntegerValue();
        }
        return startColor;
    }
}
