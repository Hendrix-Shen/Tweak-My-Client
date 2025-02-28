package top.hendrixshen.tweakmyclient.mixin.feature.breakingRestrictionBox;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.game.Configs;

@Mixin(MultiPlayerGameMode.class)
public abstract class MixinMultiPlayerGameMode {
    @Inject(method = "startDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void onStartDestroyBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (this.tmc$shouldLimit(pos)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "continueDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void onContinueDestroyBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (this.tmc$shouldLimit(pos)) {
            cir.setReturnValue(false);
        }
    }

    @Unique
    private boolean tmc$shouldLimit(BlockPos pos) {
        if (!Configs.breakingRestrictionBox.getBooleanValue()) {
            return false;
        }

        return Configs.breakingRestrictionBoxRestriction.isAllowed(pos);
    }
}
