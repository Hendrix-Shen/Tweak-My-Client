package top.hendrixshen.tweakmyclient.mixin.patch.disableResourcePackIncompatibleTip;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.screens.packs.TransferableSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.tweakmyclient.game.Configs;

@Mixin(TransferableSelectionList.PackEntry.class)
public abstract class MixinTransferableSelectionList {
    @ModifyExpressionValue(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/packs/repository/PackCompatibility;isCompatible()Z"
            )
    )
    private boolean disableIncompatibleTipLabel(boolean original) {
        return Configs.disableResourcePackIncompatibleTip.getBooleanValue() || original;
    }

    @ModifyExpressionValue(
            //#if MC > 11903
            //$$ method = "handlePackSelection",
            //#else
            method = "mouseClicked",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/packs/repository/PackCompatibility;isCompatible()Z"
            )
    )
    private boolean disableIncompatibleTipScreen(boolean original) {
        return Configs.disableResourcePackIncompatibleTip.getBooleanValue() || original;
    }
}
