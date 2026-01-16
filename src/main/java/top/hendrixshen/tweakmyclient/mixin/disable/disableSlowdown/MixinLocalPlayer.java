package top.hendrixshen.tweakmyclient.mixin.disable.disableSlowdown;

import top.hendrixshen.magiclib.api.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.ModifyExpressionValue;

@Dependencies(
        conflict = {
                @Dependency(value = "meteor-client"),
                @Dependency(value = "wurst")
        }
)
@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer extends LivingEntity {
    protected MixinLocalPlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyExpressionValue(
            //#if MC >= 12111
            //$$ method = "modifyInput",
            //#else
            method = "aiStep",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem ()Z"
                    // CHECKSTYLE.OFF: NoWhitespaceBefore
                    // CHECKSTYLE.OFF: SeparatorWrap
                    //#if MC < 12111
                    , ordinal = 0
                    //#endif
                    // CHECKSTYLE.ON: SeparatorWrap
                    // CHECKSTYLE.ON: NoWhitespaceBefore
            )
    )
    private boolean getUsingItemState(boolean original) {
        if (Configs.disableSlowdown.getBooleanValue()) {
            return false;
        }

        return original;
    }
}
