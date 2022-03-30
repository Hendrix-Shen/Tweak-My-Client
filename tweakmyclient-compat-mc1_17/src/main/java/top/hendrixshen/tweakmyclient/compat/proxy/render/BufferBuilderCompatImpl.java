package top.hendrixshen.tweakmyclient.compat.proxy.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.GameRenderer;

public class BufferBuilderCompatImpl extends BufferBuilderCompatApi {
    public static void initCompat() {
        INSTANCE = new BufferBuilderCompatImpl();
    }

    @Override
    public void beginGLLines(BufferBuilder bufferBuilder) {
        bufferBuilder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
    }

    @Override
    public void compat_1_17_above() {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.applyModelViewMatrix();
    }
}
