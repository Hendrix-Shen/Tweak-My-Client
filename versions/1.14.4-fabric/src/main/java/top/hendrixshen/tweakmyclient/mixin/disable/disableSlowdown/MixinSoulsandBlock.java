package top.hendrixshen.tweakmyclient.mixin.disable.disableSlowdown;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoulsandBlock;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoulsandBlock.class)
public abstract class MixinSoulsandBlock {
    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
    private void onEntityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity, CallbackInfo ci) {
        if (Configs.disableSlowdown.getBooleanValue() && entity instanceof LocalPlayer) {
            ci.cancel();
        }
    }
}
