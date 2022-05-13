package top.hendrixshen.tweakmyclient.util.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.Color4f;
//#if MC >= 11600
import net.minecraft.client.Minecraft;
//#endif
//#if MC >= 11700
import net.minecraft.client.renderer.GameRenderer;
//#endif
//#if MC >= 11600
import net.minecraft.core.BlockPos;
//#endif
import net.minecraft.world.phys.AABB;
//#if MC >= 11600
import net.minecraft.world.phys.Vec3;
//#endif
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import top.hendrixshen.magiclib.compat.minecraft.blaze3d.vertex.VertexFormatCompatApi;
//#if MC < 11700
//$$ import org.lwjgl.opengl.GL11;
//#endif

public class RenderUtil {
    //#if MC >= 11600
    public static void renderAreaOutline(BlockPos pos1, BlockPos pos2, float lineWidth, Color4f colorX, Color4f colorY, Color4f colorZ, Minecraft minecraft) {
        RenderSystem.lineWidth(lineWidth);

        Vec3 cameraPos = minecraft.gameRenderer.getMainCamera().getPosition();
        final double dx = cameraPos.x;
        final double dy = cameraPos.y;
        final double dz = cameraPos.z;

        double minX = Math.min(pos1.getX(), pos2.getX()) - dx;
        double minY = Math.min(pos1.getY(), pos2.getY()) - dy;
        double minZ = Math.min(pos1.getZ(), pos2.getZ()) - dz;
        double maxX = Math.max(pos1.getX(), pos2.getX()) - dx + 1;
        double maxY = Math.max(pos1.getY(), pos2.getY()) - dy + 1;
        double maxZ = Math.max(pos1.getZ(), pos2.getZ()) - dz + 1;

        drawBoundingBoxEdges(minX, minY, minZ, maxX, maxY, maxZ, colorX, colorY, colorZ);
    }

    private static void drawBoundingBoxEdges(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color4f colorX, Color4f colorY, Color4f colorZ) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();

        //#if MC > 11700
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.applyModelViewMatrix();
        bufferbuilder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        //#else
        //$$ bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormat.POSITION_COLOR);
        //#endif

        drawBoundingBoxLinesX(bufferbuilder, minX, minY, minZ, maxX, maxY, maxZ, colorX);
        drawBoundingBoxLinesY(bufferbuilder, minX, minY, minZ, maxX, maxY, maxZ, colorY);
        drawBoundingBoxLinesZ(bufferbuilder, minX, minY, minZ, maxX, maxY, maxZ, colorZ);

        tesselator.end();
    }

    private static void drawBoundingBoxLinesX(BufferBuilder buffer, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color4f color) {
        buffer.vertex(minX, minY, minZ).color(color.r, color.g, color.b, color.a).endVertex();
        buffer.vertex(maxX, minY, minZ).color(color.r, color.g, color.b, color.a).endVertex();

        buffer.vertex(minX, maxY, minZ).color(color.r, color.g, color.b, color.a).endVertex();
        buffer.vertex(maxX, maxY, minZ).color(color.r, color.g, color.b, color.a).endVertex();

        buffer.vertex(minX, minY, maxZ).color(color.r, color.g, color.b, color.a).endVertex();
        buffer.vertex(maxX, minY, maxZ).color(color.r, color.g, color.b, color.a).endVertex();

        buffer.vertex(minX, maxY, maxZ).color(color.r, color.g, color.b, color.a).endVertex();
        buffer.vertex(maxX, maxY, maxZ).color(color.r, color.g, color.b, color.a).endVertex();
    }

    private static void drawBoundingBoxLinesY(BufferBuilder buffer, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color4f color) {
        buffer.vertex(minX, minY, minZ).color(color.r, color.g, color.b, color.a).endVertex();
        buffer.vertex(minX, maxY, minZ).color(color.r, color.g, color.b, color.a).endVertex();

        buffer.vertex(maxX, minY, minZ).color(color.r, color.g, color.b, color.a).endVertex();
        buffer.vertex(maxX, maxY, minZ).color(color.r, color.g, color.b, color.a).endVertex();

        buffer.vertex(minX, minY, maxZ).color(color.r, color.g, color.b, color.a).endVertex();
        buffer.vertex(minX, maxY, maxZ).color(color.r, color.g, color.b, color.a).endVertex();

        buffer.vertex(maxX, minY, maxZ).color(color.r, color.g, color.b, color.a).endVertex();
        buffer.vertex(maxX, maxY, maxZ).color(color.r, color.g, color.b, color.a).endVertex();
    }

    private static void drawBoundingBoxLinesZ(BufferBuilder buffer, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color4f color) {
        buffer.vertex(minX, minY, minZ).color(color.r, color.g, color.b, color.a).endVertex();
        buffer.vertex(minX, minY, maxZ).color(color.r, color.g, color.b, color.a).endVertex();

        buffer.vertex(maxX, minY, minZ).color(color.r, color.g, color.b, color.a).endVertex();
        buffer.vertex(maxX, minY, maxZ).color(color.r, color.g, color.b, color.a).endVertex();

        buffer.vertex(minX, maxY, minZ).color(color.r, color.g, color.b, color.a).endVertex();
        buffer.vertex(minX, maxY, maxZ).color(color.r, color.g, color.b, color.a).endVertex();

        buffer.vertex(maxX, maxY, minZ).color(color.r, color.g, color.b, color.a).endVertex();
        buffer.vertex(maxX, maxY, maxZ).color(color.r, color.g, color.b, color.a).endVertex();
    }

    //#endif
    public static void renderShapeOverlay(VoxelShape voxelShape, double x, double y, double z, Color4f color4f) {
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

        VoxelShape optimizedVoxelShape = voxelShape.toAabbs().stream()
                .map(box -> box.inflate(0.005, 0.005, 0.005))
                .map(Shapes::create)
                .reduce(Shapes::or)
                .orElse(Shapes.empty()).optimize();
        for (AABB aabb : optimizedVoxelShape.toAabbs()) {
            RenderUtils.drawBoxAllSidesBatchedQuads(aabb.minX + x, aabb.minY + y, aabb.minZ + z,
                    aabb.maxX + x, aabb.maxY + y, aabb.maxZ + z, color4f, buffer);
        }
        tesselator.end();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        //#if MC < 11700
        //$$ RenderSystem.enableTexture();
        //#endif
    }
}
