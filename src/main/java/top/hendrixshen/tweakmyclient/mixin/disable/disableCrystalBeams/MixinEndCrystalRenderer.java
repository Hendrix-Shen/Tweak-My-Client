package top.hendrixshen.tweakmyclient.mixin.disable.disableCrystalBeams;

import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.helper.CrystalBeamsDisableMode;

//#if MC > 11404
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
//#endif

//#if MC > 11605
import net.minecraft.client.renderer.entity.EntityRendererProvider;
//#else
//$$ import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
//#endif

@Mixin(EndCrystalRenderer.class)
public abstract class MixinEndCrystalRenderer extends EntityRenderer<EndCrystal> {
    //#if MC > 11605
    protected MixinEndCrystalRenderer(EntityRendererProvider.Context context) {
        super(context);
    //#else
    //$$ protected MixinEndCrystalRenderer(EntityRenderDispatcher entityRenderDispatcher) {
    //$$ super(entityRenderDispatcher);
    //#endif
    }

    @Inject(
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
            ),
            cancellable = true
    )
    //#if MC > 11404
    private void onRenderCrystalBeams(EndCrystal endCrystal, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
    //#else
    //$$ private void onRenderCrystalBeams(EndCrystal endCrystal, double d, double e, double f, float g, float h, CallbackInfo ci) {
    //#endif
        if (Configs.disableCrystalBeams) {
            if (Configs.crystalBeamsDisableMode == CrystalBeamsDisableMode.TRACKING) {
                return;
            }

            //#if MC > 11404
            super.render(endCrystal, f, g, poseStack, multiBufferSource, i);
            //#else
            //$$ super.render(endCrystal, d, e, f, g, h);
            //#endif
            ci.cancel();
        }
    }
}
