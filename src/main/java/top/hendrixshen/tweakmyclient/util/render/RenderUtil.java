package top.hendrixshen.tweakmyclient.util.render;

//#if MC >= 11500
import com.mojang.blaze3d.systems.RenderSystem;
//#else
//$$ import com.mojang.blaze3d.platform.GlStateManager;
//#endif
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
//#if MC >= 11600
import net.minecraft.world.phys.Vec3;
//#endif
import net.minecraft.world.phys.shapes.VoxelShape;
import top.hendrixshen.magiclib.compat.minecraft.blaze3d.vertex.VertexFormatCompatApi;
//#if MC < 11700
//$$ import org.lwjgl.opengl.GL11;
//#endif

public class RenderUtil {
    //#if MC >= 11600
    public static void renderAreaOutline(BlockPos pos1, BlockPos pos2, float lineWidth, Color4f colorX, Minecraft minecraft) {
        //#if MC >= 11500
        RenderSystem.lineWidth(lineWidth);
        //#else
        //$$ GlStateManager.lineWidth(lineWidth);
        //#endif

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

        drawBoundingBoxEdges(minX, minY, minZ, maxX, maxY, maxZ, colorX);
    }

    private static void drawBoundingBoxEdges(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color4f color) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();

        //#if MC > 11700
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.applyModelViewMatrix();
        bufferbuilder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
        //#else
        //$$ bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormat.POSITION_COLOR);
        //#endif

        RenderUtils.drawBoxAllEdgesBatchedLines(minX, minY, minZ, maxX, maxY, maxZ, color, bufferbuilder);

        tesselator.end();
    }

    //#endif
    public static void renderShapeOverlay(VoxelShape voxelShape,
                                          double x, double y, double z, Color4f color4f) {
        //#if MC < 11700
        //#if MC >= 11500
        //$$ RenderSystem.disableTexture();
        //#else
        //$$ GlStateManager.disableTexture();
        //#endif
        //#endif
        //#if MC >= 11500
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        //#else
        //$$ GlStateManager.enableBlend();
        //$$ GlStateManager.disableCull();
        //#endif
        //#if MC >= 11700
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        //#endif
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormatCompatApi.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        voxelShape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) ->
                RenderUtils.drawBoxAllSidesBatchedQuads(minX + x, minY + y, minZ + z,
                        maxX + x, maxY + y, maxZ + z, color4f, buffer));

        tesselator.end();
        //#if MC >= 11500
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        //#else
        //$$ GlStateManager.enableCull();
        //$$ GlStateManager.disableBlend();
        //#endif
        //#if MC < 11700
        //#if MC >= 11500
        //$$ RenderSystem.enableTexture();
        //#else
        //$$ GlStateManager.enableTexture();
        //#endif
        //#endif
    }

    public static void renderShapeOutline(VoxelShape voxelShape, float lineWidth, double x, double y, double z, Color4f color4f) {
        //#if MC < 11700
        //#if MC >= 11500
        //$$ RenderSystem.disableTexture();
        //#else
        //$$ GlStateManager.disableTexture();
        //#endif
        //#endif
        //#if MC >= 11500
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        //#else
        //$$ GlStateManager.enableBlend();
        //$$ GlStateManager.disableCull();
        //#endif
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

        //#if MC >= 11500
        RenderSystem.lineWidth(lineWidth);
        //#else
        //$$ GlStateManager.lineWidth(lineWidth);
        //#endif

        voxelShape.forAllEdges((minX, minY, minZ, maxX, maxY, maxZ) -> {
            buffer.vertex(minX + x, minY + y, minZ + z).color(color4f.r, color4f.g, color4f.b, color4f.a).endVertex();
            buffer.vertex(maxX + x, maxY + y, maxZ + z).color(color4f.r, color4f.g, color4f.b, color4f.a).endVertex();
        });

        tesselator.end();
        //#if MC >= 11500
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        //#else
        //$$ GlStateManager.enableCull();
        //$$ GlStateManager.disableBlend();
        //#endif

        //#if MC < 11700
        //#if MC >= 11500
        //$$ RenderSystem.enableTexture();
        //#else
        //$$ GlStateManager.enableTexture();
        //#endif
        //#endif
    }
}
