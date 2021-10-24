package top.hendrixshen.TweakMyClient.mixin.disable.disableRenderOverlayPumpkin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.scores.Objective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(Gui.class)
public abstract class MixinGui {
    @Shadow
    protected abstract void renderPumpkin();

    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;renderPumpkin()V"
            )
    )
    private void onRenderPumpkinOverlay(Gui instance) {
        if (!Configs.Disable.DISABLE_RENDER_OVERLAY_PUMPKIN.getBooleanValue()) {
            renderPumpkin();
        }
    }
}
