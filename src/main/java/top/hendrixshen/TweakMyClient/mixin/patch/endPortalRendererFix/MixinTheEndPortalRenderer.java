package top.hendrixshen.TweakMyClient.mixin.patch.endPortalRendererFix;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.util.render.EnderPortalRenderMode;

@Mixin(TheEndPortalRenderer.class)
public abstract class MixinTheEndPortalRenderer {
    @Shadow
    protected abstract void renderFace(TheEndPortalBlockEntity theEndPortalBlockEntity, Matrix4f matrix4f, VertexConsumer vertexConsumer, float f, float g, float h, float i, float j, float k, float l, float m, Direction direction);

    @Inject(
            method = "renderCube",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/blockentity/TheEndPortalRenderer;renderFace(Lnet/minecraft/world/level/block/entity/TheEndPortalBlockEntity;Lcom/mojang/math/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFLnet/minecraft/core/Direction;)V",
                    ordinal = 0
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void onRenderCube(TheEndPortalBlockEntity theEndPortalBlockEntity, Matrix4f matrix4f, VertexConsumer vertexConsumer, CallbackInfo ci, float f, float g) {
        if (Configs.Patch.ENDER_PORTAL_RENDERER_FIX.getBooleanValue() && Configs.Generic.ENDER_PORTAL_RENDER_MODE.getOptionListValue() != EnderPortalRenderMode.MODERN) {
            if (Configs.Generic.ENDER_PORTAL_RENDER_MODE.getOptionListValue() == EnderPortalRenderMode.ACTUAL) {
                // Render the ender portal using its hit box.
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, f, g, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, g, f, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 1.0F, 1.0F, g, f, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 0.0F, f, g, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, f, f, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, g, g, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
            } else if (Configs.Generic.ENDER_PORTAL_RENDER_MODE.getOptionListValue() == EnderPortalRenderMode.FULL) {
                // Render the end portal as a full block.
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
            } else if (Configs.Generic.ENDER_PORTAL_RENDER_MODE.getOptionListValue() == EnderPortalRenderMode.LEGACY) {
                // Minecraft 21w13a below.
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, g, g, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
            }
            ci.cancel();
        }
    }
}
