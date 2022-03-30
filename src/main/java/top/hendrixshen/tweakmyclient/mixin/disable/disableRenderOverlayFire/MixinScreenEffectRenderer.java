package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayFire;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;

@Dependencies(and = @Dependency(value = "minecraft", versionPredicate = ">=1.16"))
@Mixin(ScreenEffectRenderer.class)
public abstract class MixinScreenEffectRenderer {
    @Inject(
            method = "renderFire",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private static void onRenderFireOverlay(Minecraft minecraft, PoseStack poseStack, CallbackInfo ci) {
        if (Configs.disableRenderOverlayFire) {
            ci.cancel();
        }
    }
}
