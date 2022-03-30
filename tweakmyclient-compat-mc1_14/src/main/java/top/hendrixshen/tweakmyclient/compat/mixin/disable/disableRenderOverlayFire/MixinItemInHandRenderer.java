package top.hendrixshen.tweakmyclient.compat.mixin.disable.disableRenderOverlayFire;

import net.minecraft.client.renderer.ItemInHandRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(ItemInHandRenderer.class)
public abstract class MixinItemInHandRenderer {
    @Inject(
            method = "renderFire",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onRenderFireOverlay(CallbackInfo ci) {
        if (Configs.disableRenderOverlayFire) {
            ci.cancel();
        }
    }
}
