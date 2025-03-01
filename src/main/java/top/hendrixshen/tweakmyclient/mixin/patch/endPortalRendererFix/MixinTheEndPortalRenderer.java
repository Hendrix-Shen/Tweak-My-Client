package top.hendrixshen.tweakmyclient.mixin.patch.endPortalRendererFix;

import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.hendrixshen.tweakmyclient.game.Configs;
import top.hendrixshen.tweakmyclient.impl.patch.endPortalRendererFix.EnderPortalRenderMode;

//#if MC > 11404
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//#else
//$$ import com.mojang.blaze3d.platform.GlStateManager;
//$$ import com.mojang.blaze3d.vertex.BufferBuilder;
//$$ import com.mojang.blaze3d.vertex.DefaultVertexFormat;
//$$ import com.mojang.blaze3d.vertex.Tesselator;
//$$ import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
//$$ import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
//$$ import net.minecraft.Util;
//$$ import net.minecraft.client.Minecraft;
//$$ import net.minecraft.client.renderer.GameRenderer;
//$$ import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
//$$ import net.minecraft.resources.ResourceLocation;
//$$ import org.lwjgl.opengl.GL11;
//$$ import org.spongepowered.asm.mixin.Final;
//$$ import java.nio.FloatBuffer;
//$$ import java.util.Random;
//#endif

@Mixin(TheEndPortalRenderer.class)
public abstract class MixinTheEndPortalRenderer
    //#if MC < 11500
    //$$ extends BlockEntityRenderer<TheEndPortalBlockEntity>
    //#endif
{
    //#if MC > 11404
    @Shadow
    protected abstract void renderFace(
            TheEndPortalBlockEntity theEndPortalBlockEntity,
            Matrix4f matrix4f,
            VertexConsumer vertexConsumer,
            float x0,
            float x1,
            float y0,
            float y1,
            float z0,
            float z1,
            float z2,
            float z3,
            //#if MC < 11700
            float colorR,
            float colorG,
            float colorB,
            //#endif
            Direction direction
    );
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
    //$$ private void magiclib$renderFace(TheEndPortalBlockEntity theEndPortalBlockEntity,
    //$$                         BufferBuilder bufferBuilder,
    //$$                         double x0,
    //$$                         double x1,
    //$$                         double y0,
    //$$                         double y1,
    //$$                         double z0,
    //$$                         double z1,
    //$$                         double z2,
    //$$                         double z3,
    //$$                         float colorR,
    //$$                         float colorG,
    //$$                         float colorB,
    //$$                         Direction direction
    //$$ ) {
    //$$     if (theEndPortalBlockEntity.shouldRenderFace(direction)) {
    //$$         bufferBuilder.vertex(x0, y0, z0).color(colorR, colorG, colorB, 1.0F).endVertex();
    //$$         bufferBuilder.vertex(x1, y0, z1).color(colorR, colorG, colorB, 1.0F).endVertex();
    //$$         bufferBuilder.vertex(x1, y1, z2).color(colorR, colorG, colorB, 1.0F).endVertex();
    //$$         bufferBuilder.vertex(x0, y1, z3).color(colorR, colorG, colorB, 1.0F).endVertex();
    //$$     }
    //$$ }
    //#endif

    //#if MC > 11404
    @Inject(
            method = "renderCube",
            at = @At(
                    value = "INVOKE",
                    //#if MC > 11902
                    //$$ target = "Lnet/minecraft/client/renderer/blockentity/TheEndPortalRenderer;renderFace(Lnet/minecraft/world/level/block/entity/TheEndPortalBlockEntity;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFLnet/minecraft/core/Direction;)V",
                    //#elseif MC > 11605
                    //$$ target = "Lnet/minecraft/client/renderer/blockentity/TheEndPortalRenderer;renderFace(Lnet/minecraft/world/level/block/entity/TheEndPortalBlockEntity;Lcom/mojang/math/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFLnet/minecraft/core/Direction;)V",
                    //#else
                    target = "Lnet/minecraft/client/renderer/blockentity/TheEndPortalRenderer;renderFace(Lnet/minecraft/world/level/block/entity/TheEndPortalBlockEntity;Lcom/mojang/math/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFFFFLnet/minecraft/core/Direction;)V",
                    //#endif
                    ordinal = 0
            ),
            cancellable = true
    )
    private void onRenderCube(
            TheEndPortalBlockEntity blockEntity,
            //#if MC < 11700
            float offset,
            float factor,
            //#endif
            Matrix4f matrix4f,
            VertexConsumer consumer,
            CallbackInfo ci,
            //#if MC > 11605
            //$$ @Local(ordinal = 0) float offsetDown,
            //$$ @Local(ordinal = 1) float offsetUp
            //#else
            @Local(ordinal = 2) float colorR,
            @Local(ordinal = 3) float colorG,
            @Local(ordinal = 4) float colorB
            //#endif
    ) {
        if (!Configs.endPortalRendererFix.getBooleanValue()) {
            return;
        }

        if (Configs.enderPortalRenderMode.getOptionListValue() == EnderPortalRenderMode.ACTUAL) {
            // Rendering the ender portal using its hit box.
            //#if MC > 11605
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, offsetDown, offsetUp, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, offsetUp, offsetDown, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 1.0F, 1.0F, offsetUp, offsetDown, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 0.0F, offsetDown, offsetUp, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, offsetDown, offsetDown, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, offsetUp, offsetUp, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
            //#else
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, offset, 1.0F, 1.0F, 1.0F, 1.0F, colorR, colorG, colorB, Direction.SOUTH);
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, offset, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, colorR, colorG, colorB, Direction.NORTH);
            this.renderFace(blockEntity, matrix4f, consumer, 1.0F, 1.0F, offset, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, colorR, colorG, colorB, Direction.EAST);
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 0.0F, 0.0F, offset, 0.0F, 1.0F, 1.0F, 0.0F, colorR, colorG, colorB, Direction.WEST);
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, colorR, colorG, colorB, Direction.DOWN);
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, offset, offset, 1.0F, 1.0F, 0.0F, 0.0F, colorR, colorG, colorB, Direction.UP);
            //#endif
        } else if (Configs.enderPortalRenderMode.getOptionListValue() == EnderPortalRenderMode.FULL) {
            // Rendering the end portal as a full block.
            //#if MC > 11605
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
            //#else
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, colorR, colorG, colorB, Direction.SOUTH);
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, colorR, colorG, colorB, Direction.NORTH);
            this.renderFace(blockEntity, matrix4f, consumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, colorR, colorG, colorB, Direction.EAST);
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, colorR, colorG, colorB, Direction.WEST);
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, colorR, colorG, colorB, Direction.DOWN);
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, colorR, colorG, colorB, Direction.UP);
            //#endif
        } else if (Configs.enderPortalRenderMode.getOptionListValue() == EnderPortalRenderMode.LEGACY) {
            // Rendering the end portal with Minecraft 21w13a below.
            //#if MC > 11605
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, offsetUp, offsetUp, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
            //#else
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, colorR, colorG, colorB, Direction.SOUTH);
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, colorR, colorG, colorB, Direction.NORTH);
            this.renderFace(blockEntity, matrix4f, consumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, colorR, colorG, colorB, Direction.EAST);
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, colorR, colorG, colorB, Direction.WEST);
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, colorR, colorG, colorB, Direction.DOWN);
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, offset, offset, 1.0F, 1.0F, 0.0F, 0.0F, colorR, colorG, colorB, Direction.UP);
            //#endif
        } else if (Configs.enderPortalRenderMode.getOptionListValue() == EnderPortalRenderMode.MODERN) {
            // Rendering the end portal with Minecraft 21w13a and above.
            //#if MC > 11605
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, offsetDown, offsetDown, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
            //$$ this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, offsetUp, offsetUp, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
            //#else
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, colorR, colorG, colorB, Direction.SOUTH);
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, colorR, colorG, colorB, Direction.NORTH);
            this.renderFace(blockEntity, matrix4f, consumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, colorR, colorG, colorB, Direction.EAST);
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, colorR, colorG, colorB, Direction.WEST);
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.375F, 0.375F, 0.0F, 0.0F, 1.0F, 1.0F, colorR, colorG, colorB, Direction.DOWN);
            this.renderFace(blockEntity, matrix4f, consumer, 0.0F, 1.0F, offset, offset, 1.0F, 1.0F, 0.0F, 0.0F, colorR, colorG, colorB, Direction.UP);
            //#endif
        }

        ci.cancel();
    }
    //#else
    //$$ @WrapMethod(method = "render(Lnet/minecraft/world/level/block/entity/TheEndPortalBlockEntity;DDDFI)V")
    //$$ private void patchRender(TheEndPortalBlockEntity entity, double camX, double camY, double camZ, float partialTicks, int packedLight, Operation<Void> original) {
    //$$     if (!Configs.endPortalRendererFix.getBooleanValue()) {
    //$$         original.call(entity, camX, camY, camZ, partialTicks, packedLight);
    //$$     }
    //$$
    //$$     GlStateManager.disableLighting();
    //$$     MixinTheEndPortalRenderer.RANDOM.setSeed(31100L);
    //$$     GlStateManager.getMatrix(GL11.GL_MODELVIEW_MATRIX, MixinTheEndPortalRenderer.MODELVIEW);
    //$$     GlStateManager.getMatrix(GL11.GL_PROJECTION_MATRIX, MixinTheEndPortalRenderer.PROJECTION);
    //$$     double distSqr = camX * camX + camY * camY + camZ * camZ;
    //$$     int passes = this.getPasses(distSqr);
    //$$     float offset = this.getOffset();
    //$$     boolean bl = false;
    //$$     GameRenderer gameRenderer = Minecraft.getInstance().gameRenderer;
    //$$
    //$$     for (int l = 0; l < passes; l++) {
    //$$         GlStateManager.pushMatrix();
    //$$         float factor = 2.0F / (float)(18 - l);
    //$$
    //$$         if (l == 0) {
    //$$             this.bindTexture(MixinTheEndPortalRenderer.END_SKY_LOCATION);
    //$$             factor = 0.15F;
    //$$             GlStateManager.enableBlend();
    //$$             GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    //$$         }
    //$$
    //$$         if (l >= 1) {
    //$$             this.bindTexture(MixinTheEndPortalRenderer.END_PORTAL_LOCATION);
    //$$             bl = true;
    //$$             gameRenderer.resetFogColor(true);
    //$$         }
    //$$
    //$$         if (l == 1) {
    //$$             GlStateManager.enableBlend();
    //$$             GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
    //$$         }
    //$$
    //$$         GlStateManager.texGenMode(GlStateManager.TexGen.S, GL11.GL_EYE_LINEAR);
    //$$         GlStateManager.texGenMode(GlStateManager.TexGen.T, GL11.GL_EYE_LINEAR);
    //$$         GlStateManager.texGenMode(GlStateManager.TexGen.R, GL11.GL_EYE_LINEAR);
    //$$         GlStateManager.texGenParam(GlStateManager.TexGen.S, GL11.GL_EYE_PLANE, this.getBuffer(1.0F, 0.0F, 0.0F, 0.0F));
    //$$         GlStateManager.texGenParam(GlStateManager.TexGen.T, GL11.GL_EYE_PLANE, this.getBuffer(0.0F, 1.0F, 0.0F, 0.0F));
    //$$         GlStateManager.texGenParam(GlStateManager.TexGen.R, GL11.GL_EYE_PLANE, this.getBuffer(0.0F, 0.0F, 1.0F, 0.0F));
    //$$         GlStateManager.enableTexGen(GlStateManager.TexGen.S);
    //$$         GlStateManager.enableTexGen(GlStateManager.TexGen.T);
    //$$         GlStateManager.enableTexGen(GlStateManager.TexGen.R);
    //$$         GlStateManager.popMatrix();
    //$$         GlStateManager.matrixMode(GL11.GL_TEXTURE);
    //$$         GlStateManager.pushMatrix();
    //$$         GlStateManager.loadIdentity();
    //$$         GlStateManager.translatef(0.5F, 0.5F, 0.0F);
    //$$         GlStateManager.scalef(0.5F, 0.5F, 1.0F);
    //$$         float n = (float)(l + 1);
    //$$         GlStateManager.translatef(17.0F / n, (2.0F + n / 1.5F) * ((float)(Util.getMillis() % 800000L) / 800000.0F), 0.0F);
    //$$         GlStateManager.rotatef((n * n * 4321.0F + n * 9.0F) * 2.0F, 0.0F, 0.0F, 1.0F);
    //$$         GlStateManager.scalef(4.5F - n / 4.0F, 4.5F - n / 4.0F, 1.0F);
    //$$         GlStateManager.multMatrix(MixinTheEndPortalRenderer.PROJECTION);
    //$$         GlStateManager.multMatrix(MixinTheEndPortalRenderer.MODELVIEW);
    //$$         Tesselator tesselator = Tesselator.getInstance();
    //$$         BufferBuilder bufferBuilder = tesselator.getBuilder();
    //$$         bufferBuilder.begin(7, DefaultVertexFormat.POSITION_COLOR);
    //$$         float colorR = (MixinTheEndPortalRenderer.RANDOM.nextFloat() * 0.5F + 0.1F) * factor;
    //$$         float colorG = (MixinTheEndPortalRenderer.RANDOM.nextFloat() * 0.5F + 0.4F) * factor;
    //$$         float colorB = (MixinTheEndPortalRenderer.RANDOM.nextFloat() * 0.5F + 0.5F) * factor;
    //$$
    //$$         if (Configs.enderPortalRenderMode.getOptionListValue() == EnderPortalRenderMode.ACTUAL) {
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY, camY + 0.75F, camZ + 1.0F, camZ + 1.0F, camZ + 1.0F, camZ + 1.0F, colorR, colorG, colorB, Direction.SOUTH);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY + 0.75F, camY, camZ, camZ, camZ, camZ, colorR, colorG, colorB, Direction.NORTH);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX + 1.0F, camX + 1.0F, camY + 0.75, camY, camZ, camZ + 1.0F, camZ + 1.0F, camZ, colorR, colorG, colorB, Direction.EAST);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX, camY, camY + 0.75F, camZ, camZ + 1.0F, camZ + 1.0F, camZ, colorR, colorG, colorB, Direction.WEST);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY, camY, camZ, camZ, camZ + 1.0F, camZ + 1.0F, colorR, colorG, colorB, Direction.DOWN);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY + 0.75F, camY + 0.75, camZ + 1.0F, camZ + 1.0F, camZ, camZ, colorR, colorG, colorB, Direction.UP);
    //$$         } else if (Configs.enderPortalRenderMode.getOptionListValue() == EnderPortalRenderMode.FULL) {
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY, camY + 1.0F, camZ + 1.0F, camZ + 1.0F, camZ + 1.0F, camZ + 1.0F, colorR, colorG, colorB, Direction.SOUTH);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY + 1.0F, camY, camZ, camZ, camZ, camZ, colorR, colorG, colorB, Direction.NORTH);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX + 1.0F, camX + 1.0F, camY + 1.0F, camY, camZ, camZ + 1.0F, camZ + 1.0F, camZ, colorR, colorG, colorB, Direction.EAST);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX, camY, camY + 1.0F, camZ, camZ + 1.0F, camZ + 1.0F, camZ, colorR, colorG, colorB, Direction.WEST);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY, camY, camZ, camZ, camZ + 1.0F, camZ + 1.0F, colorR, colorG, colorB, Direction.DOWN);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY + 1.0F, camY + 1.0F, camZ + 1.0F, camZ + 1.0F, camZ, camZ, colorR, colorG, colorB, Direction.UP);
    //$$         } else if (Configs.enderPortalRenderMode.getOptionListValue() == EnderPortalRenderMode.LEGACY) {
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY, camY + 1.0F, camZ + 1.0F, camZ + 1.0F, camZ + 1.0F, camZ + 1.0F, colorR, colorG, colorB, Direction.SOUTH);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY + 1.0F, camY, camZ, camZ, camZ, camZ, colorR, colorG, colorB, Direction.NORTH);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX + 1.0F, camX + 1.0F, camY + 1.0F, camY, camZ, camZ + 1.0F, camZ + 1.0F, camZ, colorR, colorG, colorB, Direction.EAST);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX, camY, camY + 1.0F, camZ, camZ + 1.0F, camZ + 1.0F, camZ, colorR, colorG, colorB, Direction.WEST);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY, camY, camZ, camZ, camZ + 1.0F, camZ + 1.0F, colorR, colorG, colorB, Direction.DOWN);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY + offset, camY + offset, camZ + 1.0F, camZ + 1.0F, camZ, camZ, colorR, colorG, colorB, Direction.UP);
    //$$         } else if (Configs.enderPortalRenderMode.getOptionListValue() == EnderPortalRenderMode.MODERN) {
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY, camY + 1.0F, camZ + 1.0F, camZ + 1.0F, camZ + 1.0F, camZ + 1.0F, colorR, colorG, colorB, Direction.SOUTH);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY + 1.0F, camY, camZ, camZ, camZ, camZ, colorR, colorG, colorB, Direction.NORTH);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX + 1.0F, camX + 1.0F, camY + 1.0F, camY, camZ, camZ + 1.0F, camZ + 1.0F, camZ, colorR, colorG, colorB, Direction.EAST);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX, camY, camY + 1.0F, camZ, camZ + 1.0F, camZ + 1.0F, camZ, colorR, colorG, colorB, Direction.WEST);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY, camY, camZ, camZ, camZ + 1.0F, camZ + 1.0F, colorR, colorG, colorB, Direction.DOWN);
    //$$             this.magiclib$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY + offset, camY + offset, camZ + 1.0F, camZ + 1.0F, camZ, camZ, colorR, colorG, colorB, Direction.UP);
    //$$         }
    //$$
    //$$         tesselator.end();
    //$$         GlStateManager.popMatrix();
    //$$         GlStateManager.matrixMode(GL11.GL_MODELVIEW);
    //$$         this.bindTexture(MixinTheEndPortalRenderer.END_SKY_LOCATION);
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
