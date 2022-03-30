package top.hendrixshen.tweakmyclient.compat.proxy.render;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import org.lwjgl.opengl.GL11;

public class BufferBuilderCompatImpl extends BufferBuilderCompatApi {
    public static void initCompat() {
        INSTANCE = new BufferBuilderCompatImpl();
    }

    @Override
    public void beginGLLines(BufferBuilder bufferBuilder) {
        bufferBuilder.begin(GL11.GL_LINES, DefaultVertexFormat.POSITION_COLOR);
    }

    @Override
    public void compat_1_17_above() {

    }
}
