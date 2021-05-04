package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(Block.class)
public class MixinBlock {
    @Inject(
            method = "getVelocityMultiplier",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onGetVelocityMultiplier(CallbackInfoReturnable<Float> cir) {
        if (Configs.Disable.DISABLE_SLOWDOWN.getBooleanValue() && cir.getReturnValueF() < 1.0F) {
            cir.setReturnValue(1.0F);
        }
    }
}
