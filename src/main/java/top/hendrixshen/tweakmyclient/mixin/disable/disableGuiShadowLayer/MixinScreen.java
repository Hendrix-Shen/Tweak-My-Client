package top.hendrixshen.tweakmyclient.mixin.disable.disableGuiShadowLayer;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.screens.Screen;

// CHECKSTYLE.OFF: ImportOrder
//#if MC > 11904
//$$ import net.minecraft.client.gui.GuiGraphics;
//#endif

//#if 12000 > MC && MC > 11502
import com.mojang.blaze3d.vertex.PoseStack;
//#endif
// CHECKSTYLE.ON: ImportOrder

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

// CHECKSTYLE.OFF: JavadocStyle

/**
 * <li>mc1.14 ~ mc1.20.4    : subproject 1.16.5 (main project)        &lt;--------</li>
 * <li>mc1.20.5+            : subproject 1.20.6 [dummy]</li>
 * <li>mc1.21.10 ~ mc1.21.11: subproject 1.20.10</li>
 * <li>mc26.1+              : subproject 26.1.2</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(Screen.class)
public abstract class MixinScreen extends AbstractContainerEventHandler {
    @WrapOperation(
            //#if MC > 11903
            //$$ method = "renderBackground",
            //#elseif MC > 11502
            method = "renderBackground(Lcom/mojang/blaze3d/vertex/PoseStack;I)V",
            //#else
            //$$ method = "renderBackground(I)V",
            //#endif
            at = @At(
                    value = "INVOKE",
                    //#if MC > 12001
                    //$$ target = "Lnet/minecraft/client/gui/screens/Screen;renderTransparentBackground(Lnet/minecraft/client/gui/GuiGraphics;)V"
                    //#elseif MC > 11904
                    //$$ target = "Lnet/minecraft/client/gui/GuiGraphics;fillGradient(IIIIII)V"
                    //#elseif MC > 11502
                    target = "Lnet/minecraft/client/gui/screens/Screen;fillGradient(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V"
                    //#else
                    //$$ target = "Lnet/minecraft/client/gui/screens/Screen;fillGradient(IIIIII)V"
                    //#endif
            )
    )
    private void onFillGradient(
            //#if MC > 12001
            //$$ Screen instance,
            //$$ GuiGraphics guiGraphics,
            //#elseif MC > 11904
            //$$ GuiGraphics instance,
            //#elseif MC > 11903
            //#else
            Screen instance,
            //#endif
            //#if 12000 > MC && MC > 11502
            PoseStack poseStack,
            //#endif
            //#if MC < 12002
            int minX,
            int minY,
            int maxX,
            int maxY,
            int colorFrom,
            int colorTo,
            //#endif
            Operation<Void> original
    ) {
        if (!Configs.disableGuiShadowLayer.getBooleanValue()) {
            original.call(
                    //#if 11904 > MC || MC > 11904
                    instance,
                    //#endif
                    //#if MC > 12001
                    //$$ guiGraphics
                    //#endif
                    //#if 12000 > MC && MC > 11502
                    poseStack,
                    //#endif
                    //#if MC < 12002
                    minX,
                    minY,
                    maxX,
                    maxY,
                    colorFrom,
                    colorTo
                    //#endif
            );
        }
    }
}
