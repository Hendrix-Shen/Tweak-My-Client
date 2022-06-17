package top.hendrixshen.tweakmyclient.mixin.disable.disableSlowdown;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
//#if MC >= 11500
import org.spongepowered.asm.mixin.injection.Inject;
//#endif
import org.spongepowered.asm.mixin.injection.Redirect;

//#if MC >= 11500
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//#endif
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

    //#if MC >= 11500
    @Inject(
            method = "getBlockSpeedFactor",
            at = @At(
                    value = "RETURN",
                    ordinal = 1
            ),
            cancellable = true
    )
    private void onGetBlockSpeedFactor(CallbackInfoReturnable<Float> cir) {
        if (Configs.disableSlowdown && (Object) this instanceof LocalPlayer && cir.getReturnValueF() < 1.0F) {
            cir.setReturnValue(1.0F);
        }
    }
    //#endif
}
