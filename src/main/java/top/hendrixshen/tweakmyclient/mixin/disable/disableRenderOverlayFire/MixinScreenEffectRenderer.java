package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayFire;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.game.Configs;

//#if MC > 12103
//$$ import net.minecraft.client.renderer.MultiBufferSource;
//#else
import net.minecraft.client.Minecraft;
//#endif

@Mixin(ScreenEffectRenderer.class)
public abstract class MixinScreenEffectRenderer {
    @Inject(method = "renderFire", at = @At("HEAD"), cancellable = true)
    private static void onRenderFire(
            //#if MC < 12104
            Minecraft minecraft,
            //#endif
            PoseStack poseStack,
            //#if MC > 12103
            //$$ MultiBufferSource multiBufferSource,
            //#endif
            CallbackInfo ci
    ) {
        if (Configs.disableFireOverlayRender.getBooleanValue()) {
            ci.cancel();
        }
    }
}
