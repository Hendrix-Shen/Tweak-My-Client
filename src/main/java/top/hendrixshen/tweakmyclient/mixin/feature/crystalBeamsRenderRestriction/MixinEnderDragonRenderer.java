package top.hendrixshen.tweakmyclient.mixin.feature.crystalBeamsRenderRestriction;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.tweakmyclient.game.Configs;
import top.hendrixshen.tweakmyclient.impl.feature.crystalBeamsRenderRestriction.CrystalBeamsRenderRestrictionMode;

//#if MC > 11605
//$$ import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
//#else
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
//#endif

@Mixin(EnderDragonRenderer.class)
public abstract class MixinEnderDragonRenderer extends EntityRenderer<EnderDragon> {
    protected MixinEnderDragonRenderer(
            //#if MC > 11605
            //$$ Context context
            //#else
            EntityRenderDispatcher entityRenderDispatcher
            //#endif
    ) {
        super(
                //#if MC > 11605
                //$$ context
                //#else
                entityRenderDispatcher
                //#endif
        );
    }

    @ModifyExpressionValue(
            //#if MC > 11404
            method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            //#else
            //$$ method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;DDDFF)V",
            //#endif
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;nearestCrystal:Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;",
                    opcode = Opcodes.GETFIELD
            )
    )
    private EndCrystal onRenderCrystalBeams(EndCrystal original) {
        if (Configs.crystalBeamsRenderRestriction.getBooleanValue() && !((CrystalBeamsRenderRestrictionMode) Configs.crystalBeamsRenderRestrictionType.getOptionListValue()).isCrystalBeamAllow()) {
            return null;
        }

        return original;
    }
}
