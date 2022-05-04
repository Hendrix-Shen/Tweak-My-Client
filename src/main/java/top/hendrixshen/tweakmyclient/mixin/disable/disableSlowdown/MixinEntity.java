package top.hendrixshen.tweakmyclient.mixin.disable.disableSlowdown;

//#if MC < 11500
//$$ import net.minecraft.client.Minecraft;
//#else
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
//#endif
//#if MC >= 11700
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
//#endif

import org.spongepowered.asm.mixin.Mixin;
//#if MC >= 11500
//#if MC >= 11700
import org.spongepowered.asm.mixin.Shadow;
//#endif
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
//#if MC >= 11700
import org.spongepowered.asm.mixin.injection.Redirect;
//#endif
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.config.Configs;
//#endif
//#if MC >= 11500

@Mixin(Entity.class)
//#else
//$$ @Mixin(Minecraft.class)
//#endif
public class MixinEntity {
    //#if MC >= 11700
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
    //#endif

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
