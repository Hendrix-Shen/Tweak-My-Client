package top.hendrixshen.tweakmyclient.util;

import fi.dy.masa.malilib.render.RenderUtils;
import org.jetbrains.annotations.NotNull;
import top.hendrixshen.magiclib.impl.render.context.RenderGlobal;

// CHECKSTYLE.OFF: ImportOrder
//#if MC >= 12105
//$$ import fi.dy.masa.malilib.util.data.Color4f;
//$$ import fi.dy.masa.malilib.render.MaLiLibPipelines;
//$$ import fi.dy.masa.malilib.render.RenderContext;
//#else
import fi.dy.masa.malilib.util.Color4f;
import top.hendrixshen.magiclib.api.compat.mojang.blaze3d.vertex.VertexFormatCompat;
//#endif
// CHECKSTYLE.ON: ImportOrder

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

// CHECKSTYLE.OFF: ImportOrder
//#if 12106 > MC && MC > 12104
//$$ import com.mojang.blaze3d.buffers.BufferUsage;
//#endif

//#if MC >= 12105
//$$ import com.mojang.blaze3d.opengl.GlStateManager;
//#else
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
//#endif

//#if 12105 > MC && MC > 12006
//$$ import com.mojang.blaze3d.vertex.BufferUploader;
//#endif

//#if 12105 > MC && MC > 11404
import com.mojang.blaze3d.systems.RenderSystem;
//#endif

//#if 12103 > MC && MC > 11605
//$$ import net.minecraft.client.renderer.GameRenderer;
//#endif

//#if MC > 12006
//$$ import com.mojang.blaze3d.vertex.MeshData;
//#endif

//#if MC < 11500
//$$ import com.mojang.blaze3d.platform.GlStateManager;
//#endif
// CHECKSTYLE.ON: ImportOrder

public class RenderUtil {
    public static void renderAreaOutline(double minX, double minY, double minZ,
                                         double maxX, double maxY, double maxZ,
                                         Color4f color, boolean depthTest) {
        Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        final double dx = cameraPos.x;
        final double dy = cameraPos.y;
        final double dz = cameraPos.z;

        RenderUtil.drawBoundingBoxEdges(
                minX - dx, minY - dy, minZ - dz,
                maxX - dx + 1, maxY - dy + 1, maxZ - dz + 1,
                color, depthTest
        );
    }

    public static void renderAreaOutline(@NotNull AreaBox areaBox, Color4f color, boolean depthTest) {
        RenderUtil.renderAreaOutline(
                areaBox.getMinX(), areaBox.getMinY(), areaBox.getMinZ(),
                areaBox.getMaxX(), areaBox.getMaxY(), areaBox.getMaxZ(),
                color, depthTest
        );
    }

    public static void renderAreaOverlay(double minX, double minY, double minZ,
                                         double maxX, double maxY, double maxZ,
                                         Color4f color, boolean depthTest) {
        Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        final double dx = cameraPos.x;
        final double dy = cameraPos.y;
        final double dz = cameraPos.z;

        RenderUtil.drawBoundingBoxOverlay(
                minX - dx, minY - dy, minZ - dz,
                maxX - dx + 1, maxY - dy + 1, maxZ - dz + 1,
                color, depthTest
        );
    }

    public static void renderAreaOverlay(@NotNull AreaBox areaBox, Color4f color4f, boolean depthTest) {
        RenderUtil.renderAreaOverlay(
                areaBox.getMinX(), areaBox.getMinY(), areaBox.getMinZ(),
                areaBox.getMaxX(), areaBox.getMaxY(), areaBox.getMaxZ(),
                color4f, depthTest
        );
    }

    private static void drawBoundingBoxEdges(double minX, double minY, double minZ,
                                             double maxX, double maxY, double maxZ,
                                             @NotNull Color4f color, boolean depthTest) {
        //#if MC < 12105
        if (!depthTest) {
            RenderGlobal.disableDepthTest();
        }
        //#endif

        RenderGlobal.enableBlend();
        // TODO: Migrate to RenderGlobal.
        //#if MC >= 12105
        //$$ GlStateManager._disableCull();
        //#elseif MC > 11404
        RenderSystem.disableCull();
        //#else
        //$$ GlStateManager.disableCull();
        //#endif
        //#if MC < 11700
        RenderGlobal.disableTexture();
        //#endif
        //#if 12102 > MC && MC > 11605
        //$$ RenderGlobal.setShader(GameRenderer::getPositionColorShader);
        //#endif

        //#if MC > 12104
        //$$ RenderContext ctx = new RenderContext(
        //$$         // CHECKSTYLE.OFF: NoWhitespaceBefore
        //$$         // CHECKSTYLE.OFF: SeparatorWrap
        //$$         //#if MC >= 12107
        //$$         //$$ () -> "tweak_my_client:bounding_box_outline",
        //$$         //#endif
        //$$         depthTest ? MaLiLibPipelines.DEBUG_LINES_MASA_SIMPLE_NO_CULL : MaLiLibPipelines.DEBUG_LINES_MASA_SIMPLE_NO_DEPTH_NO_CULL
        //$$         //#if MC < 12106
        //$$         , BufferUsage.STATIC_WRITE
        //$$         //#endif
        //$$         // CHECKSTYLE.ON: SeparatorWrap
        //$$         // CHECKSTYLE.ON: NoWhitespaceBefore
        //$$ );
        //$$ BufferBuilder builder = ctx.getBuilder();
        //#else
        Tesselator tesselator = Tesselator.getInstance();
        //#if MC > 12006
        //$$ BufferBuilder buffer = tesselator.begin(VertexFormatCompat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        //#else
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormatCompat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        //#endif
        //#endif
        //#if 12102 > MC && MC > 11605
        //$$ RenderSystem.setShader(GameRenderer::getPositionColorShader);
        //#endif
        RenderUtils.drawBoxAllEdgesBatchedLines(
                //#if MC > 12006
                //$$ (float) minX, (float) minY, (float) minZ,
                //$$ (float) maxX, (float) maxY, (float) maxZ,
                //#else
                minX, minY, minZ,
                maxX, maxY, maxZ,
                //#endif
                color,
                //#if MC >= 12111
                //$$ 2.0F,
                //#endif
                //#if MC >= 12105
                //$$ builder
                //#else
                buffer
                //#endif
        );
        //#if MC > 12104
        //$$
        //$$ try {
        //$$     MeshData meshData = builder.build();
        //$$
        //$$     if (meshData != null) {
        //$$         ctx.draw(meshData, false, true);
        //$$         meshData.close();
        //$$     }
        //$$
        //$$     ctx.close();
        //$$ } catch (Exception ignored) {
        //$$     // NO-OP
        //$$ }
        //#elseif MC > 12006
        //$$ RenderUtil.end(buffer);
        //#else
        tesselator.end();
        //#endif

        //#if MC < 11700
        RenderGlobal.enableTexture();
        //#endif
        // TODO: Migrate to RenderGlobal.
        //#if MC >= 12105
        //$$ GlStateManager._enableCull();
        //#elseif MC > 11404
        RenderSystem.enableCull();
        //#else
        //$$ GlStateManager.enableCull();
        //#endif
        RenderGlobal.disableBlend();

        //#if MC < 12105
        if (!depthTest) {
            RenderGlobal.enableDepthTest();
        }
        //#endif
    }

    private static void drawBoundingBoxOverlay(double minX, double minY, double minZ,
                                               double maxX, double maxY, double maxZ,
                                               @NotNull Color4f color, boolean depthTest) {
        //#if MC < 12105
        if (!depthTest) {
            RenderGlobal.disableDepthTest();
        }
        //#endif

        RenderGlobal.enableBlend();
        // TODO: Migrate to RenderGlobal.
        //#if MC >= 12105
        //$$ GlStateManager._disableCull();
        //#elseif MC > 11404
        RenderSystem.disableCull();
        //#else
        //$$ GlStateManager.disableCull();
        //#endif
        //#if MC < 11700
        RenderGlobal.disableTexture();
        //#endif
        //#if 12102 > MC && MC > 11605
        //$$ RenderGlobal.setShader(GameRenderer::getPositionColorShader);
        //#endif

        //#if MC > 12104
        //$$ RenderContext ctx = new RenderContext(
        //$$         // CHECKSTYLE.OFF: NoWhitespaceBefore
        //$$         // CHECKSTYLE.OFF: SeparatorWrap
        //$$         //#if MC >= 12107
        //$$         //$$ () -> "tweak_my_client:bounding_box_overlay",
        //$$         //#endif
        //$$         depthTest ? MaLiLibPipelines.POSITION_COLOR_MASA : MaLiLibPipelines.POSITION_COLOR_MASA_NO_DEPTH_NO_CULL
        //$$         //#if MC < 12106
        //$$         , BufferUsage.STATIC_WRITE
        //$$         //#endif
        //$$         // CHECKSTYLE.ON: SeparatorWrap
        //$$         // CHECKSTYLE.ON: NoWhitespaceBefore
        //$$ );
        //$$ BufferBuilder builder = ctx.getBuilder();
        //#else
        Tesselator tesselator = Tesselator.getInstance();
        //#if MC > 12006
        //$$ BufferBuilder buffer = tesselator.begin(VertexFormatCompat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        //#else
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormatCompat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        //#endif
        //#endif
        RenderUtils.drawBoxAllSidesBatchedQuads(
                //#if MC > 12006
                //$$ (float) minX, (float) minY, (float) minZ,
                //$$ (float) maxX, (float) maxY, (float) maxZ,
                //#else
                minX, minY, minZ, maxX, maxY, maxZ,
                //#endif
                color,
                //#if MC >= 12105
                //$$ builder
                //#else
                buffer
                //#endif
        );
        //#if MC > 12104
        //$$
        //$$ try {
        //$$     MeshData meshData = builder.build();
        //$$
        //$$     if (meshData != null) {
        //$$         ctx.draw(meshData, false, true);
        //$$         meshData.close();
        //$$     }
        //$$
        //$$     ctx.close();
        //$$ } catch (Exception ignored) {
        //$$     // NO-OP
        //$$ }
        //#elseif MC > 12006
        //$$ RenderUtil.end(buffer);
        //#else
        tesselator.end();
        //#endif

        //#if MC < 11700
        RenderGlobal.enableTexture();
        //#endif
        // TODO: Migrate to RenderGlobal.
        //#if MC >= 12105
        //$$ GlStateManager._enableCull();
        //#elseif MC > 11404
        RenderSystem.enableCull();
        //#else
        //$$ GlStateManager.enableCull();
        //#endif
        RenderGlobal.disableBlend();

        //#if MC < 12105
        if (!depthTest) {
            RenderGlobal.enableDepthTest();
        }
        //#endif
    }

    public static void renderShapeOutline(@NotNull VoxelShape voxelShape, double x, double y, double z, Color4f color, boolean depthTest) {
        //#if MC < 12105
        if (!depthTest) {
            RenderGlobal.disableDepthTest();
        }
        //#endif

        RenderGlobal.enableBlend();
        // TODO: Migrate to RenderGlobal.
        //#if MC >= 12105
        //$$ GlStateManager._disableCull();
        //#elseif MC > 11404
        RenderSystem.disableCull();
        //#else
        //$$ GlStateManager.disableCull();
        //#endif
        //#if MC < 11700
        RenderGlobal.disableTexture();
        //#endif
        //#if 12102 > MC && MC > 11605
        //$$ RenderGlobal.setShader(GameRenderer::getPositionColorShader);
        //#endif

        //#if MC > 12104
        //$$ RenderContext ctx = new RenderContext(
        //$$         // CHECKSTYLE.OFF: NoWhitespaceBefore
        //$$         // CHECKSTYLE.OFF: SeparatorWrap
        //$$         //#if MC >= 12107
        //$$         //$$ () -> "tweak_my_client:shape_outline",
        //$$         //#endif
        //$$         depthTest ? MaLiLibPipelines.DEBUG_LINES_MASA_SIMPLE_NO_CULL : MaLiLibPipelines.DEBUG_LINES_MASA_SIMPLE_NO_DEPTH_NO_CULL
        //$$         //#if MC < 12106
        //$$         , BufferUsage.STATIC_WRITE
        //$$         //#endif
        //$$         // CHECKSTYLE.ON: SeparatorWrap
        //$$         // CHECKSTYLE.ON: NoWhitespaceBefore
        //$$ );
        //$$ BufferBuilder builder = ctx.getBuilder();
        //#else
        Tesselator tesselator = Tesselator.getInstance();
        //#if MC > 12006
        //$$ BufferBuilder buffer = tesselator.begin(VertexFormatCompat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        //#else
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormatCompat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        //#endif
        //#endif
        voxelShape.forAllEdges((minX, minY, minZ, maxX, maxY, maxZ) -> {
            //#if MC >= 1.21.11
            //$$ builder.addVertex((float) (minX + x), (float) (minY + y), (float) (minZ + z)).setColor(color.r, color.g, color.b, color.a).setLineWidth(1.0F);
            //$$ builder.addVertex((float) (maxX + x), (float) (maxY + y), (float) (maxZ + z)).setColor(color.r, color.g, color.b, color.a).setLineWidth(1.0F);
            //#elseif MC >= 12105
            //$$ builder.addVertex((float) (minX + x), (float) (minY + y), (float) (minZ + z)).setColor(color.r, color.g, color.b, color.a);
            //$$ builder.addVertex((float) (maxX + x), (float) (maxY + y), (float) (maxZ + z)).setColor(color.r, color.g, color.b, color.a);
            //#elseif MC > 12006
            //$$ buffer.addVertex((float) (minX + x), (float) (minY + y), (float) (minZ + z)).setColor(color.r, color.g, color.b, color.a);
            //$$ buffer.addVertex((float) (maxX + x), (float) (maxY + y), (float) (maxZ + z)).setColor(color.r, color.g, color.b, color.a);
            //#else
            buffer.vertex(minX + x, minY + y, minZ + z).color(color.r, color.g, color.b, color.a).endVertex();
            buffer.vertex(maxX + x, maxY + y, maxZ + z).color(color.r, color.g, color.b, color.a).endVertex();
            //#endif
        });
        //#if MC > 12104
        //$$
        //$$ try {
        //$$     MeshData meshData = builder.build();
        //$$
        //$$     if (meshData != null) {
        //$$         ctx.draw(meshData, false, true);
        //$$         meshData.close();
        //$$     }
        //$$
        //$$     ctx.close();
        //$$ } catch (Exception ignored) {
        //$$     // NO-OP
        //$$ }
        //#elseif MC > 12006
        //$$ RenderUtil.end(buffer);
        //#else
        tesselator.end();
        //#endif

        //#if MC < 11700
        RenderGlobal.enableTexture();
        //#endif
        // TODO: Migrate to RenderGlobal.
        //#if MC >= 12105
        //$$ GlStateManager._enableCull();
        //#elseif MC > 11404
        RenderSystem.enableCull();
        //#else
        //$$ GlStateManager.enableCull();
        //#endif
        RenderGlobal.disableBlend();

        //#if MC < 12105
        if (!depthTest) {
            RenderGlobal.enableDepthTest();
        }
        //#endif
    }

    public static void renderShapeOverlay(@NotNull VoxelShape voxelShape, double x, double y, double z, Color4f color, boolean depthTest) {
        //#if MC < 12105
        if (!depthTest) {
            RenderGlobal.disableDepthTest();
        }
        //#endif

        RenderGlobal.enableBlend();
        // TODO: Migrate to RenderGlobal.
        //#if MC >= 12105
        //$$ GlStateManager._disableCull();
        //#elseif MC > 11404
        RenderSystem.disableCull();
        //#else
        //$$ GlStateManager.disableCull();
        //#endif
        //#if MC < 11700
        RenderGlobal.disableTexture();
        //#endif
        //#if 12102 > MC && MC > 11605
        //$$ RenderGlobal.setShader(GameRenderer::getPositionColorShader);
        //#endif

        //#if MC > 12104
        //$$ RenderContext ctx = new RenderContext(
        //$$         // CHECKSTYLE.OFF: NoWhitespaceBefore
        //$$         // CHECKSTYLE.OFF: SeparatorWrap
        //$$         //#if MC >= 12107
        //$$         //$$ () -> "tweak_my_client:shape_overlay",
        //$$         //#endif
        //$$         depthTest ? MaLiLibPipelines.POSITION_COLOR_MASA : MaLiLibPipelines.POSITION_COLOR_MASA_NO_DEPTH_NO_CULL
        //$$         //#if MC < 12106
        //$$         , BufferUsage.STATIC_WRITE
        //$$         //#endif
        //$$         // CHECKSTYLE.ON: SeparatorWrap
        //$$         // CHECKSTYLE.ON: NoWhitespaceBefore
        //$$ );
        //$$ BufferBuilder builder = ctx.getBuilder();
        //#else
        Tesselator tesselator = Tesselator.getInstance();
        //#if MC > 12006
        //$$ BufferBuilder buffer = tesselator.begin(VertexFormatCompat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        //#else
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormatCompat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        //#endif
        //#endif
        voxelShape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) ->
                RenderUtils.drawBoxAllSidesBatchedQuads(
                        //#if MC > 12006
                        //$$ (float) (minX + x), (float) (minY + y), (float) (minZ + z),
                        //$$ (float) (maxX + x), (float) (maxY + y), (float) (maxZ + z),
                        //#else
                        minX + x, minY + y, minZ + z,
                        maxX + x, maxY + y, maxZ + z,
                        //#endif
                        color,
                        //#if MC >= 12105
                        //$$ builder
                        //#else
                        buffer
                        //#endif
                )
        );
        //#if MC > 12104
        //$$
        //$$ try {
        //$$     MeshData meshData = builder.build();
        //$$
        //$$     if (meshData != null) {
        //$$         ctx.draw(meshData, false, true);
        //$$         meshData.close();
        //$$     }
        //$$
        //$$     ctx.close();
        //$$ } catch (Exception ignored) {
        //$$     // NO-OP
        //$$ }
        //#elseif MC > 12006
        //$$ RenderUtil.end(buffer);
        //#else
        tesselator.end();
        //#endif

        //#if MC < 11700
        RenderGlobal.enableTexture();
        //#endif
        // TODO: Migrate to RenderGlobal.
        //#if MC >= 12105
        //$$ GlStateManager._enableCull();
        //#elseif MC > 11404
        RenderSystem.enableCull();
        //#else
        //$$ GlStateManager.enableCull();
        //#endif
        RenderGlobal.disableBlend();

        //#if MC < 12105
        if (!depthTest) {
            RenderGlobal.enableDepthTest();
        }
        //#endif
    }

    //#if 12105 > MC && MC > 12006
    //$$ private static void end(BufferBuilder builder) {
    //$$     try (MeshData meshData = builder.buildOrThrow()) {
    //$$         BufferUploader.drawWithShader(meshData);
    //$$     } catch (Exception ignore) {
    //$$         // NO-OP
    //$$     }
    //$$ }
    //#endif
}
