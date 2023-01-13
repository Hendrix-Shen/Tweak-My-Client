package top.hendrixshen.tweakmyclient.util;

//#if MC >= 11800
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import fi.dy.masa.litematica.util.RayTraceUtils;
import fi.dy.masa.litematica.world.SchematicWorldHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.Jade;
import snownee.jade.JadeClient;
import snownee.jade.api.Accessor;
import snownee.jade.api.callback.JadeRayTraceCallback;
import snownee.jade.api.callback.JadeTooltipCollectedCallback;
import snownee.jade.api.config.IWailaConfig;
import snownee.jade.gui.BaseOptionsScreen;
import snownee.jade.impl.ObjectDataCenter;
import snownee.jade.impl.Tooltip;
import snownee.jade.impl.WailaClientRegistration;
import snownee.jade.impl.WailaCommonRegistration;
import snownee.jade.overlay.DatapackBlockManager;
import snownee.jade.overlay.OverlayRenderer;
import snownee.jade.overlay.TooltipRenderer;
//#if MC > 11802
import snownee.jade.util.ClientPlatformProxy;
//#endif
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.mixin.accessor.PlayerTabOverlayAccessor;
//#endif

public class JadeUtil {
    //#if MC >= 11800
    private static TooltipRenderer tooltipRenderer = null;
    private static boolean disableJadeRender = false;
    private static final Minecraft minecraft = TweakMyClient.getMinecraftClient();

    public static boolean shouldDisableJadeRender() {
        return JadeUtil.disableJadeRender;
    }

    //#if MC < 11900
    //$$ @SuppressWarnings("deprecation")
    //#endif
    public static void tick() {
        JadeUtil.disableJadeRender = false;
        IWailaConfig.IConfigGeneral config = Jade.CONFIG.get().getGeneral();

        if (minecraft.level == null || minecraft.cameraEntity == null) {
            JadeUtil.tooltipRenderer = null;
            return;
        }

        RayTraceUtils.RayTraceWrapper traceWrapper = RayTraceUtils.getGenericTrace(minecraft.level, minecraft.cameraEntity, 10.0, true, true, true);
        Level worldSchematic = SchematicWorldHandler.getSchematicWorld();
        if (traceWrapper == null || traceWrapper.getBlockHitResult() == null || worldSchematic == null ||
                worldSchematic.getBlockState(traceWrapper.getBlockHitResult().getBlockPos()).is(Blocks.AIR)) {
            JadeUtil.tooltipRenderer = null;
            return;
        }

        LocalPlayer localPlayer = minecraft.player;
        if (localPlayer == null) {
            JadeUtil.tooltipRenderer = null;
            return;
        }

        Tooltip tooltip = new Tooltip();
        BlockState blockState = worldSchematic.getBlockState(traceWrapper.getBlockHitResult().getBlockPos());
        BlockEntity blockEntity = worldSchematic.getBlockEntity(traceWrapper.getBlockHitResult().getBlockPos());
        Accessor<?> accessor = WailaClientRegistration.INSTANCE
                .blockAccessor()
                .blockState(blockState)
                .blockEntity(blockEntity)
                .level(worldSchematic)
                .player(localPlayer)
                .serverData(ObjectDataCenter.getServerData())
                .serverConnected(ObjectDataCenter.serverConnected)
                .hit(traceWrapper.getBlockHitResult())
                .fakeBlock(DatapackBlockManager.getFakeBlock(worldSchematic, traceWrapper.getBlockHitResult().getBlockPos()))
                .build();

        Accessor<?> originalAccessor = accessor;

        //#if MC >= 11903
        for (JadeRayTraceCallback callback : WailaClientRegistration.INSTANCE.rayTraceCallback.callbacks()) {
        //#else
        //$$ for (JadeRayTraceCallback callback : WailaClientRegistration.INSTANCE.rayTraceCallbacks) {
        //#endif
            accessor = callback.onRayTrace(traceWrapper.getBlockHitResult(), accessor, originalAccessor);
        }

        ObjectDataCenter.set(accessor);

        if (accessor == null || accessor.getHitResult() == null) {
            JadeUtil.tooltipRenderer = null;
            return;
        }

        if (!accessor.shouldDisplay()) {
            JadeUtil.tooltipRenderer = null;
            return;
        }

        //#if MC < 11900
        //$$ boolean showDetails = localPlayer.isCrouching();
        //#endif
        if (accessor.isServerConnected()) {
            boolean request = accessor.shouldRequestData();
            if (ObjectDataCenter.isTimeElapsed(ObjectDataCenter.rateLimiter)) {
                ObjectDataCenter.resetTimer();
                if (request) {
                    //#if MC > 11802
                    accessor._requestData();
                    //#else
                    //$$ accessor._requestData(showDetails);
                    //#endif
                }
            }
            if (request && ObjectDataCenter.getServerData() == null) {
                JadeUtil.tooltipRenderer = null;
                return;
            }
        }

        //#if MC > 11802
        if (config.getDisplayMode() == IWailaConfig.DisplayMode.LITE && !ClientPlatformProxy.isShowDetailsPressed()) {
        //#else
        //$$ if (config.getDisplayMode() == IWailaConfig.DisplayMode.LITE && !showDetails) {
        //#endif
            Tooltip dummyTooltip = new Tooltip();
            //#if MC >= 11903
            accessor._gatherComponents($ -> Math.abs(WailaCommonRegistration.INSTANCE.priorities.byValue($)) > 5000 ? tooltip : dummyTooltip);
            //#else
            //$$ accessor._gatherComponents($ -> Math.abs(WailaCommonRegistration.INSTANCE.priorities.get($)) > 5000 ? tooltip : dummyTooltip);
            //#endif
            if (!dummyTooltip.isEmpty()) {
                tooltip.sneakyDetails = true;
            }
        } else {
            accessor._gatherComponents($ -> tooltip);
        }

        //#if MC >= 11903
        for (JadeTooltipCollectedCallback callback : WailaClientRegistration.INSTANCE.tooltipCollectedCallback.callbacks()) {
        //#else
        //$$ for (JadeTooltipCollectedCallback callback : WailaClientRegistration.INSTANCE.tooltipCollectedCallbacks) {
        //#endif
            callback.onTooltipCollected(tooltip, accessor);
        }
        JadeUtil.disableJadeRender = true;
        JadeUtil.tooltipRenderer = new TooltipRenderer(tooltip, true);
    }

    public static void render(PoseStack poseStack) {
        if (JadeUtil.tooltipRenderer == null ||
                !Jade.CONFIG.get().getGeneral().shouldDisplayTooltip() ||
                ((Jade.CONFIG.get().getGeneral().getDisplayMode() == IWailaConfig.DisplayMode.HOLD_KEY && !JadeClient.showOverlay.isDown()))) {
            return;
        }

        if (minecraft.screen != null) {
            if (!(minecraft.screen instanceof BaseOptionsScreen)) {
                return;
            } else {
                Rect2i position = JadeUtil.tooltipRenderer.getPosition();
                IWailaConfig.IConfigOverlay overlay = Jade.CONFIG.get().getOverlay();
                Window window = minecraft.getWindow();
                double x = minecraft.mouseHandler.xpos() * window.getGuiScaledWidth() / window.getScreenWidth();
                double y = minecraft.mouseHandler.ypos() * window.getGuiScaledHeight() / window.getScreenHeight();
                x += position.getWidth() * overlay.tryFlip(overlay.getAnchorX());
                y += position.getHeight() * overlay.getAnchorY();
                if (position.contains((int) x, (int) y)) {
                    return;
                }
            }
        }

        if (((PlayerTabOverlayAccessor) minecraft.gui.getTabList()).getVisible() ||
                minecraft.getOverlay() != null || minecraft.options.hideGui ||
                (minecraft.options.renderDebug && Jade.CONFIG.get().getGeneral().shouldHideFromDebug())) {
            return;
        }

        OverlayRenderer.ticks += minecraft.getDeltaFrameTime();
        OverlayRenderer.renderOverlay(JadeUtil.tooltipRenderer, poseStack);
    }
    //#endif
}
