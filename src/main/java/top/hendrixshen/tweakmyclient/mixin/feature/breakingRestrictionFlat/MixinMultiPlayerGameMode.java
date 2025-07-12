package top.hendrixshen.tweakmyclient.mixin.feature.breakingRestrictionFlat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.magiclib.api.compat.minecraft.world.entity.player.PlayerCompat;
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
        Level level = Minecraft.getInstance().level;
        Player player = Minecraft.getInstance().player;

        if (Configs.breakingRestrictionFlat.getBooleanValue() && level != null && player != null) {
            return !player.isShiftKeyDown() && pos.getY() < PlayerCompat.of(player).getBlockPosition().getY();
        }

        return false;
    }
}
