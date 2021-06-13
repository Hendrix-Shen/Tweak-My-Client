package top.hendrixshen.TweakMyClient.event;

import fi.dy.masa.malilib.interfaces.IRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import top.hendrixshen.TweakMyClient.TweakMyClient;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.util.render.OverlayRenderer;

public class RenderHandler implements IRenderer {
    private static final RenderHandler INSTANCE = new RenderHandler();
    private final MinecraftClient mc;

    public RenderHandler() {
        this.mc = TweakMyClient.minecraftClient;
    }

    public static RenderHandler getInstance() {
        return INSTANCE;
    }

    public void onRenderWorldLast(float partialTicks, MatrixStack matrixStack) {
        if (Configs.Feature.FEATURE_OPEN_WATER_HELPER.getBooleanValue()) {
            OverlayRenderer.getInstance().renderOpenWater(mc);
        }
    }
}
