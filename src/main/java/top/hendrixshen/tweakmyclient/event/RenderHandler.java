package top.hendrixshen.tweakmyclient.event;

import com.google.common.collect.Lists;
import fi.dy.masa.malilib.event.RenderEventHandler;
import fi.dy.masa.malilib.interfaces.IRenderer;
import lombok.Getter;
import top.hendrixshen.tweakmyclient.util.render.CustomBlockHitBoxRenderer;
import top.hendrixshen.tweakmyclient.util.render.RestrictionBoxRenderer;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;

import java.util.List;

//#if MC > 11404
import com.mojang.blaze3d.vertex.PoseStack;
//#endif

//#if MC > 11502
import org.joml.Matrix4f;
import top.hendrixshen.tweakmyclient.util.render.OpenWaterHelperRenderer;
//#endif

public class RenderHandler implements IRenderer {
    @Getter(lazy = true)
    private static final RenderHandler instance = new RenderHandler();
    private final List<top.hendrixshen.tweakmyclient.util.render.IRenderer> worldLastRenderer = Lists.newArrayList();

    public void init() {
        RenderEventHandler.getInstance().registerWorldLastRenderer(RenderHandler.getInstance());
        RenderHandler.getInstance().registerWorldLastRenderer(CustomBlockHitBoxRenderer.getInstance());
        //#if MC > 11502
        RenderHandler.getInstance().registerWorldLastRenderer(OpenWaterHelperRenderer.getInstance());
        //#endif
        RenderHandler.getInstance().registerWorldLastRenderer(RestrictionBoxRenderer.getInstance());
    }

    public void registerWorldLastRenderer(top.hendrixshen.tweakmyclient.util.render.IRenderer renderer) {
        if (this.worldLastRenderer.contains(renderer)) {
            TweakMyClientReference.getLogger().warn("Renderer {} has been registered!", renderer);
            return;
        }

        this.worldLastRenderer.add(renderer);
    }

    @Override
    //#if MC > 11605
    public void onRenderWorldLast(PoseStack poseStack, Matrix4f matrix4f) {
    //#elseif MC > 11404
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
