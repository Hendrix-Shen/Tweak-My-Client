package top.hendrixshen.tweakmyclient.mixin.feature.featureBreakingRestriction;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.helper.AreaBox;
import top.hendrixshen.tweakmyclient.helper.BreakingRestrictionBoxType;
import top.hendrixshen.tweakmyclient.helper.Cache;

import java.util.HashSet;

@Mixin(MultiPlayerGameMode.class)
public class MixinMultiPlayerGameMode {
    @Inject(
            method = "startDestroyBlock",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onStartDestroyBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (this.tmc$shouldLimit(pos)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(
            method = "continueDestroyBlock",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onContinueDestroyBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (this.tmc$shouldLimit(pos)) {
            cir.setReturnValue(false);
        }
    }

    private boolean tmc$shouldLimit(BlockPos pos) {
        if (!Configs.featureBreakingRestrictionBox) {
            return false;
        }

        BreakingRestrictionBoxType mode = Configs.listBreakingRestrictionBoxType;

        switch (mode) {
            case BLACKLIST:
                return this.tmc$inAreas(Cache.getInstance().getBreakingRestrictionBoxBlacklist(), pos);
            case WHITELIST:
                return !this.tmc$inAreas(Cache.getInstance().getBreakingRestrictionBoxWhiteList(), pos);
            default:
                return false;
        }
    }

    private boolean tmc$inAreas(HashSet<AreaBox> areaBoxes, BlockPos blockPos) {
        for (AreaBox areaBox : areaBoxes) {
            if (areaBox.contains(blockPos)) {
                return true;
            }
        }
        return false;
    }
}
