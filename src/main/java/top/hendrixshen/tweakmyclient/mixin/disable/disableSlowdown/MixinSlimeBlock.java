package top.hendrixshen.tweakmyclient.mixin.disable.disableSlowdown;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlimeBlock;

// CHECKSTYLE.OFF: ImportOrder
//#if MC < 26.2
import net.minecraft.world.phys.Vec3;
//#endif
// CHECKSTYLE.ON: ImportOrder

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.sugar.Local;

@Mixin(SlimeBlock.class)
public abstract class MixinSlimeBlock extends Block {
    public MixinSlimeBlock(Properties properties) {
        super(properties);
    }

    //#if MC < 26.2
    @Inject(
            //#if MC > 11404
            method = "bounceUp",
            //#else
            //$$ method = "updateEntityAfterFallOn",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;getDeltaMovement()Lnet/minecraft/world/phys/Vec3;"
            ),
            cancellable = true
    )
    private void makeVerticalVelocityBalance(CallbackInfo ci, @Local(argsOnly = true) Entity entity) {
        if (Configs.disableSlowdown.getBooleanValue() && entity instanceof LocalPlayer) {
            Vec3 vec3 = entity.getDeltaMovement();

            // Vertical momentum at 2x steady state.
            if (vec3.y < 0 && vec3.y > -0.0792) {
                entity.setDeltaMovement(vec3.x, 0, vec3.z);
                ci.cancel();
            }
        }
    }
    //#endif

    @Inject(method = "stepOn", at = @At("HEAD"), cancellable = true)
    private void stepOn(CallbackInfo ci, @Local(argsOnly = true) Entity entity) {
        if (Configs.disableSlowdown.getBooleanValue() && entity instanceof LocalPlayer) {
            ci.cancel();
        }
    }
}
