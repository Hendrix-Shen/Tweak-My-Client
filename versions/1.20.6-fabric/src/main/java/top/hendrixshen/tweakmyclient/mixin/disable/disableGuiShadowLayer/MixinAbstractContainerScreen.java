package top.hendrixshen.tweakmyclient.mixin.disable.disableGuiShadowLayer;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc1.20.4: subproject 1.16.5 (main project) [dummy]</li>
 * <li>mc1.20.5+        : subproject 1.20.6        &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(AbstractContainerScreen.class)
public abstract class MixinAbstractContainerScreen<T extends AbstractContainerMenu> {
    @WrapOperation(
            method = "renderBackground",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderTransparentBackground(Lnet/minecraft/client/gui/GuiGraphics;)V"
            )
    )
    private void disableGuiShadowLayer(AbstractContainerScreen<T> instance, GuiGraphics guiGraphics, Operation<Void> original) {
        if (!Configs.disableGuiShadowLayer.getBooleanValue()) {
            original.call(instance, guiGraphics);
        }
    }
}
