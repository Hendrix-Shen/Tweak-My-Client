package top.hendrixshen.tweakmyclient.event;

import com.google.common.collect.Lists;
//#if MC >= 11500
import com.mojang.blaze3d.vertex.PoseStack;
//#endif
//#if MC > 11600
import com.mojang.math.Matrix4f;
//#endif
import fi.dy.masa.malilib.interfaces.IRenderer;
import top.hendrixshen.tweakmyclient.TweakMyClient;

import java.util.List;

public class RenderHandler implements IRenderer {
    private static final RenderHandler INSTANCE = new RenderHandler();
    private final List<top.hendrixshen.tweakmyclient.util.render.IRenderer> worldLastRenderer = Lists.newArrayList();

    public static RenderHandler getInstance() {
        return INSTANCE;
    }

    public void registerWorldLastRenderer(top.hendrixshen.tweakmyclient.util.render.IRenderer renderer) {
        if (this.worldLastRenderer.contains(renderer)) {
            TweakMyClient.getLogger().warn("Renderer {} has been registered!", renderer);
            return;
        }
        this.worldLastRenderer.add(renderer);
    }

    @Override
    //#if MC >= 11700
    public void onRenderWorldLast(PoseStack poseStack, Matrix4f matrix4f) {
    //#elseif MC >= 11500
    //$$ public void onRenderWorldLast(float partialTicks, PoseStack poseStack) {
    //#else
    //$$ public void onRenderWorldLast(float partialTicks) {
    //#endif
        for (top.hendrixshen.tweakmyclient.util.render.IRenderer renderer : this.worldLastRenderer) {
            if (renderer.shouldRender()) {
                renderer.render();
            }
        }
    }
}
