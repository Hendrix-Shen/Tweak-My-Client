package top.hendrixshen.tweakmyclient.compat.mixin.patch.endPortalRendererFix;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.helper.EnderPortalRenderMode;

@Mixin(TheEndPortalRenderer.class)
public abstract class MixinTheEndPortalRenderer extends BlockEntityRenderer<TheEndPortalBlockEntity> {
    @Shadow @Final
    private static ResourceLocation END_SKY_LOCATION;

    private void renderFace(TheEndPortalBlockEntity theEndPortalBlockEntity, BufferBuilder bufferBuilder, double f, double g, double h, double i, double j, double k,
                            double l, double m, float n, float o, float p, Direction direction) {
        if (theEndPortalBlockEntity.shouldRenderFace(direction)) {
            bufferBuilder.vertex(f, h, j).color(n, o, p, 1.0F).endVertex();
            bufferBuilder.vertex(g, h, k).color(n, o, p, 1.0F).endVertex();
            bufferBuilder.vertex(g, i, l).color(n, o, p, 1.0F).endVertex();
            bufferBuilder.vertex(f, i, m).color(n, o, p, 1.0F).endVertex();
        }
    }

    @Inject(
            method = "render(Lnet/minecraft/world/level/block/entity/TheEndPortalBlockEntity;DDDFI)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/entity/TheEndPortalBlockEntity;shouldRenderFace(Lnet/minecraft/core/Direction;)Z",
                    ordinal = 0
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void onRenderCube(TheEndPortalBlockEntity theEndPortalBlockEntity, double d, double e, double f, float arg4, int arg5, CallbackInfo ci, double h, int j, float k, boolean bl, GameRenderer gameRenderer, int l, float m, float n, Tesselator tesselator, BufferBuilder bufferBuilder, float o, float p, float q) {
        if (Configs.endPortalRendererFix && Configs.enderPortalRenderMode != EnderPortalRenderMode.LEGACY) {
            if (Configs.enderPortalRenderMode == EnderPortalRenderMode.ACTUAL) {
                this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 0.0F, d + 1.0F, e + 0.0F, e + 0.75, f + 1.0F, f + 1.0F, f + 1.0F, f + 1.0F, o, p, q, Direction.SOUTH);
                this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 0.0F, d + 1.0F, e + 0.75, e + 0.0F, f + 0.0F, f + 0.0F, f + 0.0F, f + 0.0F, o, p, q, Direction.NORTH);
                this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 1.0F, d + 1.0F, e + 0.75, e + 0.0F, f + 0.0F, f + 1.0F, f + 1.0F, f + 0.0F, o, p, q, Direction.EAST);
                this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 0.0F, d + 0.0F, e + 0.0F, e + 0.75, f + 0.0F, f + 1.0F, f + 1.0F, f + 0.0F, o, p, q, Direction.WEST);
                this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 0.0F, d + 1.0F, e + 0.0F, e + 0.0F, f + 0.0F, f + 0.0F, f + 1.0F, f + 1.0F, o, p, q, Direction.DOWN);
                this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 0.0F, d + 1.0F, e + 0.75, e + 0.75, f + 1.0F, f + 1.0F, f + 0.0F, f + 0.0F, o, p, q, Direction.UP);
            } else if (Configs.enderPortalRenderMode == EnderPortalRenderMode.FULL) {
                // Rendering the end portal as a full block.
                this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 0.0F, d + 1.0F, e + 0.0F, e + 1.0F, f + 1.0F, f + 1.0F, f + 1.0F, f + 1.0F, o, p, q, Direction.SOUTH);
                this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 0.0F, d + 1.0F, e + 1.0F, e + 0.0F, f + 0.0F, f + 0.0F, f + 0.0F, f + 0.0F, o, p, q, Direction.NORTH);
                this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 1.0F, d + 1.0F, e + 1.0F, e + 0.0F, f + 0.0F, f + 1.0F, f + 1.0F, f + 0.0F, o, p, q, Direction.EAST);
                this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 0.0F, d + 0.0F, e + 0.0F, e + 1.0F, f + 0.0F, f + 1.0F, f + 1.0F, f + 0.0F, o, p, q, Direction.WEST);
                this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 0.0F, d + 1.0F, e + 0.0F, e + 0.0F, f + 0.0F, f + 0.0F, f + 1.0F, f + 1.0F, o, p, q, Direction.DOWN);
                this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 0.0F, d + 1.0F, e + 1.0F, e + 1.0F, f + 1.0F, f + 1.0F, f + 0.0F, f + 0.0F, o, p, q, Direction.UP);
            } else if (Configs.enderPortalRenderMode == EnderPortalRenderMode.MODERN) {
                // Rendering the end portal with Minecraft 21w13a and above.
                this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 0.0F, d + 1.0F, e + 0.0F, e + 1.0F, f + 1.0F, f + 1.0F, f + 1.0F, f + 1.0F, o, p, q, Direction.SOUTH);
                this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 0.0F, d + 1.0F, e + 1.0F, e + 0.0F, f + 0.0F, f + 0.0F, f + 0.0F, f + 0.0F, o, p, q, Direction.NORTH);
                this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 1.0F, d + 1.0F, e + 1.0F, e + 0.0F, f + 0.0F, f + 1.0F, f + 1.0F, f + 0.0F, o, p, q, Direction.EAST);
                this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 0.0F, d + 0.0F, e + 0.0F, e + 1.0F, f + 0.0F, f + 1.0F, f + 1.0F, f + 0.0F, o, p, q, Direction.WEST);
                this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 0.0F, d + 1.0F, e + 0.375F, e + 0.375F, f + 0.0F, f + 0.0F, f + 1.0F, f + 1.0F, o, p, q, Direction.DOWN);
                this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 0.0F, d + 1.0F, e + 1.0F, e + 1.0F, f + 1.0F, f + 1.0F, f + 0.0F, f + 0.0F, o, p, q, Direction.UP);
            }

            tesselator.end();
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
            this.bindTexture(END_SKY_LOCATION);

            GlStateManager.disableBlend();
            GlStateManager.disableTexGen(GlStateManager.TexGen.S);
            GlStateManager.disableTexGen(GlStateManager.TexGen.T);
            GlStateManager.disableTexGen(GlStateManager.TexGen.R);
            GlStateManager.enableLighting();

            if (bl) {
                TweakMyClient.getMinecraftClient().gameRenderer.resetFogColor(false);
            }

            ci.cancel();
        }
    }
}
