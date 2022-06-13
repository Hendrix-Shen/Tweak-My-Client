package top.hendrixshen.tweakmyclient.mixin.feature.featureAutoClimb;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer extends LivingEntity {
    protected MixinLocalPlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "aiStep",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onAiStep(CallbackInfo ci) {
        //#if MC >= 11600
        if (Configs.featureAutoClimb && this.onClimbable() && this.getXRotCompat() <= -50f && !this.isCrouching()) {
        //#elseif MC >= 11500
        //$$ if (Configs.featureAutoClimb && this.onLadder() && this.getXRotCompat() <= -50f && !this.isCrouching()) {
        //#else
        //$$ if (Configs.featureAutoClimb && this.onLadder() && this.getXRotCompat() <= -50f && !this.isVisuallySneaking()) {
        //#endif
            Vec3 vec3 = this.getDeltaMovement();
            this.setDeltaMovement(vec3.x, 0.1176D, vec3.z);
        }
    }
}
