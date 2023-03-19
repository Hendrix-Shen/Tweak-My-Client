package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayFire;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
//#if MC > 11404
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;
//#else
//$$ import top.hendrixshen.magiclib.compat.preprocess.api.DummyClass;
//#endif

//#if MC > 11404
@Mixin(ScreenEffectRenderer.class)
//#else
//$$ @Mixin(DummyClass.class)
//#endif
public abstract class MixinScreenEffectRenderer {
    //#if MC > 11404
    @Inject(
            method = "renderFire",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private static void onRenderFire(Minecraft minecraft, PoseStack poseStack, CallbackInfo ci) {
        if (Configs.disableRenderOverlayFire) {
            ci.cancel();
        }
    }
    //#endif
}
