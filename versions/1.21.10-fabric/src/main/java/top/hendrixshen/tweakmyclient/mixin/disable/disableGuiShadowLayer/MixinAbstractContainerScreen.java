package top.hendrixshen.tweakmyclient.mixin.disable.disableGuiShadowLayer;

// CHECKSTYLE.OFF: JavadocStyle

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import top.hendrixshen.tweakmyclient.game.Configs;

@Mixin(Screen.class)
public class MixinAbstractContainerScreen {
    @WrapOperation(
            method = "renderBackground",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/Screen;renderTransparentBackground(Lnet/minecraft/client/gui/GuiGraphics;)V"
            )
    )
    private void disableGuiShadowLayer(Screen instance, GuiGraphics guiGraphics, Operation<Void> original) {
        if (!Configs.disableGuiShadowLayer.getBooleanValue()) {
            original.call(instance, guiGraphics);
        }
    }

}
