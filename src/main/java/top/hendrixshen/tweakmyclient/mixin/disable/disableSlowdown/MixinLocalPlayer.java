package top.hendrixshen.tweakmyclient.mixin.disable.disableSlowdown;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.game.Configs;

@Dependencies(
        conflict = {
                @Dependency(value = "meteor-client"),
                @Dependency(value = "wurst")
        }
)
@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer extends LivingEntity {
    @Shadow
    private boolean startedUsingItem;

    protected MixinLocalPlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyExpressionValue(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem ()Z",
                    ordinal = 0
            )
    )
    private boolean getUsingItemState(boolean original) {
        if (Configs.disableSlowdown.getBooleanValue()) {
            return false;
        }

        return original;
    }
}
