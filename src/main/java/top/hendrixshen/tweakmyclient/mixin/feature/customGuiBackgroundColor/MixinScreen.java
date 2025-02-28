package top.hendrixshen.tweakmyclient.mixin.feature.customGuiBackgroundColor;

import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import top.hendrixshen.tweakmyclient.game.Configs;

@Mixin(Screen.class)
public abstract class MixinScreen {
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
            //#if MC > 12001
            //$$ method = "renderTransparentBackground",
            //#elseif MC > 11903
            //$$ method = "renderBackground",
            //#elseif MC > 11502
            method = "renderBackground(Lcom/mojang/blaze3d/vertex/PoseStack;I)V",
            //#else
            //$$ method = "renderBackground(I)V",
            //#endif
            at = @At(
                    value = "INVOKE",
                    //#if MC > 11904
                    //$$ target = "Lnet/minecraft/client/gui/GuiGraphics;fillGradient(IIIIII)V"
                    //#elseif MC > 11502
                    target = "Lnet/minecraft/client/gui/screens/Screen;fillGradient(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V"
                    //#else
                    //$$ target = "Lnet/minecraft/client/gui/screens/Screen;fillGradient(IIIIII)V"
                    //#endif
            )
    )
    private void onFillGradient(Args args) {
        if (Configs.customGuiBackgroundColor.getBooleanValue()) {
            args.set(
                    //#if 12000 > MC && MC > 11404
                    5,
                    //#else
                    //$$ 6,
                    //#endif
                    Configs.customGuiBackgroundStartColor.getIntegerValue()
            );
            args.set(
                    //#if 12000 > MC && MC > 11404
                    4,
                    //#else
                    //$$ 5,
                    //#endif
                    Configs.customGuiBackgroundStopColor.getIntegerValue()
            );
        }
    }
}
