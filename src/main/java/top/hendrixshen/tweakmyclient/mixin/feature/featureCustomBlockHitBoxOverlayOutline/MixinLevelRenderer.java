package top.hendrixshen.tweakmyclient.mixin.feature.featureCustomBlockHitBoxOverlayOutline;

import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

//#if MC > 11404
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
//#else
//$$ import net.minecraft.client.Camera;
//$$ import net.minecraft.world.phys.HitResult;
//#endif

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer {
    @Inject(
            method = "renderHitOutline",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    //#if MC > 11404
    private void onDrawBlockOutline(PoseStack poseStack, VertexConsumer vertexConsumer, Entity entity, double d, double e, double f, BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
    //#else
    //$$ private void onDrawBlockOutline(Camera camera, HitResult hitResult, int i, CallbackInfo ci) {
    //#endif
        if (Configs.featureCustomBlockHitBoxOverlayOutline) {
            ci.cancel();
        }
    }
}
