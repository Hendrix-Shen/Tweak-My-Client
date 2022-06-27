package top.hendrixshen.tweakmyclient.mixin.disable.disableSlowdown;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    public MixinLivingEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyVariable(
            method = "travel",
            at = @At(
                    value = "STORE"
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            //#if MC >= 11500
                            target = "Lnet/minecraft/world/entity/LivingEntity;getBlockPosBelowThatAffectsMyMovement()Lnet/minecraft/core/BlockPos;"
                            //#else
                            //$$ target = "Lnet/minecraft/world/entity/LivingEntity;getBoundingBox()Lnet/minecraft/world/phys/AABB;"
                            //#endif
                    ),
                    to = @At(
                            value = "INVOKE",
                            //#if MC >= 11600
                            target = "Lnet/minecraft/world/entity/LivingEntity;handleRelativeFrictionAndCalculateMovement(Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;"
                            //#else
                            //$$ target = "Lnet/minecraft/world/entity/LivingEntity;getFrictionInfluencedSpeed(F)F"
                            //#endif
                    )
            ),
            ordinal = 0
    )
    private float onGetFriction(float f) {
        if (Configs.disableSlowdown && (Object) this instanceof LocalPlayer && !this.isInWater() && f > 0.6F) {
            return 0.6F;
        }
        return f;
    }
}
