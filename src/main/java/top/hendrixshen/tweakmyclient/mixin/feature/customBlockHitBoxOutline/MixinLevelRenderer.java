package top.hendrixshen.tweakmyclient.mixin.feature.customBlockHitBoxOutline;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.renderer.LevelRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer {
    @Inject(
            //#if MC >= 26.2
            //$$ method = "submitBlockOutline",
            //#else
            method = "renderHitOutline",
            //#endif
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void onDrawBlockOutline(CallbackInfo ci) {
        if (Configs.customBlockHitBoxOutline.getBooleanValue()) {
            ci.cancel();
        }
    }
}
