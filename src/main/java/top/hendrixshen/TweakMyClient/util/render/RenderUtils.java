package top.hendrixshen.TweakMyClient.util.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import fi.dy.masa.malilib.util.Color4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class RenderUtils {
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

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.applyModelViewMatrix();
        bufferbuilder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

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
}
