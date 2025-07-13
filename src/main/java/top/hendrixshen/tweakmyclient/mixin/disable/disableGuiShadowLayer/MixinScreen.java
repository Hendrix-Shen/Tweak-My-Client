package top.hendrixshen.tweakmyclient.mixin.disable.disableGuiShadowLayer;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.screens.Screen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class MixinScreen extends AbstractContainerEventHandler {
    @Inject(
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
            ),
            cancellable = true
    )
    private void onFillGradient(CallbackInfo ci) {
        if (Configs.disableGuiShadowLayer.getBooleanValue()) {
            ci.cancel();
        }
    }
}
