package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {
    @Inject(
            method = "renderScoreboardSidebar",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onRenderScoreboardSidebar(MatrixStack matrices, ScoreboardObjective objective, CallbackInfo ci) {
        if (Configs.Disable.DISABLE_RENDER_SCOREBOARD.getBooleanValue()) {
            ci.cancel();
        }
    }

    @Redirect(
            method = "renderScoreboardSidebar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/option/GameOptions;getTextBackgroundColor(F)I",
                    ordinal = 1
            )
    )
    private int changeSidebarTitleBackgroundColor(GameOptions gameOptions, float fallbackOpacity) {
        if (Configs.Feature.FEATURE_CUSTOM_SIDEBAR_BACKGROUND_COLOR.getBooleanValue()) {
            return Configs.Color.COLOR_SIDEBAR_TITLE.getIntegerValue();
        }
        return gameOptions.getTextBackgroundColor(fallbackOpacity);
    }

    @Redirect(
            method = "renderScoreboardSidebar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/option/GameOptions;getTextBackgroundColor(F)I",
                    ordinal = 0
            )
    )
    private int changeSidebarContentBackgroundColor(GameOptions gameOptions, float fallbackOpacity) {
        if (Configs.Feature.FEATURE_CUSTOM_SIDEBAR_BACKGROUND_COLOR.getBooleanValue()) {
            return Configs.Color.COLOR_SIDEBAR_CONTENT.getIntegerValue();
        }
        return gameOptions.getTextBackgroundColor(fallbackOpacity);
    }

    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"
            )
    )
    private boolean onRenderPumpkinOverlay(ItemStack itemStack, Item item) {
        if (!Configs.Disable.DISABLE_RENDER_OVERLAY_PUMPKIN.getBooleanValue()) {
            return itemStack.isOf(item);
        }
        return false;
    }
}
