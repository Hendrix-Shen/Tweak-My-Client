package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayFire;

//#if MC >= 11500
import com.mojang.blaze3d.vertex.PoseStack;
//#endif
import net.minecraft.client.Minecraft;
//#if MC >= 11500
import net.minecraft.client.renderer.ScreenEffectRenderer;
//#endif
import org.spongepowered.asm.mixin.Mixin;
//#if MC >= 11500
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(ScreenEffectRenderer.class)
//#else
//$$ @Mixin(Minecraft.class)
//#endif
public abstract class MixinScreenEffectRenderer {
    //#if MC >= 11500
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
