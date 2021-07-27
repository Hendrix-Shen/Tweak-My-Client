package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(Screen.class)
public abstract class MixinScreen extends AbstractParentElement {
    @Redirect(
            method = "renderBackground(Lnet/minecraft/client/util/math/MatrixStack;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/Screen;fillGradient(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"
            )
    )
    private void onFillGradient(Screen screen, MatrixStack matrices, int startX, int startY, int endX, int endY, int colorStart, int colorEnd) {
        if (Configs.Feature.FEATURE_CUSTOM_GUI_BACKGROUND_COLOR.getBooleanValue()) {
            colorStart = Configs.Color.COLOR_GUI_START.getIntegerValue();
            colorEnd = Configs.Color.COLOR_GUI_STOP.getIntegerValue();
        }
        if (!Configs.Disable.DISABLE_GUI_SHADOW_LAYER.getBooleanValue()) {
            this.fillGradient(matrices, startX, startY, endX, endY, colorStart, colorEnd);
        }
    }
}
