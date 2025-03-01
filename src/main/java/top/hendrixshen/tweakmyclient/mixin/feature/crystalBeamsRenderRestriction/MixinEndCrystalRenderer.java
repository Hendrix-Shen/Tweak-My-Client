package top.hendrixshen.tweakmyclient.mixin.feature.crystalBeamsRenderRestriction;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.tweakmyclient.game.Configs;
import top.hendrixshen.tweakmyclient.impl.feature.crystalBeamsRenderRestriction.CrystalBeamsRenderRestrictionMode;

@Mixin(EndCrystalRenderer.class)
public abstract class MixinEndCrystalRenderer {
    @ModifyExpressionValue(
            //#if MC > 11404
            method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            //#else
            //$$ method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;DDDFF)V",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;getBeamTarget()Lnet/minecraft/core/BlockPos;"
            )
    )
    private BlockPos onRenderCrystalBeams(BlockPos original) {
        if (Configs.crystalBeamsRenderRestriction.getBooleanValue() && !((CrystalBeamsRenderRestrictionMode) Configs.crystalBeamsRenderRestrictionType.getOptionListValue()).isCrystalBeamAllow()) {
            return null;
        }

        return original;
    }
}
