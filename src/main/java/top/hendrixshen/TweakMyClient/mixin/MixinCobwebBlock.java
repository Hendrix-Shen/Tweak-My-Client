package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.CobwebBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(CobwebBlock.class)
public class MixinCobwebBlock {
    @Inject(
            method = "onEntityCollision",
            at = @At(
                    value = "INVOKE",
                    target =  "Lnet/minecraft/entity/Entity;slowMovement(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Vec3d;)V"
            ),
            cancellable = true
    )
    private void onWalkInCobWebBlock(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci)
    {
        if ((Configs.Disable.DISABLE_SLOWDOWN.getBooleanValue()) && entity instanceof PlayerEntity)
        {
            ci.cancel();
        }
    }
}
