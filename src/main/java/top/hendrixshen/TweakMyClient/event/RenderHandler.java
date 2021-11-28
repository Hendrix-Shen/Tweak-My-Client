package top.hendrixshen.TweakMyClient.event;

import com.mojang.blaze3d.vertex.PoseStack;
import fi.dy.masa.malilib.interfaces.IRenderer;
import net.minecraft.client.Minecraft;
import top.hendrixshen.TweakMyClient.TweakMyClient;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.util.render.OverlayRenderer;

public class RenderHandler implements IRenderer {
    private static final RenderHandler INSTANCE = new RenderHandler();
    private final Minecraft minecraft;

    public RenderHandler() {
        this.minecraft = TweakMyClient.getMinecraftClient();
    }

    public static RenderHandler getInstance() {
        return INSTANCE;
    }


    public void onRenderWorldLast(float partialTicks, PoseStack matrixStack) {
        if (Configs.Feature.FEATURE_OPEN_WATER_HELPER.getBooleanValue()) {
            OverlayRenderer.getInstance().renderOpenWater(minecraft);
        }
    }
}
