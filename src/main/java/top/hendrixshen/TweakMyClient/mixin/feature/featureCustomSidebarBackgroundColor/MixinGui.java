package top.hendrixshen.TweakMyClient.mixin.feature.featureCustomSidebarBackgroundColor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.scores.Objective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(Gui.class)
public abstract class MixinGui {
    @Redirect(
            method = "displayScoreboardSidebar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Options;getBackgroundColor(F)I",
                    ordinal = 1
            )
    )
    private int changeSidebarTitleBackgroundColor(Options instance, float f) {
        if (Configs.Feature.FEATURE_CUSTOM_SIDEBAR_BACKGROUND_COLOR.getBooleanValue()) {
            return Configs.Color.COLOR_SIDEBAR_TITLE.getIntegerValue();
        }
        return instance.getBackgroundColor(f);
    }

    @Redirect(
            method = "displayScoreboardSidebar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Options;getBackgroundColor(F)I",
                    ordinal = 0
            )
    )
    private int changeSidebarContentBackgroundColor(Options instance, float f) {
        if (Configs.Feature.FEATURE_CUSTOM_SIDEBAR_BACKGROUND_COLOR.getBooleanValue()) {
            return Configs.Color.COLOR_SIDEBAR_CONTENT.getIntegerValue();
        }
        return instance.getBackgroundColor(f);
    }
}
