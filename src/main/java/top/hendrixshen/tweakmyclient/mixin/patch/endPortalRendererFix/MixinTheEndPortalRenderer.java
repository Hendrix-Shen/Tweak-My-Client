package top.hendrixshen.tweakmyclient.mixin.patch.endPortalRendererFix;

import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.helper.EnderPortalRenderMode;


//#if MC > 11404
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
//#else
//$$ import com.mojang.blaze3d.platform.GlStateManager;
//$$ import com.mojang.blaze3d.vertex.BufferBuilder;
//$$ import com.mojang.blaze3d.vertex.DefaultVertexFormat;
//$$ import com.mojang.blaze3d.vertex.Tesselator;
//$$ import net.minecraft.Util;
//$$ import net.minecraft.client.Minecraft;
//$$ import net.minecraft.client.renderer.GameRenderer;
//$$ import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
//$$ import net.minecraft.resources.ResourceLocation;
//$$ import org.spongepowered.asm.mixin.Final;
//$$ import org.spongepowered.asm.mixin.Overwrite;
//$$ import java.nio.FloatBuffer;
//$$ import java.util.Random;
//#endif

@Mixin(TheEndPortalRenderer.class)
//#if MC > 11404
public abstract class MixinTheEndPortalRenderer {
//#else
//$$ public abstract class MixinTheEndPortalRenderer extends BlockEntityRenderer<TheEndPortalBlockEntity> {
//#endif
    //#if MC > 11605
    @Shadow
    protected abstract void renderFace(TheEndPortalBlockEntity theEndPortalBlockEntity, Matrix4f matrix4f, VertexConsumer vertexConsumer, float f, float g, float h, float i, float j, float k, float l, float m, Direction direction);
    //#elseif MC > 11404
    //$$ @Shadow
    //$$ protected abstract void renderFace(TheEndPortalBlockEntity theEndPortalBlockEntity, Matrix4f matrix4f, VertexConsumer vertexConsumer, float f, float g, float h, float i, float j, float k, float l, float m, float n, float o, float p, Direction direction);
    //#else
    //$$ @Shadow
    //$$ @Final
    //$$ private static ResourceLocation END_SKY_LOCATION;
    //$$
    //$$ @Shadow
    //$$ @Final
    //$$ private static Random RANDOM;
    //$$
    //$$ @Shadow
    //$$ @Final
    //$$ private static ResourceLocation END_PORTAL_LOCATION;
    //$$
    //$$ @Shadow
    //$$ protected abstract FloatBuffer getBuffer(float par1, float par2, float par3, float par4);
    //$$
    //$$ @Shadow
    //$$ @Final
    //$$ private static FloatBuffer MODELVIEW;
    //$$
    //$$ @Shadow
    //$$ @Final
    //$$ private static FloatBuffer PROJECTION;
    //$$
    //$$ @Shadow
    //$$ protected abstract int getPasses(double d);
    //$$
    //$$ @Shadow
    //$$ protected abstract float getOffset();
    //$$
    //$$ private void renderFace(TheEndPortalBlockEntity theEndPortalBlockEntity, BufferBuilder bufferBuilder, double f, double g, double h, double i, double j, double k,
    //$$                         double l, double m, float n, float o, float p, Direction direction) {
    //$$     if (theEndPortalBlockEntity.shouldRenderFace(direction)) {
    //$$         bufferBuilder.vertex(f, h, j).color(n, o, p, 1.0F).endVertex();
    //$$         bufferBuilder.vertex(g, h, k).color(n, o, p, 1.0F).endVertex();
    //$$         bufferBuilder.vertex(g, i, l).color(n, o, p, 1.0F).endVertex();
    //$$         bufferBuilder.vertex(f, i, m).color(n, o, p, 1.0F).endVertex();
    //$$     }
    //$$ }
    //#endif

    //#if MC > 11404
    @Inject(
            method = "renderCube",
            at = @At(
                    value = "INVOKE",
                    //#if MC > 11902
                    target = "Lnet/minecraft/client/renderer/blockentity/TheEndPortalRenderer;renderFace(Lnet/minecraft/world/level/block/entity/TheEndPortalBlockEntity;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFLnet/minecraft/core/Direction;)V",
                    //#elseif MC > 11605
                    //$$ target = "Lnet/minecraft/client/renderer/blockentity/TheEndPortalRenderer;renderFace(Lnet/minecraft/world/level/block/entity/TheEndPortalBlockEntity;Lcom/mojang/math/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFLnet/minecraft/core/Direction;)V",
                    //#else
                    //$$ target = "Lnet/minecraft/client/renderer/blockentity/TheEndPortalRenderer;renderFace(Lnet/minecraft/world/level/block/entity/TheEndPortalBlockEntity;Lcom/mojang/math/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFFFFLnet/minecraft/core/Direction;)V",
                    //#endif
                    ordinal = 0
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    //#endif

    //#if MC > 11605
    private void onRenderCube(TheEndPortalBlockEntity theEndPortalBlockEntity, Matrix4f matrix4f, VertexConsumer vertexConsumer, CallbackInfo ci, float f, float g) {
        if (Configs.endPortalRendererFix && Configs.enderPortalRenderMode != EnderPortalRenderMode.MODERN) {
            if (Configs.enderPortalRenderMode == EnderPortalRenderMode.ACTUAL) {
                // Render the ender portal using its hit box.
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, f, g, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, g, f, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 1.0F, 1.0F, g, f, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 0.0F, f, g, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, f, f, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, g, g, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
            } else if (Configs.enderPortalRenderMode == EnderPortalRenderMode.FULL) {
                // Render the end portal as a full block.
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
                this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
            } else if (Configs.enderPortalRenderMode == EnderPortalRenderMode.LEGACY) {
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
    //#elseif MC > 11404
    //$$ private void onRenderCube(TheEndPortalBlockEntity theEndPortalBlockEntity, float f, float g, Matrix4f matrix4f, VertexConsumer vertexConsumer, CallbackInfo ci, float h, float i, float j) {
    //$$     if (Configs.enderPortalRenderMode == EnderPortalRenderMode.ACTUAL) {
    //$$        // Rendering the ender portal using its hit box.
    //$$        this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0F, f, 1.0F, 1.0F, 1.0F, 1.0F, h, i, j, Direction.SOUTH);
    //$$        this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, f, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, h, i, j, Direction.NORTH);
    //$$        this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 1.0F, 1.0F, f, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, h, i, j, Direction.EAST);
    //$$        this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 0.0F, 0.0F, f, 0.0F, 1.0F, 1.0F, 0.0F, h, i, j, Direction.WEST);
    //$$        this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, h, i, j, Direction.DOWN);
    //$$        this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, f, f, 1.0F, 1.0F, 0.0F, 0.0F, h, i, j, Direction.UP);
    //$$     } else if (Configs.enderPortalRenderMode == EnderPortalRenderMode.FULL) {
    //$$        // Rendering the end portal as a full block.
    //$$        this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, h, i, j, Direction.SOUTH);
    //$$        this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, h, i, j, Direction.NORTH);
    //$$        this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, h, i, j, Direction.EAST);
    //$$        this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, h, i, j, Direction.WEST);
    //$$        this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, h, i, j, Direction.DOWN);
    //$$        this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, h, i, j, Direction.UP);
    //$$     } else if (Configs.enderPortalRenderMode == EnderPortalRenderMode.MODERN) {
    //$$        // Rendering the end portal with Minecraft 21w13a and above.
    //$$        this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, h, i, j, Direction.SOUTH);
    //$$        this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, h, i, j, Direction.NORTH);
    //$$        this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, h, i, j, Direction.EAST);
    //$$        this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, h, i, j, Direction.WEST);
    //$$        this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.375F, 0.375F, 0.0F, 0.0F, 1.0F, 1.0F, h, i, j, Direction.DOWN);
    //$$        this.renderFace(theEndPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, f, f, 1.0F, 1.0F, 0.0F, 0.0F, h, i, j, Direction.UP);
    //$$     }
    //$$        ci.cancel();
    //$$ }
    //#else
    //$$ /**
    //$$  * @author Hendrix-Shen
    //$$  * @reason Fix endPortalBlockEntity renderer.
    //$$  */
    //$$ @Overwrite
    //$$ public void render(TheEndPortalBlockEntity theEndPortalBlockEntity, double d, double e, double f, float g, int i) {
    //$$     GlStateManager.disableLighting();
    //$$     RANDOM.setSeed(31100L);
    //$$     GlStateManager.getMatrix(2982, MODELVIEW);
    //$$     GlStateManager.getMatrix(2983, PROJECTION);
    //$$     double h = d * d + e * e + f * f;
    //$$     int j = this.getPasses(h);
    //$$     float k = this.getOffset();
    //$$     boolean bl = false;
    //$$     GameRenderer gameRenderer = Minecraft.getInstance().gameRenderer;
    //$$
    //$$     for (int l = 0; l < j; ++l) {
    //$$         GlStateManager.pushMatrix();
    //$$         float m = 2.0F / (float) (18 - l);
    //$$         if (l == 0) {
    //$$             this.bindTexture(END_SKY_LOCATION);
    //$$             m = 0.15F;
    //$$             GlStateManager.enableBlend();
    //$$             GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    //$$         }
    //$$
    //$$         if (l >= 1) {
    //$$             this.bindTexture(END_PORTAL_LOCATION);
    //$$             bl = true;
    //$$             gameRenderer.resetFogColor(true);
    //$$         }
    //$$
    //$$         if (l == 1) {
    //$$             GlStateManager.enableBlend();
    //$$             GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
    //$$         }
    //$$
    //$$         GlStateManager.texGenMode(GlStateManager.TexGen.S, 9216);
    //$$         GlStateManager.texGenMode(GlStateManager.TexGen.T, 9216);
    //$$         GlStateManager.texGenMode(GlStateManager.TexGen.R, 9216);
    //$$         GlStateManager.texGenParam(GlStateManager.TexGen.S, 9474, this.getBuffer(1.0F, 0.0F, 0.0F, 0.0F));
    //$$         GlStateManager.texGenParam(GlStateManager.TexGen.T, 9474, this.getBuffer(0.0F, 1.0F, 0.0F, 0.0F));
    //$$         GlStateManager.texGenParam(GlStateManager.TexGen.R, 9474, this.getBuffer(0.0F, 0.0F, 1.0F, 0.0F));
    //$$         GlStateManager.enableTexGen(GlStateManager.TexGen.S);
    //$$         GlStateManager.enableTexGen(GlStateManager.TexGen.T);
    //$$         GlStateManager.enableTexGen(GlStateManager.TexGen.R);
    //$$         GlStateManager.popMatrix();
    //$$         GlStateManager.matrixMode(5890);
    //$$         GlStateManager.pushMatrix();
    //$$         GlStateManager.loadIdentity();
    //$$         GlStateManager.translatef(0.5F, 0.5F, 0.0F);
    //$$         GlStateManager.scalef(0.5F, 0.5F, 1.0F);
    //$$         float n = (float) (l + 1);
    //$$         GlStateManager.translatef(17.0F / n, (2.0F + n / 1.5F) * ((float) (Util.getMillis() % 800000L) / 800000.0F), 0.0F);
    //$$         GlStateManager.rotatef((n * n * 4321.0F + n * 9.0F) * 2.0F, 0.0F, 0.0F, 1.0F);
    //$$         GlStateManager.scalef(4.5F - n / 4.0F, 4.5F - n / 4.0F, 1.0F);
    //$$         GlStateManager.multMatrix(PROJECTION);
    //$$         GlStateManager.multMatrix(MODELVIEW);
    //$$         Tesselator tesselator = Tesselator.getInstance();
    //$$         BufferBuilder bufferBuilder = tesselator.getBuilder();
    //$$         bufferBuilder.begin(7, DefaultVertexFormat.POSITION_COLOR);
    //$$         float o = (RANDOM.nextFloat() * 0.5F + 0.1F) * m;
    //$$         float p = (RANDOM.nextFloat() * 0.5F + 0.4F) * m;
    //$$         float q = (RANDOM.nextFloat() * 0.5F + 0.5F) * m;
    //$$         if (Configs.enderPortalRenderMode == EnderPortalRenderMode.ACTUAL) {
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d + 1.0F, e, e + 0.75, f + 1.0F, f + 1.0F, f + 1.0F, f + 1.0F, o, p, q, Direction.SOUTH);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d + 1.0F, e + 0.75, e, f, f, f, f, o, p, q, Direction.NORTH);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 1.0F, d + 1.0F, e + 0.75, e, f, f + 1.0F, f + 1.0F, f, o, p, q, Direction.EAST);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d, e, e + 0.75, f, f + 1.0F, f + 1.0F, f, o, p, q, Direction.WEST);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d + 1.0F, e, e, f, f, f + 1.0F, f + 1.0F, o, p, q, Direction.DOWN);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d + 1.0F, e + 0.75, e + 0.75, f + 1.0F, f + 1.0F, f, f, o, p, q, Direction.UP);
    //$$         } else if (Configs.enderPortalRenderMode == EnderPortalRenderMode.FULL) {
    //$$             // Rendering the end portal as a full block.
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d + 1.0F, e, e + 1.0F, f + 1.0F, f + 1.0F, f + 1.0F, f + 1.0F, o, p, q, Direction.SOUTH);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d + 1.0F, e + 1.0F, e, f, f, f, f, o, p, q, Direction.NORTH);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 1.0F, d + 1.0F, e + 1.0F, e, f, f + 1.0F, f + 1.0F, f, o, p, q, Direction.EAST);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d, e, e + 1.0F, f, f + 1.0F, f + 1.0F, f, o, p, q, Direction.WEST);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d + 1.0F, e, e, f, f, f + 1.0F, f + 1.0F, o, p, q, Direction.DOWN);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d + 1.0F, e + 1.0F, e + 1.0F, f + 1.0F, f + 1.0F, f, f, o, p, q, Direction.UP);
    //$$         } else if (Configs.enderPortalRenderMode == EnderPortalRenderMode.MODERN) {
    //$$             // Rendering the end portal with Minecraft 21w13a and above.
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d + 1.0F, e, e + 1.0F, f + 1.0F, f + 1.0F, f + 1.0F, f + 1.0F, o, p, q, Direction.SOUTH);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d + 1.0F, e + 1.0F, e, f, f, f, f, o, p, q, Direction.NORTH);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 1.0F, d + 1.0F, e + 1.0F, e, f, f + 1.0F, f + 1.0F, f, o, p, q, Direction.EAST);
    //$$              this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d, e, e + 1.0F, f, f + 1.0F, f + 1.0F, f, o, p, q, Direction.WEST);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d + 1.0F, e + 0.375F, e + 0.375F, f, f, f + 1.0F, f + 1.0F, o, p, q, Direction.DOWN);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d + 1.0F, e + 1.0F, e + 1.0F, f + 1.0F, f + 1.0F, f, f, o, p, q, Direction.UP);
    //$$         } else {
    //$$             // Rendering the end portal with Minecraft 21w13a below.
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d + 1.0F, e, e + 1.0F, f + 1.0F, f + 1.0F, f + 1.0F, f + 1.0F, o, p, q, Direction.SOUTH);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d + 1.0F, e + 1.0F, e, f, f, f, f, o, p, q, Direction.NORTH);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d + 1.0F, d + 1.0F, e + 1.0F, e, f, f + 1.0F, f + 1.0F, f, o, p, q, Direction.EAST);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d, e, e + 1.0F, f, f + 1.0F, f + 1.0F, f, o, p, q, Direction.WEST);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d + 1.0F, e, e, f, f, f + 1.0F, f + 1.0F, o, p, q, Direction.DOWN);
    //$$             this.renderFace(theEndPortalBlockEntity, bufferBuilder, d, d + 1.0F, e + 0.375F, e + 0.375F, f + 1.0F, f + 1.0F, f, f, o, p, q, Direction.UP);
    //$$         }
    //$$         tesselator.end();
    //$$         GlStateManager.popMatrix();
    //$$         GlStateManager.matrixMode(5888);
    //$$         this.bindTexture(END_SKY_LOCATION);
    //$$     }
    //$$
    //$$     GlStateManager.disableBlend();
    //$$     GlStateManager.disableTexGen(GlStateManager.TexGen.S);
    //$$     GlStateManager.disableTexGen(GlStateManager.TexGen.T);
    //$$     GlStateManager.disableTexGen(GlStateManager.TexGen.R);
    //$$     GlStateManager.enableLighting();
    //$$     if (bl) {
    //$$         gameRenderer.resetFogColor(false);
    //$$     }
    //$$ }
    //#endif

}
