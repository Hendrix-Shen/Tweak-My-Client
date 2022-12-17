package top.hendrixshen.tweakmyclient.compat.meteor.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.compat.meteor.MeteorHelper;
import top.hendrixshen.tweakmyclient.compat.wurst.WurstHelper;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.mixin.MixinType;
import top.hendrixshen.tweakmyclient.util.mixin.annotation.MagicAttack;
import top.hendrixshen.tweakmyclient.util.mixin.annotation.MagicInterruption;

@MagicInterruption(targets = "meteordevelopment.meteorclient.mixin.ClientPlayerEntityMixin")
@Dependencies(and = @Dependency(value = "meteor-client"))
@Mixin(value = LocalPlayer.class, priority = 1100)
public abstract class MixinLocalPlayer extends LivingEntity {
    @Shadow
    private boolean startedUsingItem;

    protected MixinLocalPlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @MagicAttack(
            type = MixinType.REDIRECT,
            name = "redirectUsingItem",
            owner = "class_1309",
            method = "method_6007",
            desc = "()V"
    )
    private boolean tmc$getUsingItemState(LocalPlayer instance) {
        if (Configs.disableSlowdown || MeteorHelper.isNoSlowdownHackEnable()) {
            return false;
        }
        return this.startedUsingItem;
    }
}
