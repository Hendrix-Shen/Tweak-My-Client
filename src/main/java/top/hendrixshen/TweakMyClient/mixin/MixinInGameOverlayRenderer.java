package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(InGameOverlayRenderer.class)
public abstract class MixinInGameOverlayRenderer {
    @Redirect(
            method = "renderOverlays",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameOverlayRenderer;renderFireOverlay(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/util/math/MatrixStack;)V"
            )
    )
    private static void onRenderFireOverlay(MinecraftClient minecraftClient, MatrixStack matrixStack) {
        if (!Configs.Disable.DISABLE_RENDER_OVERLAY_FIRE.getBooleanValue()) {
            IMixinInGameOverlayRenderer.invokeRenderFireOverlay(minecraftClient, matrixStack);
        }
    }
}
