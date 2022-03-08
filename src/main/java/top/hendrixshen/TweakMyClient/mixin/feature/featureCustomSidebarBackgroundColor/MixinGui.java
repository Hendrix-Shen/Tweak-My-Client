package top.hendrixshen.TweakMyClient.mixin.feature.featureCustomSidebarBackgroundColor;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import top.hendrixshen.TweakMyClient.config.Configs;

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
        if (Configs.Feature.FEATURE_CUSTOM_SIDEBAR_BACKGROUND_COLOR.getBooleanValue()) {
            args.set(4, Configs.Color.COLOR_SIDEBAR_TITLE.getIntegerValue());
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
        if (Configs.Feature.FEATURE_CUSTOM_SIDEBAR_BACKGROUND_COLOR.getBooleanValue()) {
            args.set(4, Configs.Color.COLOR_SIDEBAR_CONTENT.getIntegerValue());
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
        if (Configs.Feature.FEATURE_CUSTOM_SIDEBAR_BACKGROUND_COLOR.getBooleanValue()) {
            args.set(4, Configs.Color.COLOR_SIDEBAR_CONTENT.getIntegerValue());
        }
    }
}
