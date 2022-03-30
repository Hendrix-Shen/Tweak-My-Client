package top.hendrixshen.tweakmyclient.compat.event;

import com.mojang.blaze3d.vertex.PoseStack;
import fi.dy.masa.malilib.interfaces.IRenderer;
import net.minecraft.client.Minecraft;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.render.OverlayRenderer;

public class RenderHandler implements IRenderer {
    private static final RenderHandler INSTANCE = new RenderHandler();
    private final Minecraft minecraft;

    public RenderHandler() {
        this.minecraft = TweakMyClient.getMinecraftClient();
    }

    public static RenderHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void onRenderWorldLast(float partialTicks, PoseStack matrixStack) {
        if (Configs.featureOpenWaterHelper) {
            OverlayRenderer.getInstance().renderOpenWater(minecraft);
        }
    }
}
