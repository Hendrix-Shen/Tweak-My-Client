package top.hendrixshen.tweakmyclient.mixin.feature.crystalBeamsRenderRestriction;

import top.hendrixshen.tweakmyclient.game.Configs;
import top.hendrixshen.tweakmyclient.impl.feature.crystalBeamRenderRestriction.CrystalBeamRenderRestrictionMode;

// CHECKSTYLE.OFF: ImportOrder
//#if MC >= 12102
//$$ import org.objectweb.asm.Opcodes;
//#endif
// CHECKSTYLE.ON: ImportOrder

import net.minecraft.client.renderer.entity.EndCrystalRenderer;

// CHECKSTYLE.OFF: ImportOrder
//#if MC >= 12102
//$$ import net.minecraft.world.phys.Vec3;
//#else
import net.minecraft.core.BlockPos;
//#endif
// CHECKSTYLE.ON: ImportOrder

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.ModifyExpressionValue;

@Mixin(EndCrystalRenderer.class)
public abstract class MixinEndCrystalRenderer {
    @ModifyExpressionValue(
            //#if MC >= 12110
            //$$ method = "submit(Lnet/minecraft/client/renderer/entity/state/EndCrystalRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V",
            //#elseif MC >= 12102
            //$$ method = "render(Lnet/minecraft/client/renderer/entity/state/EndCrystalRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            //#elseif MC > 11404
            method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            //#else
            //$$ method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;DDDFF)V",
            //#endif
            at = @At(
                    //#if MC >= 12102
                    //$$ value = "FIELD",
                    //$$ target = "Lnet/minecraft/client/renderer/entity/state/EndCrystalRenderState;beamOffset:Lnet/minecraft/world/phys/Vec3;",
                    //$$ opcode = Opcodes.GETFIELD
                    //#else
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;getBeamTarget()Lnet/minecraft/core/BlockPos;"
                    //#endif
            )
    )
    // CHECKSTYLE.OFF: Indentation
    // @formatter:off
    private
    //#if MC >= 12102
    //$$ Vec3
    //#else
    BlockPos
    //#endif
    onRenderCrystalBeams(
            //#if MC >= 12102
            //$$ Vec3 original
            //#else
            BlockPos original
            //#endif
    ) {
        // @formatter:on
        // CHECKSTYLE.OFF: Indentation
        if (Configs.crystalBeamRenderRestriction.getBooleanValue()
                && !((CrystalBeamRenderRestrictionMode) Configs.crystalBeamRenderRestrictionType.getOptionListValue()).isCrystalBeamAllow()) {
            return null;
        }

        return original;
    }
}
