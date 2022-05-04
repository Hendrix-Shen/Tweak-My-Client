package top.hendrixshen.tweakmyclient.event;

//#if MC >= 11600
import com.mojang.blaze3d.vertex.PoseStack;
//#if MC > 11600
import com.mojang.math.Matrix4f;
//#endif
import fi.dy.masa.malilib.interfaces.IRenderer;
import net.minecraft.client.Minecraft;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.render.OverlayRenderer;
//#endif

//#if MC >= 11600
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
    //#if MC >= 11700
    public void onRenderWorldLast(PoseStack poseStack, Matrix4f matrix4f) {
    //#else
    //$$ public void onRenderWorldLast(float partialTicks, PoseStack poseStack) {
    //#endif
        if (Configs.featureOpenWaterHelper) {
            OverlayRenderer.getInstance().renderOpenWater(minecraft);
        }
    }
//#else
//$$ public class RenderHandler {
//#endif
}
