package top.hendrixshen.tweakmyclient.mixin.feature.crystalBeamsRenderRestriction;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.tweakmyclient.game.Configs;
import top.hendrixshen.tweakmyclient.impl.feature.crystalBeamsRenderRestriction.CrystalBeamsRenderRestrictionMode;

@Mixin(EndCrystalRenderer.class)
public abstract class MixinEndCrystalRenderer {
    @WrapWithCondition(
            //#if MC > 11404
            method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            //#else
            //$$ method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;DDDFF)V",
            //#endif
            at = @At(
                    value = "INVOKE",
                    //#if MC > 11404
                    target = "Lnet/minecraft/client/renderer/entity/EnderDragonRenderer;renderCrystalBeams(FFFFILcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"
                    //#else
                    //$$ target = "Lnet/minecraft/client/renderer/entity/EnderDragonRenderer;renderCrystalBeams(DDDFDDDIDDD)V"
                    //#endif
            )
    )
    private boolean onRenderCrystalBeams(float x, float y, float z, float partialTick, int tickCount, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
        return !Configs.crystalBeamsRenderRestriction.getBooleanValue()
                || ((CrystalBeamsRenderRestrictionMode) Configs.crystalBeamsRenderRestrictionType.getOptionListValue()).isCrystalBeamAllow();
    }
}
