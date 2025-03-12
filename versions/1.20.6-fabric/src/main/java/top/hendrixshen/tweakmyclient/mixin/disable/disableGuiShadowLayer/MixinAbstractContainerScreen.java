package top.hendrixshen.tweakmyclient.mixin.disable.disableGuiShadowLayer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.tweakmyclient.game.Configs;

@Mixin(AbstractContainerScreen.class)
public class MixinAbstractContainerScreen<T extends AbstractContainerMenu> {
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
