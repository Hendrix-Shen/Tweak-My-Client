package top.hendrixshen.tweakmyclient.util.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.Color4f;
//#if MC >= 11700
import net.minecraft.client.renderer.GameRenderer;
//#endif
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import top.hendrixshen.magiclib.compat.minecraft.blaze3d.vertex.VertexFormatCompatApi;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.helper.AreaBox;
//#if MC < 11700
//$$ import org.lwjgl.opengl.GL11;
//#endif

public class RenderUtil {
    public static void renderAreaOutline(double minX, double minY, double minZ,
                                         double maxX, double maxY, double maxZ, Color4f color) {
        Vec3 cameraPos = TweakMyClient.getMinecraftClient().gameRenderer.getMainCamera().getPosition();
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
        Vec3 cameraPos = TweakMyClient.getMinecraftClient().gameRenderer.getMainCamera().getPosition();
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
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        //#if MC > 11700
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferbuilder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        //#else
        //$$ bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormat.POSITION_COLOR);
        //#endif
        RenderUtils.drawBoxAllEdgesBatchedLines(minX, minY, minZ, maxX, maxY, maxZ, color, bufferbuilder);
        tesselator.end();
    }

    private static void drawBoundingBoxOverlay(double minX, double minY, double minZ,
                                              double maxX, double maxY, double maxZ, @NotNull Color4f color) {
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        //#if MC >= 11700
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        //#else
        //$$ RenderSystem.disableTexture();
        //#endif
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        bufferBuilder.begin(VertexFormatCompatApi.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        RenderUtils.drawBoxAllSidesBatchedQuads(minX, minY, minZ, maxX, maxY, maxZ, color, bufferBuilder);
        tesselator.end();
        RenderSystem.disableBlend();
        RenderSystem.enableCull();
        //#if MC < 11700
        //$$ RenderSystem.enableTexture();
        //#endif
    }

    public static void renderShapeOverlay(@NotNull VoxelShape voxelShape, double x, double y, double z, Color4f color) {
        //#if MC < 11700
        //$$ RenderSystem.disableTexture();
        //#endif
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        //#if MC >= 11700
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        //#endif
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormatCompatApi.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        voxelShape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) ->
                RenderUtils.drawBoxAllSidesBatchedQuads(minX + x, minY + y, minZ + z,
                        maxX + x, maxY + y, maxZ + z, color, buffer));

        tesselator.end();
        //#if MC >= 11500
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        //#endif
        //#if MC < 11700
        //$$ RenderSystem.enableTexture();
        //#endif
    }

    public static void renderShapeOutline(@NotNull VoxelShape voxelShape, double x, double y, double z, Color4f color) {
        //#if MC < 11700
        //$$ RenderSystem.disableTexture();
        //#endif
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        //#if MC >= 11700
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        //#endif
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        //#if MC >= 11700
        buffer.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        //#else
        //$$ buffer.begin(GL11.GL_LINES, DefaultVertexFormat.POSITION_COLOR);
        //#endif

        voxelShape.forAllEdges((minX, minY, minZ, maxX, maxY, maxZ) -> {
            buffer.vertex(minX + x, minY + y, minZ + z).color(color.r, color.g, color.b, color.a).endVertex();
            buffer.vertex(maxX + x, maxY + y, maxZ + z).color(color.r, color.g, color.b, color.a).endVertex();
        });

        tesselator.end();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();

        //#if MC < 11700
        //$$ RenderSystem.enableTexture();
        //#endif
    }
}
