package top.hendrixshen.tweakmyclient.mixin.disable.disableSlowdown;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(SlimeBlock.class)
public class MixinSlimeBlock extends Block {
    public MixinSlimeBlock(Properties properties) {
        super(properties);
    }

    @Inject(
            method = "bounceUp",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onbounceUp(Entity entity, CallbackInfo ci) {
        if (Configs.disableSlowdown.getBooleanValue() && entity instanceof LocalPlayer) {
            Vec3 vec3 = entity.getDeltaMovement();
            if (vec3.y < 0) {
                entity.setDeltaMovement(vec3.x, 0, vec3.z);
            }
            ci.cancel();
        }
    }

    @Inject(
            method = "stepOn",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void stepOn(Level level, BlockPos blockPos, Entity entity, CallbackInfo ci) {
        if (Configs.disableSlowdown.getBooleanValue()) {
            ci.cancel();
        }
    }
}
