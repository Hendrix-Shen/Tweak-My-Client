package top.hendrixshen.tweakmyclient.util;

import com.mojang.blaze3d.vertex.*;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.Color4f;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import top.hendrixshen.magiclib.api.compat.mojang.blaze3d.vertex.VertexFormatCompat;
import top.hendrixshen.magiclib.impl.render.context.RenderGlobal;

//#if MC > 11605
//$$ import net.minecraft.client.renderer.GameRenderer;
//#else
import org.lwjgl.opengl.GL11;
//#endif

//#if MC > 11404
import com.mojang.blaze3d.systems.RenderSystem;
//#else
//$$ import com.mojang.blaze3d.platform.GlStateManager;
//#endif

public class RenderUtil {
    public static void renderAreaOutline(double minX, double minY, double minZ,
                                         double maxX, double maxY, double maxZ, Color4f color) {
        Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        final double dx = cameraPos.x;
        final double dy = cameraPos.y;
        final double dz = cameraPos.z;

        RenderUtil.drawBoundingBoxEdges(minX - dx, minY - dy, minZ - dz,
                maxX - dx + 1, maxY - dy + 1, maxZ - dz + 1, color);
    }

    public static void renderAreaOutline(@NotNull BlockPos pos1, @NotNull BlockPos pos2, Color4f color) {
        RenderUtil.renderAreaOutline(Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(), pos2.getY()),
                Math.min(pos1.getZ(), pos2.getZ()), Math.max(pos1.getX(), pos2.getX()),
                Math.max(pos1.getY(), pos2.getY()), Math.max(pos1.getZ(), pos2.getZ()), color);
    }

    public static void renderAreaOutline(@NotNull AreaBox areaBox, Color4f color) {
        RenderUtil.renderAreaOutline(areaBox.getMinX(), areaBox.getMinY(), areaBox.getMinZ(),
                areaBox.getMaxX(), areaBox.getMaxY(), areaBox.getMaxZ(), color);
    }

    public static void renderAreaOverlay(double minX, double minY, double minZ,
                                         double maxX, double maxY, double maxZ, Color4f color) {
        Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        final double dx = cameraPos.x;
        final double dy = cameraPos.y;
        final double dz = cameraPos.z;

        RenderUtil.drawBoundingBoxOverlay(minX - dx, minY - dy, minZ - dz,
                maxX - dx + 1, maxY - dy + 1, maxZ - dz + 1, color);
    }

    public static void renderAreaOverlay(@NotNull BlockPos pos1, @NotNull BlockPos pos2, Color4f color) {
        RenderUtil.renderAreaOverlay(Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(), pos2.getY()),
                Math.min(pos1.getZ(), pos2.getZ()), Math.max(pos1.getX(), pos2.getX()),
                Math.max(pos1.getY(), pos2.getY()), Math.max(pos1.getZ(), pos2.getZ()), color);
    }

    public static void renderAreaOverlay(@NotNull AreaBox areaBox, Color4f color4f) {
        RenderUtil.renderAreaOverlay(areaBox.getMinX(), areaBox.getMinY(), areaBox.getMinZ(),
                areaBox.getMaxX(), areaBox.getMaxY(), areaBox.getMaxZ(), color4f);
    }

    private static void drawBoundingBoxEdges(double minX, double minY, double minZ,
                                             double maxX, double maxY, double maxZ, @NotNull Color4f color) {
        Tesselator tesselator = Tesselator.getInstance();
        //#if MC > 12006
        //$$ BufferBuilder buffer = tesselator.begin(VertexFormatCompat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        //#else
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormatCompat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        //#endif
        //#if MC > 11700
        //$$ RenderSystem.setShader(GameRenderer::getPositionColorShader);
        //#endif
        RenderUtils.drawBoxAllEdgesBatchedLines(
                //#if MC > 12006
                //$$ (float) minX, (float) minY, (float) minZ,
                //$$ (float) maxX, (float) maxY, (float) maxZ,
                //#else
                minX, minY, minZ, maxX, maxY, maxZ,
                //#endif
                color, buffer);
        //#if MC > 12006
        //$$ RenderUtil.end(buffer);
        //#else
        tesselator.end();
        //#endif
    }

    private static void drawBoundingBoxOverlay(double minX, double minY, double minZ,
                                              double maxX, double maxY, double maxZ, @NotNull Color4f color) {
        RenderGlobal.enableBlend();
        // TODO: Migrate to RenderGlobal.
        //#if MC > 11404
        RenderSystem.disableCull();
        //#else
        //$$ GlStateManager.disableCull();
        //#endif
        //#if MC > 11605
        //$$ RenderGlobal.setShader(GameRenderer::getPositionColorShader);
        //#else
        RenderGlobal.disableTexture();
        //#endif
        Tesselator tesselator = Tesselator.getInstance();
        //#if MC > 12006
        //$$ BufferBuilder buffer = tesselator.begin(VertexFormatCompat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        //#else
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormatCompat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        //#endif
        RenderUtils.drawBoxAllSidesBatchedQuads(
                //#if MC > 12006
                //$$ (float) minX, (float) minY, (float) minZ,
                //$$ (float) maxX, (float) maxY, (float) maxZ,
                //#else
                minX, minY, minZ, maxX, maxY, maxZ,
                //#endif
                color, buffer);
        //#if MC > 12006
        //$$ RenderUtil.end(buffer);
        //#else
        tesselator.end();
        //#endif
        RenderGlobal.disableBlend();
        // TODO: Migrate to RenderGlobal.
        //#if MC > 11404
        RenderSystem.enableCull();
        //#else
        //$$ GlStateManager.enableCull();
        //#endif
        //#if MC < 11700
        RenderGlobal.enableTexture();
        //#endif
    }

    public static void renderShapeOverlay(@NotNull VoxelShape voxelShape, double x, double y, double z, Color4f color) {
        //#if MC < 11700
        RenderGlobal.disableTexture();
        //#endif
        RenderGlobal.enableBlend();
        // TODO: Migrate to RenderGlobal.
        //#if MC > 11404
        RenderSystem.disableCull();
        //#else
        //$$ GlStateManager.disableCull();
        //#endif
        //#if MC > 11605
        //$$ RenderGlobal.setShader(GameRenderer::getPositionColorShader);
        //#endif
        Tesselator tesselator = Tesselator.getInstance();
        //#if MC > 12006
        //$$ BufferBuilder buffer = tesselator.begin(VertexFormatCompat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        //#else
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormatCompat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
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
                        color, buffer));
        //#if MC > 12006
        //$$ RenderUtil.end(buffer);
        //#else
        tesselator.end();
        //#endif
        RenderGlobal.disableBlend();
        // TODO: Migrate to RenderGlobal.
        //#if MC > 11404
        RenderSystem.enableCull();
        //#else
        //$$ GlStateManager.enableCull();
        //#endif
        //#if MC < 11700
        RenderGlobal.enableTexture();
        //#endif
    }

    public static void renderShapeOutline(@NotNull VoxelShape voxelShape, double x, double y, double z, Color4f color) {
        //#if MC < 11700
        RenderGlobal.disableTexture();
        //#endif
        RenderGlobal.enableBlend();
        // TODO: Migrate to RenderGlobal.
        //#if MC > 11404
        RenderSystem.disableCull();
        //#else
        //$$ GlStateManager.disableCull();
        //#endif
        //#if MC >= 11700
        //$$ RenderGlobal.setShader(GameRenderer::getPositionColorShader);
        //#endif
        Tesselator tesselator = Tesselator.getInstance();
        //#if MC > 12006
        //$$ BufferBuilder buffer = tesselator.begin(VertexFormatCompat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        //#else
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormatCompat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        //#endif
        voxelShape.forAllEdges((minX, minY, minZ, maxX, maxY, maxZ) -> {
            //#if MC > 12006
            //$$ buffer.addVertex((float) (minX + x), (float) (minY + y), (float) (minZ + z)).setColor(color.r, color.g, color.b, color.a);
            //$$ buffer.addVertex((float) (maxX + x), (float) (maxY + y), (float) (maxZ + z)).setColor(color.r, color.g, color.b, color.a);
            //#else
            buffer.vertex(minX + x, minY + y, minZ + z).color(color.r, color.g, color.b, color.a).endVertex();
            buffer.vertex(maxX + x, maxY + y, maxZ + z).color(color.r, color.g, color.b, color.a).endVertex();
            //#endif
        });
        //#if MC > 12006
        //$$ RenderUtil.end(buffer);
        //#else
        tesselator.end();
        //#endif
        // TODO: Migrate to RenderGlobal.
        //#if MC > 11404
        RenderSystem.enableCull();
        //#else
        //$$ GlStateManager.enableCull();
        //#endif
        RenderGlobal.disableBlend();
        //#if MC < 11700
        RenderGlobal.enableTexture();
        //#endif
    }

    //#if MC > 12006
    //$$ private static void end(BufferBuilder builder) {
    //$$     try (MeshData meshData = builder.buildOrThrow()) {
    //$$         BufferUploader.drawWithShader(meshData);
    //$$     } catch (Exception ignore) {
    //$$     }
    //$$ }
    //#endif
}
