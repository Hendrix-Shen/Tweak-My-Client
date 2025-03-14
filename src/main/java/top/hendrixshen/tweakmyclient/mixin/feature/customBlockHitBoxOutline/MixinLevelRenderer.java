package top.hendrixshen.tweakmyclient.mixin.feature.customBlockHitBoxOutline;

import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.game.Configs;

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
    @Inject(method = "renderHitOutline", at = @At(value = "HEAD"), cancellable = true)
    private void onDrawBlockOutline(
            //#if MC > 11404
            PoseStack poseStack,
            VertexConsumer vertexConsumer,
            Entity entity,
            double camX,
            double camY,
            double camZ,
            BlockPos blockPos,
            BlockState blockState,
            //#if MC > 12102
            //$$ int color,
            //#endif
            //#else
            //$$ Camera camera,
            //$$ HitResult hitResult,
            //$$ int i,
            //#endif
            CallbackInfo ci
    ) {
        if (Configs.customBlockHitBoxOutline.getBooleanValue()) {
            ci.cancel();
        }
    }
}
