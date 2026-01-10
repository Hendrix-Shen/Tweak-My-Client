package top.hendrixshen.tweakmyclient.mixin.disable.disableGuiShadowLayer;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc1.20.4: subproject 1.16.5 (main project)</li>
 * <li>mc1.20.5+        : subproject 1.20.6 [dummy]</li>
 * <li>mc1.21.10+       : subproject 1.20.10        &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(Screen.class)
public abstract class MixinScreen {
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
