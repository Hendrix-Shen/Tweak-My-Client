package top.hendrixshen.tweakmyclient.compat.proxy.render;

import com.mojang.blaze3d.vertex.BufferBuilder;

public abstract class BufferBuilderCompatApi {
    protected static BufferBuilderCompatApi INSTANCE;

    public static BufferBuilderCompatApi getInstance() {
        return INSTANCE;
    }

    public abstract void beginGLLines(BufferBuilder bufferBuilder);

    public abstract void compat_1_17_above();
}
