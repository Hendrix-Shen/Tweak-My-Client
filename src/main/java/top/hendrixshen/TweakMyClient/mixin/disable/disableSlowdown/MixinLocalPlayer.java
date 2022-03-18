package top.hendrixshen.tweakmyclient.mixin.disable.disableSlowdown;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer extends LivingEntity {
    @Shadow
    private boolean startedUsingItem;

    protected MixinLocalPlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Redirect(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/player/LocalPlayer.isUsingItem ()Z",
                    ordinal = 0
            )
    )
    private boolean getUsingItemState(LocalPlayer instance) {
        if (Configs.disableSlowdown.getBooleanValue()) {
            return false;
        }
        return this.startedUsingItem;
    }
}
