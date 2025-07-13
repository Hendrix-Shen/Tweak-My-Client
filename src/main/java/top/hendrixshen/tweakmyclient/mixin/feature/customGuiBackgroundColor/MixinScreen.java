package top.hendrixshen.tweakmyclient.mixin.feature.customGuiBackgroundColor;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.gui.screens.Screen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Screen.class)
public abstract class MixinScreen {
    @ModifyArg(
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
            ),
            //#if MC > 11904
            index = 4
            //#elseif MC > 11502
            //$$ index = 5
            //#else
            //$$ index = 4
            //#endif
    )
    private int modifyColorStart(int colorStart) {
        if (Configs.customGuiBackgroundColor.getBooleanValue()) {
            return Configs.customGuiBackgroundStartColor.getIntegerValue();
        }

        return colorStart;
    }

    @ModifyArg(
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
            ),
            //#if MC > 11904
            index = 5
            //#elseif MC > 11502
            //$$ index = 6
            //#else
            //$$ index = 5
            //#endif
    )
    private int modifyColorStop(int colorStop) {
        if (Configs.customGuiBackgroundColor.getBooleanValue()) {
            return Configs.customGuiBackgroundStopColor.getIntegerValue();
        }

        return colorStop;
    }
}
