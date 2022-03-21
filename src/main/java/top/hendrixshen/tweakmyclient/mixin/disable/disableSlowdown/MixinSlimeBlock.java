package top.hendrixshen.tweakmyclient.mixin.disable.disableSlowdown;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
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
            if (vec3.y < 0 && vec3.y > -0.0792) { // Vertical momentum at 2x steady state.
                entity.setDeltaMovement(vec3.x, 0, vec3.z);
                ci.cancel();
            }
        }
    }
}
