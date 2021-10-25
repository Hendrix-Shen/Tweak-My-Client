package top.hendrixshen.TweakMyClient.mixin.disable.disableSlowdown;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(WebBlock.class)
public class MixinWebBlock {
    @Inject(
            method = "entityInside",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;makeStuckInBlock(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/phys/Vec3;)V"
            ),
            cancellable = true
    )
    private void onWalkInCobWebBlock(BlockState blockState, Level level, BlockPos blockPos, Entity entity, CallbackInfo ci) {
        if ((Configs.Disable.DISABLE_SLOWDOWN.getBooleanValue()) && entity instanceof Player) {
            ci.cancel();
        }
    }
}
