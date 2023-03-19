package top.hendrixshen.tweakmyclient.mixin.disable.disableSlowdown;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.MiscUtil;

//#if MC > 11404
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//#endif

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

    //#if MC > 11404
    @SuppressWarnings("ConstantConditions")
    @Inject(
            method = "getBlockSpeedFactor",
            at = @At(
                    value = "RETURN",
                    ordinal = 1
            ),
            cancellable = true
    )
    private void onGetBlockSpeedFactor(CallbackInfoReturnable<Float> cir) {
        if (Configs.disableSlowdown && MiscUtil.cast(this) instanceof LocalPlayer && cir.getReturnValueF() < 1.0F) {
            cir.setReturnValue(1.0F);
        }
    }
    //#endif
}
