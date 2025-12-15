package top.hendrixshen.tweakmyclient.mixin.feature.crystalBeamsRenderRestriction;

import org.objectweb.asm.Opcodes;
import top.hendrixshen.tweakmyclient.game.Configs;
import top.hendrixshen.tweakmyclient.impl.feature.crystalBeamRenderRestriction.CrystalBeamRenderRestrictionMode;

import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

// CHECKSTYLE.OFF: ImportOrder
//#if MC < 12103
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
//#endif

//#if MC > 12101
//$$ import net.minecraft.client.renderer.entity.state.EnderDragonRenderState;
//$$ import net.minecraft.world.phys.Vec3;
//#endif

//#if MC > 11605
//$$ import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
//#else
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
//#endif
// CHECKSTYLE.ON: ImportOrder

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.ModifyExpressionValue;

@Mixin(EnderDragonRenderer.class)
public abstract class MixinEnderDragonRenderer extends EntityRenderer<
        // CHECKSTYLE.OFF: NoWhitespaceBefore
        // CHECKSTYLE.OFF: SeparatorWrap
        EnderDragon
        //#if MC > 12101
        //$$ , EnderDragonRenderState
        //#endif
        // CHECKSTYLE.ON: SeparatorWrap
        // CHECKSTYLE.ON: NoWhitespaceBefore
        > {
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
            //#if MC >= 12110
            //$$ method = "submit(Lnet/minecraft/client/renderer/entity/state/EnderDragonRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V",
            //#elseif MC > 12101
            //$$ method = "render(Lnet/minecraft/client/renderer/entity/state/EnderDragonRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            //#elseif MC > 11404
            method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            //#else
            //$$ method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;DDDFF)V",
            //#endif
            at = @At(
                    value = "FIELD",
                    //#if MC > 12101
                    //$$ target = "Lnet/minecraft/client/renderer/entity/state/EnderDragonRenderState;beamOffset:Lnet/minecraft/world/phys/Vec3;",
                    //#else
                    target = "Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;nearestCrystal:Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;",
                    //#endif
                    opcode = Opcodes.GETFIELD
            )
    )
    // CHECKSTYLE.OFF: Indentation
    // @formatter:off
    private
    //#if MC > 12101
    //$$ Vec3
    //#else
    EndCrystal
    //#endif
    onRenderCrystalBeams(
            //#if MC > 12101
            //$$ Vec3 original
            //#else
            EndCrystal original
            //#endif
    ) {
        // @formatter:on
        // CHECKSTYLE.OFF: Indentation
        if (Configs.crystalBeamRenderRestriction.getBooleanValue() && !((CrystalBeamRenderRestrictionMode) Configs.crystalBeamRenderRestrictionType.getOptionListValue()).isCrystalBeamAllow()) {
            return null;
        }

        return original;
    }
}
