package top.hendrixshen.TweakMyClient.mixin.disable.disableGuiShadowLayer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(Screen.class)
public abstract class MixinScreen extends AbstractContainerEventHandler {
    /**
     * @param i startX
     * @param j startY
     * @param k endX
     * @param l endY
     * @param m colorStart
     * @param n colorEnd
     */
    @Redirect(
            method = "renderBackground(Lcom/mojang/blaze3d/vertex/PoseStack;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/Screen;fillGradient(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V"
            )
    )
    private void onFillGradient(Screen instance, PoseStack poseStack, int i, int j, int k, int l, int m, int n) {
        if (Configs.Feature.FEATURE_CUSTOM_GUI_BACKGROUND_COLOR.getBooleanValue()) {
            m = Configs.Color.COLOR_GUI_START.getIntegerValue();
            n = Configs.Color.COLOR_GUI_STOP.getIntegerValue();
        }
        if (!Configs.Disable.DISABLE_GUI_SHADOW_LAYER.getBooleanValue()) {
            this.fillGradient(poseStack, i, j, k, l, m, n);
        }
    }
}
