package top.hendrixshen.tweakmyclient.mixin.disable.disableSlowdown;

import top.hendrixshen.magiclib.util.MiscUtil;
import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// CHECKSTYLE.OFF: ImportOrder
//#if MC > 12101
//$$ import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.ModifyExpressionValue;
//#else
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
//#endif
// CHECKSTYLE.ON: ImportOrder

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    public MixinLivingEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    //#if MC > 12101
    //$$ @SuppressWarnings("ConstantConditions")
    //$$ @ModifyExpressionValue(
    //$$         method = "travelInAir",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Lnet/minecraft/world/level/block/Block;getFriction()F"
    //$$         )
    //$$ )
    //$$ private float modifyFriction(float friction) {
    //$$     if (Configs.disableSlowdown.getBooleanValue() && MiscUtil.cast(this) instanceof LocalPlayer && friction > 0.6F) {
    //$$         return 0.6F;
    //$$     }
    //$$
    //$$     return friction;
    //$$ }
    //#else
    @SuppressWarnings("ConstantConditions")
    @ModifyVariable(
            method = "travel",
            at = @At("STORE"),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            //#if MC > 11404
                            target = "Lnet/minecraft/world/entity/LivingEntity;getBlockPosBelowThatAffectsMyMovement()Lnet/minecraft/core/BlockPos;"
                            //#else
                            //$$ target = "Lnet/minecraft/world/entity/LivingEntity;getBoundingBox()Lnet/minecraft/world/phys/AABB;"
                            //#endif
                    ),
                    to = @At(
                            value = "INVOKE",
                            //#if MC > 11502
                            target = "Lnet/minecraft/world/entity/LivingEntity;handleRelativeFrictionAndCalculateMovement(Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;"
                            //#else
                            //$$ target = "Lnet/minecraft/world/entity/LivingEntity;getFrictionInfluencedSpeed(F)F"
                            //#endif
                    )
            ),
            ordinal = 0
    )
    private float modifyFriction(float friction) {
        if (Configs.disableSlowdown.getBooleanValue() && MiscUtil.cast(this) instanceof LocalPlayer && !this.isInWater() && friction > 0.6F) {
            return 0.6F;
        }

        return friction;
    }
    //#endif
}
