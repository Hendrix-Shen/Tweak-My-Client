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

//#if MC < 11500
//$$ import net.minecraft.world.level.BlockGetter;
//#endif

@Mixin(SlimeBlock.class)
public class MixinSlimeBlock extends Block {
    public MixinSlimeBlock(Properties properties) {
        super(properties);
    }

    @Inject(
            //#if MC > 11404
            method = "bounceUp",
            //#else
            //$$ method = "updateEntityAfterFallOn",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;getDeltaMovement()Lnet/minecraft/world/phys/Vec3;"
            ),
            cancellable = true
    )
    //#if MC > 11404
    private void onBounceUp(Entity entity, CallbackInfo ci) {
    //#else
    //$$ private void updateEntityAfterFallOn(BlockGetter blockGetter, Entity entity, CallbackInfo ci) {
    //#endif
        if (Configs.disableSlowdown && entity instanceof LocalPlayer) {
            Vec3 vec3 = entity.getDeltaMovement();

            if (vec3.y < 0 && vec3.y > -0.0792) { // Vertical momentum at 2x steady state.
                entity.setDeltaMovement(vec3.x, 0, vec3.z);
                ci.cancel();
            }
        }
    }
}
