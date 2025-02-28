package top.hendrixshen.tweakmyclient.mixin.feature.crystalBeamsRenderRestriction;

import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.game.Configs;

//#if MC > 11605
//$$ import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
//#else
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
//#endif

//#if MC > 11404
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import top.hendrixshen.tweakmyclient.impl.feature.crystalBeamsRenderRestriction.CrystalBeamsRenderRestrictionMode;
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

    @Inject(
            //#if MC > 11404
            method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            //#else
            //$$ method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;DDDFF)V",
            //#endif
            at = @At(
                    value = "INVOKE",
                    //#if MC > 11404
                    target = "Lnet/minecraft/client/renderer/entity/EnderDragonRenderer;renderCrystalBeams(FFFFILcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"
                    //#else
                    //$$ target = "Lnet/minecraft/client/renderer/entity/EnderDragonRenderer;renderCrystalBeams(DDDFDDDIDDD)V"
                    //#endif
            ),
            cancellable = true
    )
    private void onRenderCrystalBeams(
            EnderDragon enderDragon,
            //#if MC < 1500
            //$$ double cameraX,
            //$$ double cameraY,
            //$$ double cameraZ,
            //#endif
            float entityYaw,
            float partialTicks,
            //#if MC > 11404
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            //#endif
            CallbackInfo ci
    ) {
        if (!Configs.crystalBeamsRenderRestriction.getBooleanValue()
                || ((CrystalBeamsRenderRestrictionMode) Configs.crystalBeamsRenderRestrictionType.getOptionListValue()).isCrystalBeamAllow()) {
            return;
        }

        super.render(
                enderDragon,
                //#if MC < 1500
                //$$ cameraX,
                //$$ cameraY,
                //$$ cameraZ,
                //#endif
                entityYaw,
                partialTicks,
                //#if MC > 11404
                poseStack,
                buffer,
                packedLight
                //#endif
        );
        poseStack.popPose();
        ci.cancel();
    }
}
