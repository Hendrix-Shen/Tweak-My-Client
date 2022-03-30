package top.hendrixshen.tweakmyclient.compat.mixin.disable.disableSlowdown;

import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(Block.class)
public class MixinBlock {
    @Inject(
            method = "getFriction",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onGetFriction(CallbackInfoReturnable<Float> cir) {
        if (Configs.disableSlowdown && cir.getReturnValueF() < 0.6F) {
            cir.setReturnValue(0.6F);
        }
    }
}
