package top.hendrixshen.tweakmyclient.compat.mixin.feature.featureCustomSidebarBackgroundColor;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(Gui.class)
public abstract class MixinGui {
    @ModifyArgs(
            method = "displayScoreboardSidebar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;fill(IIIII)V",
                    ordinal = 1
            )
    )
    private void changeSidebarTitleBackgroundColor(Args args) {
        if (Configs.featureCustomSidebarBackgroundColor) {
            args.set(4, Configs.colorSidebarTitle.intValue);
        }
    }

    @ModifyArgs(
            method = "displayScoreboardSidebar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;fill(IIIII)V",
                    ordinal = 0
            )
    )
    private void changeSidebarContentBackgroundColor_1(Args args) {
        if (Configs.featureCustomSidebarBackgroundColor) {
            args.set(4, Configs.colorSidebarContent.intValue);
        }
    }

    @ModifyArgs(
            method = "displayScoreboardSidebar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;fill(IIIII)V",
                    ordinal = 2
            )
    )
    private void changeSidebarContentBackgroundColor_2(Args args) {
        if (Configs.featureCustomSidebarBackgroundColor) {
            args.set(4, Configs.colorSidebarContent.intValue);
        }
    }
}
