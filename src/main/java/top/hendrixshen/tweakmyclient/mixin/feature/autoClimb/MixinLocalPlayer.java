package top.hendrixshen.tweakmyclient.mixin.feature.autoClimb;

import top.hendrixshen.magiclib.api.compat.minecraft.world.entity.player.PlayerCompat;
import top.hendrixshen.magiclib.util.MiscUtil;
import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer extends LivingEntity {
    protected MixinLocalPlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void onAiStep(CallbackInfo ci) {
        PlayerCompat playerCompat = PlayerCompat.of(MiscUtil.cast(this));

        if (Configs.autoClimb.getBooleanValue()
                //#if MC > 11502
                && this.onClimbable()
                //#else
                //$$ && this.onLadder()
                //#endif
                && playerCompat.getXRot() <= -50F
                //#if MC > 11404
                && !this.isCrouching()
                //#else
                //$$ && !this.isVisuallySneaking()
                //#endif
        ) {
            Vec3 vec3 = this.getDeltaMovement();
            this.setDeltaMovement(vec3.x, 0.1176D, vec3.z);
        }
    }
}
