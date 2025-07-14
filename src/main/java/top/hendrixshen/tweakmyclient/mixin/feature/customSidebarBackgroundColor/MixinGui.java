package top.hendrixshen.tweakmyclient.mixin.feature.customSidebarBackgroundColor;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.gui.Gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.ModifyExpressionValue;

@Mixin(Gui.class)
public abstract class MixinGui {
    @ModifyExpressionValue(
            //#if 12102 > MC && MC > 12002
            //$$ method = "method_55440",
            //#else
            method = "displayScoreboardSidebar",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Options;getBackgroundColor(F)I",
                    ordinal = 0
            )
    )
    private int modifyBackgroundColorTitle(int value) {
        if (Configs.customSidebarBackgroundColor.getBooleanValue()) {
            return Configs.customSidebarTitleColor.getIntegerValue();
        }

        return value;
    }

    @ModifyExpressionValue(
            //#if 12102 > MC && MC > 12002
            //$$ method = "method_55440",
            //#else
            method = "displayScoreboardSidebar",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Options;getBackgroundColor(F)I",
                    ordinal = 1
            )
    )
    private int modifyBackgroundColorContent(int value) {
        if (Configs.customSidebarBackgroundColor.getBooleanValue()) {
            return Configs.customSidebarContentColor.getIntegerValue();
        }

        return value;
    }
}
