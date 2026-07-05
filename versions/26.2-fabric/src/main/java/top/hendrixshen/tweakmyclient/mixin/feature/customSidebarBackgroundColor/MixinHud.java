package top.hendrixshen.tweakmyclient.mixin.feature.customSidebarBackgroundColor;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.gui.Hud;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.ModifyExpressionValue;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc26.1: subproject 1.16.5 (main project) [dummy]</li>
 * <li>mc26.2+        : subproject 26.2       &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(Hud.class)
public abstract class MixinHud {
    @ModifyExpressionValue(
            method = "displayScoreboardSidebar",
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
            method = "displayScoreboardSidebar",
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
