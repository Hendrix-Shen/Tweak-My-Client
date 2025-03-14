package top.hendrixshen.tweakmyclient.mixin.feature.crystalBeamsRenderRestriction;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.tweakmyclient.game.Configs;
import top.hendrixshen.tweakmyclient.impl.feature.crystalBeamsRenderRestriction.CrystalBeamsRenderRestrictionMode;

//#if MC > 12101
//$$ import net.minecraft.world.phys.Vec3;
//$$ import org.objectweb.asm.Opcodes;
//#endif

@Mixin(EndCrystalRenderer.class)
public abstract class MixinEndCrystalRenderer {
    @ModifyExpressionValue(
            //#if MC > 12101
            //$$ method = "render(Lnet/minecraft/client/renderer/entity/state/EndCrystalRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            //#elseif MC > 11404
            method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            //#else
            //$$ method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;DDDFF)V",
            //#endif
            at = @At(
                    //#if MC > 12101
                    //$$ value = "FIELD",
                    //$$ target = "Lnet/minecraft/client/renderer/entity/state/EndCrystalRenderState;beamOffset:Lnet/minecraft/world/phys/Vec3;",
                    //$$ opcode = Opcodes.GETFIELD
                    //#else
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;getBeamTarget()Lnet/minecraft/core/BlockPos;"
                    //#endif
            )
    )
    private
    //#if MC > 12101
    //$$ Vec3
    //#else
    BlockPos
    //#endif
    onRenderCrystalBeams(
            //#if MC > 12101
            //$$ Vec3 original
            //#else
            BlockPos original
            //#endif
    ) {
        if (Configs.crystalBeamsRenderRestriction.getBooleanValue() && !((CrystalBeamsRenderRestrictionMode) Configs.crystalBeamsRenderRestrictionType.getOptionListValue()).isCrystalBeamAllow()) {
            return null;
        }

        return original;
    }
}
