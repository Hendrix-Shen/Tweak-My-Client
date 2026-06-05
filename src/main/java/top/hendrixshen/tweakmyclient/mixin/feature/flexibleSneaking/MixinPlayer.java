package top.hendrixshen.tweakmyclient.mixin.feature.flexibleSneaking;

import top.hendrixshen.magiclib.api.compat.minecraft.world.entity.EntityCompat;
import top.hendrixshen.tweakmyclient.game.Configs;

// CHECKSTYLE.OFF: ImportOrder
//#if MC < 1.19.4
import org.objectweb.asm.Opcodes;
//#endif
// CHECKSTYLE.ON: ImportOrder

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.LavaFluid;

// CHECKSTYLE.OFF: ImportOrder
//#if MC < 1.20.5
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
//#endif

//#if MC >= 1.15
import net.minecraft.world.entity.player.Player;
//#endif
// CHECKSTYLE.ON: ImportOrder

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.sugar.Share;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;

// CHECKSTYLE.OFF: ImportOrder
//#if MC >= 1.20.5
//$$ import org.spongepowered.asm.mixin.Shadow;
//#endif
// CHECKSTYLE.ON: ImportOrder

@Mixin(
        //#if MC >= 1.15
        Player.class
        //#else
        //$$ Entity.class
        //#endif
)
public abstract class MixinPlayer {
    @Unique
    private static final float tmc$MAX_STEP_HEIGHT = 1.2F;

    //#if MC >= 1.20.5
    //$$ @Shadow
    //$$ protected abstract boolean canFallAtLeast(
    //$$         double deltaX,
    //$$         double deltaZ,
    //$$         //#if MC >= 1.21.5
    //$$         //$$ double minHeight
    //$$         //#else
    //$$         float minHeight
    //$$         //#endif
    //$$ );
    //#endif

    @WrapOperation(
            method = "maybeBackOffFromEdge",
            at = @At(
                    //#if MC >= 1.19.4
                    //$$ value = "INVOKE",
                    //$$ target = "Lnet/minecraft/world/entity/player/Player;maxUpStep()F"
                    //#else
                    value = "FIELD",
                    //#if MC >= 1.15
                    target = "Lnet/minecraft/world/entity/player/Player;maxUpStep:F",
                    //#else
                    //$$ target = "Lnet/minecraft/world/entity/Entity;maxUpStep:F",
                    //#endif
                    opcode = Opcodes.GETFIELD
                    //#endif
            )
    )
    private float fakeStepHeight(
            //#if MC >= 1.15
            Player instance,
            //#else
            //$$ Entity instance,
            //#endif
            Operation<Float> original,
            @Share("originalMaxUpStep") LocalFloatRef originalMaxUpStep
    ) {
        if (!Configs.flexibleSneaking.getBooleanValue() || !(instance instanceof LocalPlayer)) {
            return original.call(instance);
        }

        originalMaxUpStep.set(original.call(instance));
        return MixinPlayer.tmc$MAX_STEP_HEIGHT;
    }

    @WrapOperation(
            method = "maybeBackOffFromEdge",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 1.21.5
                    //$$ target = "Lnet/minecraft/world/entity/player/Player;canFallAtLeast(DDD)Z"
                    //#elseif MC >= 1.20.5
                    //$$ target = "Lnet/minecraft/world/entity/player/Player;canFallAtLeast(DDF)Z"
                    //#else
                    target = "Lnet/minecraft/world/level/Level;noCollision(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Z"
                    //#endif
            )
    )
    private boolean checkFallAtLava(
            //#if MC >= 1.20.5
            //$$ Player entity,
            //$$ double deltaX,
            //$$ double deltaZ,
            //#if MC >= 1.21.5
            //$$ double minHeight,
            //#else
            //$$ float minHeight,
            //#endif
            //#else
            Level level,
            Entity entity,
            AABB aabb,
            //#endif
            Operation<Boolean> original,
            @Share("originalMaxUpStep") LocalFloatRef originalMaxUpStep
    ) {
        EntityCompat entityCompat = EntityCompat.of(entity);
        //#if MC >= 1.20.5
        //$$ Level level = entity.level();
        //#endif

        boolean result = original.call(
                //#if MC >= 1.20.5
                //$$ entity,
                //$$ deltaX,
                //$$ deltaZ,
                //$$ minHeight
                //#else
                level,
                entity,
                aabb
                //#endif
        );

        if (!Configs.flexibleSneaking.getBooleanValue() || !(entity instanceof LocalPlayer)) {
            return result;
        }

        if (level.getFluidState(entityCompat.getBlockPosition().below()).getType() instanceof LavaFluid) {
            // boolean originalResult this.canFallAtLeast(deltaX, deltaZ, originalMaxUpStep.get());
            boolean originalResult = original.call(
                    //#if MC >= 1.20.5
                    //$$ entity,
                    //$$ deltaX,
                    //$$ deltaZ,
                    //#if MC >= 1.20.5
                    //$$ (double) originalMaxUpStep.get()
                    //#else
                    //$$ originalMaxUpStep.get()
                    //#endif
                    //#else
                    level,
                    entity,
                    aabb.move(0, MixinPlayer.tmc$MAX_STEP_HEIGHT - originalMaxUpStep.get(), 0)
                    //#endif
            );

            return originalResult && !result;
        }

        return result;
    }
}
