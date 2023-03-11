package top.hendrixshen.tweakmyclient.mixin.disable.disableCrystalBeams;

//#if MC >= 11500
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
//#endif
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
//#if MC < 11700
//$$ import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
//#endif
import net.minecraft.client.renderer.entity.EntityRenderer;
//#if MC >= 11700
import net.minecraft.client.renderer.entity.EntityRendererProvider;
//#endif
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.helper.CrystalBeamsDisableMode;

@Mixin(EnderDragonRenderer.class)
public abstract class MixinEnderDragonRenderer extends EntityRenderer<EnderDragon> {
    //#if MC >= 11700
    protected MixinEnderDragonRenderer(EntityRendererProvider.Context context) {
        super(context);
        //#else
        //$$ protected MixinEnderDragonRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        //$$ super(entityRenderDispatcher);
        //#endif
    }

    @Inject(
            //#if MC >= 11500
            method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            //#else
            //$$ method = "render(Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;DDDFF)V",
            //#endif
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 11500
                    target = "Lnet/minecraft/client/renderer/entity/EnderDragonRenderer;renderCrystalBeams(FFFFILcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"
                    //#else
                    //$$ target = "Lnet/minecraft/client/renderer/entity/EnderDragonRenderer;renderCrystalBeams(DDDFDDDIDDD)V"
                    //#endif
            ),
            cancellable = true
    )
    //#if MC >= 11500
    private void onRenderCrystalBeams(EnderDragon enderDragon, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
    //#else
    //$$ private void onRenderCrystalBeams(EnderDragon enderDragon, double d, double e, double f, float g, float h, CallbackInfo ci) {
    //#endif
        if (Configs.disableCrystalBeams) {
            if (Configs.crystalBeamsDisableMode == CrystalBeamsDisableMode.FIXED) {
                return;
            }

            //#if MC >= 11500
            poseStack.popPose();
            super.render(enderDragon, f, g, poseStack, multiBufferSource, i);

            //#endif
            ci.cancel();
        }
    }
}
