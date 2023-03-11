package top.hendrixshen.tweakmyclient.util.wthit;

//#if MC > 11802
import mcp.mobius.waila.access.DataAccessor;
import mcp.mobius.waila.api.IBlacklistConfig;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.config.PluginConfig;
import mcp.mobius.waila.config.WailaConfig;
import mcp.mobius.waila.gui.hud.ComponentHandler;
import mcp.mobius.waila.gui.hud.Line;
import mcp.mobius.waila.gui.hud.Tooltip;
import mcp.mobius.waila.gui.hud.TooltipRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import fi.dy.masa.litematica.util.RayTraceUtils;
import fi.dy.masa.litematica.world.SchematicWorldHandler;
import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import top.hendrixshen.magiclib.compat.minecraft.network.chat.ComponentCompatApi;
import top.hendrixshen.magiclib.language.I18n;
import top.hendrixshen.tweakmyclient.TweakMyClient;

import java.lang.reflect.Field;
import java.util.Objects;
//#endif

public class WthitUtil_1_19 {
    //#if MC > 11802
    private static final Tooltip TOOLTIP = new Tooltip();
    private static final Line SNEAK_DETAIL = (new Line(null)).with(ComponentCompatApi.literal(I18n.get("tooltip.waila.sneak_for_details")).withStyle(ChatFormatting.ITALIC));
    private static final Minecraft minecraft = TweakMyClient.getMinecraftClient();
    private static Field STATE;
    private static boolean disableWthitRender = false;

    static {
        try {
            STATE = Class.forName("mcp.mobius.waila.gui.hud.TooltipHandler").getDeclaredField("STATE");
            STATE.setAccessible(true);
        } catch (NoSuchFieldException | ClassNotFoundException ignore) {
        }
    }

    @Contract(pure = true)
    private static TooltipRenderer.@Nullable State getState() {
        try {
            return (TooltipRenderer.State) WthitUtil_1_19.STATE.get(null);
        } catch (IllegalAccessException ignore) {
            return null;
        }
    }

    private static void setStateRender(boolean bl) {
        try {
            Class<?> clz = Class.forName("mcp.mobius.waila.gui.hud.TooltipHandler$ConfigTooltipRendererState");
            Field render = clz.getDeclaredField("render");
            render.setAccessible(true);
            render.set(WthitUtil_1_19.getState(), bl);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException ignore) {
        }
    }

    public static boolean shouldDisableWthitRender() {
        return WthitUtil_1_19.disableWthitRender;
    }

    public static void tick() {
        WthitUtil_1_19.disableWthitRender = false;
        WthitUtil_1_19.setStateRender(false);
        if (WthitUtil_1_19.STATE == null || minecraft.level == null || minecraft.cameraEntity == null) {
            return;
        }

        RayTraceUtils.RayTraceWrapper traceWrapper = RayTraceUtils.getGenericTrace(minecraft.level, minecraft.cameraEntity, 10.0, true, true, true);
        Level worldSchematic = SchematicWorldHandler.getSchematicWorld();
        if (traceWrapper == null || traceWrapper.getBlockHitResult() == null || worldSchematic == null ||
                worldSchematic.getBlockState(traceWrapper.getBlockHitResult().getBlockPos()).is(Blocks.AIR)) {
            return;
        }

        LocalPlayer localPlayer = minecraft.player;
        if (localPlayer == null) {
            return;
        }

        WthitUtil_1_19.disableWthitRender = true;
        DataAccessor accessor = DataAccessor.INSTANCE;
        WailaConfig.General config = Waila.CONFIG.get().getGeneral();
        accessor.set(worldSchematic, localPlayer, traceWrapper.getBlockHitResult(),
                minecraft.cameraEntity, minecraft.getFrameTime());
        TooltipRenderer.beginBuild(WthitUtil_1_19.getState());
        Block block = accessor.getBlock();

        if (!PluginConfig.CLIENT.getBoolean(WailaConstants.CONFIG_SHOW_BLOCK) ||
                block instanceof LiquidBlock && !PluginConfig.CLIENT.getBoolean(WailaConstants.CONFIG_SHOW_FLUID)) {
            return;
        }

        if (accessor.getBlockState().is(Waila.BLOCK_BLACKLIST_TAG) || IBlacklistConfig.get().contains(block)) {
            return;
        }

        BlockEntity blockEntity = accessor.getBlockEntity();
        if (blockEntity != null && IBlacklistConfig.get().contains(blockEntity)) {
            return;
        }

        BlockState state = worldSchematic.getBlockState(traceWrapper.getBlockHitResult().getBlockPos());
        if (state == IBlockComponentProvider.EMPTY_BLOCK_STATE) {
            return;
        }

        accessor.setState(state);
        WthitUtil_1_19.TOOLTIP.clear();
        ComponentHandler.gatherBlock(accessor, WthitUtil_1_19.TOOLTIP, TooltipPosition.HEAD);
        TooltipRenderer.add(WthitUtil_1_19.TOOLTIP);
        WthitUtil_1_19.TOOLTIP.clear();
        ComponentHandler.gatherBlock(accessor, WthitUtil_1_19.TOOLTIP, TooltipPosition.BODY);
        if (config.isShiftForDetails() && !WthitUtil_1_19.TOOLTIP.isEmpty() && !minecraft.player.isShiftKeyDown()) {
            TooltipRenderer.add(WthitUtil_1_19.SNEAK_DETAIL);
        } else {
            TooltipRenderer.add(WthitUtil_1_19.TOOLTIP);
        }

        WthitUtil_1_19.TOOLTIP.clear();
        ComponentHandler.gatherBlock(accessor, WthitUtil_1_19.TOOLTIP, TooltipPosition.TAIL);
        TooltipRenderer.add(WthitUtil_1_19.TOOLTIP);

        if (PluginConfig.CLIENT.getBoolean(WailaConstants.CONFIG_SHOW_ICON)) {
            TooltipRenderer.setIcon(ComponentHandler.getIcon(Objects.requireNonNull(traceWrapper.getBlockHitResult())));
        }
        WthitUtil_1_19.setStateRender(true);
        TooltipRenderer.endBuild();
    }
    //#endif
}
