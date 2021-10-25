package top.hendrixshen.TweakMyClient.mixin.disable.disableRenderOverlayFire;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(ScreenEffectRenderer.class)
public abstract class MixinScreenEffectRenderer {
    @Redirect(
            method = "renderScreenEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/ScreenEffectRenderer;renderFire(Lnet/minecraft/client/Minecraft;Lcom/mojang/blaze3d/vertex/PoseStack;)V"
            )
    )
    private static void onRenderFireOverlay(Minecraft minecraft, PoseStack poseStack) {
        if (!Configs.Disable.DISABLE_RENDER_OVERLAY_FIRE.getBooleanValue()) {
            IMixinScreenEffectRenderer.invokeRenderFireOverlay(minecraft, poseStack);
        }
    }
}
