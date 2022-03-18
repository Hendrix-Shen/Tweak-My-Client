package top.hendrixshen.tweakmyclient.mixin.feature.featureCustomGuiBackgroundColor;

import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(Screen.class)
public abstract class MixinScreen extends AbstractContainerEventHandler {
    /**
     * args
     * 0 - poseStack
     * 1 – startX
     * 2 – startY
     * 3 – endX
     * 4 – endY
     * 5 – colorStart
     * 6 – colorEnd
     */
    @ModifyArgs(
            method = "renderBackground(Lcom/mojang/blaze3d/vertex/PoseStack;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/Screen;fillGradient(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V"
            )
    )
    private void onFillGradient(Args args) {
        if (Configs.featureCustomGuiBackgroundColor.getBooleanValue()) {
            args.set(5, Configs.colorGuiStart.getIntegerValue());
            args.set(6, Configs.colorGuiStop.getIntegerValue());
        }
    }
}
