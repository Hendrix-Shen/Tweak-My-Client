package top.hendrixshen.tweakmyclient.compat.mixin.disable.disableSlowdown;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(Entity.class)
public class MixinEntity {
    @Shadow
    protected Vec3 stuckSpeedMultiplier;

    @Redirect(
            method = "move",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/entity/Entity;stuckSpeedMultiplier:Lnet/minecraft/world/phys/Vec3;",
                    opcode = Opcodes.GETFIELD,
                    ordinal = 0
            )
    )
    private Vec3 onGetStuckSpeedMultiplier(Entity instance) {
        return Configs.disableSlowdown && instance instanceof LocalPlayer ? Vec3.ZERO : this.stuckSpeedMultiplier;
    }
}
