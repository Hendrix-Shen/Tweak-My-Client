package top.hendrixshen.tweakmyclient.mixin.patch.endPortalRendererFix;

import org.lwjgl.opengl.GL11;
import top.hendrixshen.tweakmyclient.game.Configs;
import top.hendrixshen.tweakmyclient.impl.patch.endPortalRendererFix.EndPortalRenderMode;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import java.nio.FloatBuffer;
import java.util.Random;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 : subproject 1.14.4        &lt;--------</li>
 * <li>mc1.15+: subproject 1.16.5 (main project)</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(TheEndPortalRenderer.class)
public abstract class MixinTheEndPortalRenderer extends BlockEntityRenderer<TheEndPortalBlockEntity> {
    @Shadow
    @Final
    private static ResourceLocation END_SKY_LOCATION;

    @Shadow
    @Final
    private static Random RANDOM;

    @Shadow
    @Final
    private static ResourceLocation END_PORTAL_LOCATION;

    @Shadow
    protected abstract FloatBuffer getBuffer(float par1, float par2, float par3, float par4);

    @Shadow
    @Final
    private static FloatBuffer MODELVIEW;

    @Shadow
    @Final
    private static FloatBuffer PROJECTION;

    @Shadow
    protected abstract int getPasses(double distance);

    @Shadow
    protected abstract float getOffset();

    @Unique
    private void tmc$renderFace(
            TheEndPortalBlockEntity theEndPortalBlockEntity,
            BufferBuilder bufferBuilder,
            double x0, double x1, double y0, double y1,
            double z0, double z1, double z2, double z3,
            float colorR, float colorG, float colorB,
            Direction direction
    ) {
        if (theEndPortalBlockEntity.shouldRenderFace(direction)) {
            bufferBuilder.vertex(x0, y0, z0).color(colorR, colorG, colorB, 1.0F).endVertex();
            bufferBuilder.vertex(x1, y0, z1).color(colorR, colorG, colorB, 1.0F).endVertex();
            bufferBuilder.vertex(x1, y1, z2).color(colorR, colorG, colorB, 1.0F).endVertex();
            bufferBuilder.vertex(x0, y1, z3).color(colorR, colorG, colorB, 1.0F).endVertex();
        }
    }

    @WrapMethod(method = "render(Lnet/minecraft/world/level/block/entity/TheEndPortalBlockEntity;DDDFI)V")
    private void patchRender(TheEndPortalBlockEntity entity, double camX, double camY, double camZ, float partialTicks, int packedLight, Operation<Void> original) {
        if (!Configs.endPortalRendererFix.getBooleanValue()) {
            original.call(entity, camX, camY, camZ, partialTicks, packedLight);
        }

        GlStateManager.disableLighting();
        MixinTheEndPortalRenderer.RANDOM.setSeed(31100L);
        GlStateManager.getMatrix(GL11.GL_MODELVIEW_MATRIX, MixinTheEndPortalRenderer.MODELVIEW);
        GlStateManager.getMatrix(GL11.GL_PROJECTION_MATRIX, MixinTheEndPortalRenderer.PROJECTION);
        double distSqr = camX * camX + camY * camY + camZ * camZ;
        int passes = this.getPasses(distSqr);
        float offset = this.getOffset();
        boolean useFog = false;
        GameRenderer gameRenderer = Minecraft.getInstance().gameRenderer;

        for (int pass = 0; pass < passes; pass++) {
            GlStateManager.pushMatrix();
            float factor = 2.0F / (float) (18 - pass);

            if (pass == 0) {
                this.bindTexture(MixinTheEndPortalRenderer.END_SKY_LOCATION);
                factor = 0.15F;
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            }

            if (pass >= 1) {
                this.bindTexture(MixinTheEndPortalRenderer.END_PORTAL_LOCATION);
                useFog = true;
                gameRenderer.resetFogColor(true);
            }

            if (pass == 1) {
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            }

            GlStateManager.texGenMode(GlStateManager.TexGen.S, GL11.GL_EYE_LINEAR);
            GlStateManager.texGenMode(GlStateManager.TexGen.T, GL11.GL_EYE_LINEAR);
            GlStateManager.texGenMode(GlStateManager.TexGen.R, GL11.GL_EYE_LINEAR);
            GlStateManager.texGenParam(GlStateManager.TexGen.S, GL11.GL_EYE_PLANE, this.getBuffer(1.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.texGenParam(GlStateManager.TexGen.T, GL11.GL_EYE_PLANE, this.getBuffer(0.0F, 1.0F, 0.0F, 0.0F));
            GlStateManager.texGenParam(GlStateManager.TexGen.R, GL11.GL_EYE_PLANE, this.getBuffer(0.0F, 0.0F, 1.0F, 0.0F));
            GlStateManager.enableTexGen(GlStateManager.TexGen.S);
            GlStateManager.enableTexGen(GlStateManager.TexGen.T);
            GlStateManager.enableTexGen(GlStateManager.TexGen.R);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(GL11.GL_TEXTURE);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.translatef(0.5F, 0.5F, 0.0F);
            GlStateManager.scalef(0.5F, 0.5F, 1.0F);
            float n = (float) (pass + 1);
            GlStateManager.translatef(17.0F / n, (2.0F + n / 1.5F) * ((float) (Util.getMillis() % 800000L) / 800000.0F), 0.0F);
            GlStateManager.rotatef((n * n * 4321.0F + n * 9.0F) * 2.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.scalef(4.5F - n / 4.0F, 4.5F - n / 4.0F, 1.0F);
            GlStateManager.multMatrix(MixinTheEndPortalRenderer.PROJECTION);
            GlStateManager.multMatrix(MixinTheEndPortalRenderer.MODELVIEW);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferBuilder = tesselator.getBuilder();
            bufferBuilder.begin(7, DefaultVertexFormat.POSITION_COLOR);
            float colorR = (MixinTheEndPortalRenderer.RANDOM.nextFloat() * 0.5F + 0.1F) * factor;
            float colorG = (MixinTheEndPortalRenderer.RANDOM.nextFloat() * 0.5F + 0.4F) * factor;
            float colorB = (MixinTheEndPortalRenderer.RANDOM.nextFloat() * 0.5F + 0.5F) * factor;

            if (Configs.endPortalRenderMode.getOptionListValue() == EndPortalRenderMode.ACTUAL) {
                this.tmc$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY, camY + 0.75F, camZ + 1.0F, camZ + 1.0F, camZ + 1.0F, camZ + 1.0F, colorR, colorG, colorB, Direction.SOUTH);
                this.tmc$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY + 0.75F, camY, camZ, camZ, camZ, camZ, colorR, colorG, colorB, Direction.NORTH);
                this.tmc$renderFace(entity, bufferBuilder, camX + 1.0F, camX + 1.0F, camY + 0.75, camY, camZ, camZ + 1.0F, camZ + 1.0F, camZ, colorR, colorG, colorB, Direction.EAST);
                this.tmc$renderFace(entity, bufferBuilder, camX, camX, camY, camY + 0.75F, camZ, camZ + 1.0F, camZ + 1.0F, camZ, colorR, colorG, colorB, Direction.WEST);
                this.tmc$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY, camY, camZ, camZ, camZ + 1.0F, camZ + 1.0F, colorR, colorG, colorB, Direction.DOWN);
                this.tmc$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY + 0.75F, camY + 0.75, camZ + 1.0F, camZ + 1.0F, camZ, camZ, colorR, colorG, colorB, Direction.UP);
            } else if (Configs.endPortalRenderMode.getOptionListValue() == EndPortalRenderMode.FULL) {
                this.tmc$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY, camY + 1.0F, camZ + 1.0F, camZ + 1.0F, camZ + 1.0F, camZ + 1.0F, colorR, colorG, colorB, Direction.SOUTH);
                this.tmc$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY + 1.0F, camY, camZ, camZ, camZ, camZ, colorR, colorG, colorB, Direction.NORTH);
                this.tmc$renderFace(entity, bufferBuilder, camX + 1.0F, camX + 1.0F, camY + 1.0F, camY, camZ, camZ + 1.0F, camZ + 1.0F, camZ, colorR, colorG, colorB, Direction.EAST);
                this.tmc$renderFace(entity, bufferBuilder, camX, camX, camY, camY + 1.0F, camZ, camZ + 1.0F, camZ + 1.0F, camZ, colorR, colorG, colorB, Direction.WEST);
                this.tmc$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY, camY, camZ, camZ, camZ + 1.0F, camZ + 1.0F, colorR, colorG, colorB, Direction.DOWN);
                this.tmc$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY + 1.0F, camY + 1.0F, camZ + 1.0F, camZ + 1.0F, camZ, camZ, colorR, colorG, colorB, Direction.UP);
            } else if (Configs.endPortalRenderMode.getOptionListValue() == EndPortalRenderMode.LEGACY) {
                this.tmc$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY, camY + 1.0F, camZ + 1.0F, camZ + 1.0F, camZ + 1.0F, camZ + 1.0F, colorR, colorG, colorB, Direction.SOUTH);
                this.tmc$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY + 1.0F, camY, camZ, camZ, camZ, camZ, colorR, colorG, colorB, Direction.NORTH);
                this.tmc$renderFace(entity, bufferBuilder, camX + 1.0F, camX + 1.0F, camY + 1.0F, camY, camZ, camZ + 1.0F, camZ + 1.0F, camZ, colorR, colorG, colorB, Direction.EAST);
                this.tmc$renderFace(entity, bufferBuilder, camX, camX, camY, camY + 1.0F, camZ, camZ + 1.0F, camZ + 1.0F, camZ, colorR, colorG, colorB, Direction.WEST);
                this.tmc$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY, camY, camZ, camZ, camZ + 1.0F, camZ + 1.0F, colorR, colorG, colorB, Direction.DOWN);
                this.tmc$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY + offset, camY + offset, camZ + 1.0F, camZ + 1.0F, camZ, camZ, colorR, colorG, colorB, Direction.UP);
            } else if (Configs.endPortalRenderMode.getOptionListValue() == EndPortalRenderMode.MODERN) {
                this.tmc$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY, camY + 1.0F, camZ + 1.0F, camZ + 1.0F, camZ + 1.0F, camZ + 1.0F, colorR, colorG, colorB, Direction.SOUTH);
                this.tmc$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY + 1.0F, camY, camZ, camZ, camZ, camZ, colorR, colorG, colorB, Direction.NORTH);
                this.tmc$renderFace(entity, bufferBuilder, camX + 1.0F, camX + 1.0F, camY + 1.0F, camY, camZ, camZ + 1.0F, camZ + 1.0F, camZ, colorR, colorG, colorB, Direction.EAST);
                this.tmc$renderFace(entity, bufferBuilder, camX, camX, camY, camY + 1.0F, camZ, camZ + 1.0F, camZ + 1.0F, camZ, colorR, colorG, colorB, Direction.WEST);
                this.tmc$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY, camY, camZ, camZ, camZ + 1.0F, camZ + 1.0F, colorR, colorG, colorB, Direction.DOWN);
                this.tmc$renderFace(entity, bufferBuilder, camX, camX + 1.0F, camY + offset, camY + offset, camZ + 1.0F, camZ + 1.0F, camZ, camZ, colorR, colorG, colorB, Direction.UP);
            }

            tesselator.end();
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
            this.bindTexture(MixinTheEndPortalRenderer.END_SKY_LOCATION);
        }

        GlStateManager.disableBlend();
        GlStateManager.disableTexGen(GlStateManager.TexGen.S);
        GlStateManager.disableTexGen(GlStateManager.TexGen.T);
        GlStateManager.disableTexGen(GlStateManager.TexGen.R);
        GlStateManager.enableLighting();

        if (useFog) {
            gameRenderer.resetFogColor(false);
        }
    }
}
