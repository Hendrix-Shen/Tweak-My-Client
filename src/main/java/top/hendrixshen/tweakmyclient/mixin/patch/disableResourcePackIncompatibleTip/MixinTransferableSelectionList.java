package top.hendrixshen.tweakmyclient.mixin.patch.disableResourcePackIncompatibleTip;

import net.minecraft.client.gui.screens.packs.TransferableSelectionList;
import net.minecraft.server.packs.repository.PackCompatibility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(TransferableSelectionList.PackEntry.class)
public class MixinTransferableSelectionList {
    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/packs/repository/PackCompatibility;isCompatible()Z"
            )
    )
    private boolean disableIncompatibleTipLabel(PackCompatibility packCompatibility) {
        return Configs.disableResourcePackIncompatibleTip || packCompatibility.isCompatible();
    }

    @Redirect(
            //#if MC > 11903
            method = "handlePackSelection",
            //#else
            //$$ method = "mouseClicked",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/packs/repository/PackCompatibility;isCompatible()Z"
            )
    )
    private boolean disableIncompatibleTipScreen(PackCompatibility packCompatibility) {
        return Configs.disableResourcePackIncompatibleTip || packCompatibility.isCompatible();
    }
}
