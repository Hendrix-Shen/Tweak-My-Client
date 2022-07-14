package top.hendrixshen.tweakmyclient.util;

//#if MC >= 11600
//#if MC >= 11900
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
//#elseif MC >= 11800
//$$ import mcp.mobius.waila.access.DataAccessor;
//$$ import mcp.mobius.waila.api.IBlacklistConfig;
//$$ import mcp.mobius.waila.api.IBlockComponentProvider;
//$$ import mcp.mobius.waila.config.PluginConfig;
//$$ import mcp.mobius.waila.config.WailaConfig;
//$$ import mcp.mobius.waila.hud.ComponentHandler;
//$$ import mcp.mobius.waila.hud.Line;
//$$ import mcp.mobius.waila.hud.Tooltip;
//$$ import mcp.mobius.waila.hud.TooltipHandler;
//$$ import net.minecraft.world.level.block.entity.BlockEntity;
//#elseif MC >= 11700
//$$ import mcp.mobius.waila.data.DataAccessor;
//$$ import mcp.mobius.waila.api.IBlacklistConfig;
//$$ import mcp.mobius.waila.api.IBlockComponentProvider;
//$$ import mcp.mobius.waila.config.PluginConfig;
//$$ import mcp.mobius.waila.config.WailaConfig;
//$$ import mcp.mobius.waila.hud.ComponentHandler;
//$$ import mcp.mobius.waila.hud.Tooltip;
//$$ import mcp.mobius.waila.hud.TooltipHandler;
//$$ import net.minecraft.network.chat.Component;
//$$ import net.minecraft.world.level.block.entity.BlockEntity;
//#elseif MC >= 11600
//$$ import mcp.mobius.waila.api.impl.config.PluginConfig;
//$$ import mcp.mobius.waila.api.impl.config.WailaConfig;
//$$ import mcp.mobius.waila.overlay.ComponentProvider;
//$$ import mcp.mobius.waila.overlay.DataAccessor;
//$$ import mcp.mobius.waila.overlay.Tooltip;
//$$ import mcp.mobius.waila.utils.TaggableList;
//$$ import mcp.mobius.waila.utils.TaggedText;
//$$ import net.minecraft.world.item.ItemStack;
//$$ import net.minecraft.network.chat.Component;
//$$ import java.util.List;
//#endif
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
import top.hendrixshen.magiclib.compat.minecraft.network.chat.ComponentCompatApi;
import top.hendrixshen.magiclib.language.I18n;
import top.hendrixshen.tweakmyclient.TweakMyClient;

import java.lang.reflect.Field;
import java.util.Objects;
//#endif

public class WthitUtil {
    //#if MC >= 11600
    //#if MC >= 11700
    private static final Tooltip TOOLTIP = new Tooltip();
    //#else
    //$$ private static final List<Component> TOOLTIP = new TaggableList<>(TaggedText::new);
    //#endif
    //#if MC >= 11800
    private static final Line SNEAK_DETAIL = (new Line(null)).with((ComponentCompatApi.literal(I18n.get("tooltip.waila.sneak_for_details")).withStyle(ChatFormatting.ITALIC)));
    //#else if MC >= 11600
    //$$ private static final Component SNEAK_DETAIL = ComponentCompatApi.literal(I18n.get("tooltip.waila.sneak_for_details")).withStyle(ChatFormatting.ITALIC);
    //#endif
    private static final Minecraft minecraft = TweakMyClient.getMinecraftClient();
    //#if MC >= 11900
    private static Field STATE;
    //#else
    //$$ private static Field shouldRender;
    //#endif

    private static boolean disableWthitRender = false;

    static {
        try {
            //#if MC >= 11900
            STATE = Class.forName("mcp.mobius.waila.gui.hud.TooltipHandler").getDeclaredField("STATE");
            STATE.setAccessible(true);
            //#elseif MC >= 11700
            //$$ shouldRender = Class.forName("mcp.mobius.waila.hud.TooltipHandler").getDeclaredField("shouldRender");
            //$$ shouldRender.setAccessible(true);
            //#else
            //$$ shouldRender = Class.forName("mcp.mobius.waila.overlay.Tooltip").getDeclaredField("shouldRender");
            //$$ shouldRender.setAccessible(true);
            //#endif

        } catch (NoSuchFieldException | ClassNotFoundException ignore) {
        }
    }

    //#if MC >= 11900
    private static TooltipRenderer.State getState() {
        try {
            return (TooltipRenderer.State) WthitUtil.STATE.get(null);
        } catch (IllegalAccessException ignore) {
            return null;
        }
    }

    private static void setStateRender(boolean bl) {
        try {
            Class<?> clz = Class.forName("mcp.mobius.waila.gui.hud.TooltipHandler$ConfigTooltipRendererState");
            Field render = clz.getDeclaredField("render");
            render.setAccessible(true);
            render.set(WthitUtil.getState(), bl);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException ignore) {
        }
    }
    //#else
    //$$ private static void setShouldRender(boolean shouldRender) {
    //$$     try {
    //$$         WthitUtil.shouldRender.set(null, shouldRender);
    //$$     } catch (IllegalAccessException ignore) {
    //$$     }
    //$$ }
    //#endif

    public static boolean shouldDisableWthitRender() {
        return WthitUtil.disableWthitRender;
    }

    public static void tick() {
        // Check reflect.
        //#if MC >= 11900
        if (WthitUtil.STATE != null) {
        //#else
        //$$ if (shouldRender != null) {
        //#endif
            if (minecraft.level != null && minecraft.cameraEntity != null) {
                RayTraceUtils.RayTraceWrapper traceWrapper = RayTraceUtils.getGenericTrace(minecraft.level, minecraft.cameraEntity, 10.0, true, true, true);
                Level worldSchematic = SchematicWorldHandler.getSchematicWorld();
                if (traceWrapper != null && traceWrapper.getBlockHitResult() != null && !Objects.requireNonNull(worldSchematic).getBlockState(traceWrapper.getBlockHitResult().getBlockPos()).is(Blocks.AIR)) {
                    LocalPlayer localPlayer = minecraft.player;
                    if (localPlayer != null) {
                        WthitUtil.disableWthitRender = true;
                        DataAccessor accessor = DataAccessor.INSTANCE;
                        //#if MC >= 11800
                        WailaConfig.General config = Waila.CONFIG.get().getGeneral();
                        //#elseif MC >= 11700
                        //$$ WailaConfig config = Waila.config.get();
                        //#else
                        //$$ WailaConfig config = Waila.CONFIG.get();
                        //#endif
                        accessor.set(minecraft.level, localPlayer, traceWrapper.getBlockHitResult(), minecraft.cameraEntity, minecraft.getFrameTime());
                        //#if MC >= 11900
                        TooltipRenderer.beginBuild(WthitUtil.getState());
                        //#elseif MC >= 11700
                        //$$ TooltipHandler.beginBuild();
                        //#else
                        //$$ Tooltip.start();
                        //#endif
                        Block block = accessor.getBlock();

                        if (block instanceof LiquidBlock) {
                            //#if MC >= 11700
                            if (!PluginConfig.INSTANCE.getBoolean(WailaConstants.CONFIG_SHOW_FLUID)) {
                            //#else
                            //$$ if (!PluginConfig.INSTANCE.get(WailaConstants.CONFIG_SHOW_FLUID)) {
                            //#endif
                                return;
                            }
                        //#if MC >= 11700
                        } else if (!PluginConfig.INSTANCE.getBoolean(WailaConstants.CONFIG_SHOW_BLOCK)) {
                        //#else
                        //$$ } else if (!PluginConfig.INSTANCE.get(WailaConstants.CONFIG_SHOW_BLOCK)) {
                        //#endif
                            return;
                        }

                        //#if MC >= 11800
                        if (accessor.getBlockState().is(Waila.BLOCK_BLACKLIST_TAG) || IBlacklistConfig.get().contains(block)) {
                        //#elseif MC >= 11700
                        //$$ if (Waila.blockBlacklist.contains(block) || IBlacklistConfig.get().contains(block)) {
                        //#else
                        //$$ if (Waila.blockBlacklist.contains(block)) {
                        //#endif
                            return;
                        }

                        //#if MC >= 11700
                        BlockEntity blockEntity = accessor.getBlockEntity();
                        if (blockEntity != null && IBlacklistConfig.get().contains(blockEntity)) {
                            return;
                        }
                        //#endif

                        BlockState state = worldSchematic.getBlockState(traceWrapper.getBlockHitResult().getBlockPos());
                        //#if MC >= 11700
                        if (state == IBlockComponentProvider.EMPTY_BLOCK_STATE) {
                            return;
                        }
                        //#endif

                        accessor.setState(state);
                        WthitUtil.TOOLTIP.clear();
                        //#if MC >= 11700
                        ComponentHandler.gatherBlock(accessor, WthitUtil.TOOLTIP, TooltipPosition.HEAD);
                        //#if MC >= 11900
                        TooltipRenderer.add(WthitUtil.TOOLTIP);
                        //#else
                        //$$ TooltipHandler.add(WthitUtil.TOOLTIP);
                        //#endif
                        //#else
                        //$$ ComponentProvider.gatherBlock(accessor, WthitUtil.TOOLTIP, TooltipPosition.HEAD);
                        //$$ Tooltip.addLines(WthitUtil.TOOLTIP);
                        //#endif
                        WthitUtil.TOOLTIP.clear();
                        //#if MC >= 11700
                        ComponentHandler.gatherBlock(accessor, WthitUtil.TOOLTIP, TooltipPosition.BODY);
                        //#else
                        //$$ ComponentProvider.gatherBlock(accessor, WthitUtil.TOOLTIP, TooltipPosition.BODY);
                        //#endif
                        //#if MC >= 11800
                        if (config.isShiftForDetails() && !WthitUtil.TOOLTIP.isEmpty() && !minecraft.player.isShiftKeyDown()) {
                        //#elseif MC >= 11700
                        //$$ if (Waila.config.get().getGeneral().isShiftForDetails() && !TOOLTIP.isEmpty() && !minecraft.player.isShiftKeyDown()) {
                        //#else
                        //$$ if (Waila.CONFIG.get().getGeneral().shouldShiftForDetails() && !TOOLTIP.isEmpty() && !minecraft.player.isShiftKeyDown()) {
                        //#endif
                            //#if MC >= 11900
                            TooltipRenderer.add(WthitUtil.SNEAK_DETAIL);
                            //#elseif MC >= 11700
                            //$$ TooltipHandler.add(WthitUtil.SNEAK_DETAIL);
                            //#else
                            //$$ Tooltip.addLine(WthitUtil.SNEAK_DETAIL);
                            //#endif
                        } else {
                            //#if MC >= 11900
                            TooltipRenderer.add(WthitUtil.TOOLTIP);
                            //#elseif MC >= 11700
                            //$$ TooltipHandler.add(WthitUtil.TOOLTIP);
                            //#else
                            //$$ Tooltip.addLines(WthitUtil.TOOLTIP);
                            //#endif
                        }

                        WthitUtil.TOOLTIP.clear();
                        //#if MC >= 11700
                        ComponentHandler.gatherBlock(accessor, WthitUtil.TOOLTIP, TooltipPosition.TAIL);
                        //#if MC >= 11900
                        TooltipRenderer.add(WthitUtil.TOOLTIP);
                        //#else
                        //$$ TooltipHandler.add(WthitUtil.TOOLTIP);
                        //#endif
                        //#else
                        //$$ ComponentProvider.gatherBlock(accessor, WthitUtil.TOOLTIP, TooltipPosition.TAIL);
                        //$$ Tooltip.addLines(WthitUtil.TOOLTIP);
                        //#endif

                        //#if MC >= 11900
                        WthitUtil.setStateRender(true);
                        //#else
                        //$$ WthitUtil.setShouldRender(true);
                        //#endif

                        //#if MC >= 11800
                        if (PluginConfig.INSTANCE.getBoolean(WailaConstants.CONFIG_SHOW_ICON)) {
                        //#if MC >= 11900
                            TooltipRenderer.setIcon(ComponentHandler.getIcon(Objects.requireNonNull(traceWrapper.getBlockHitResult())));
                        //#else
                        //$$ TooltipHandler.setIcon(ComponentHandler.getIcon(Objects.requireNonNull(traceWrapper.getBlockHitResult())));
                        //#endif
                        //#elseif MC >= 11700
                        //$$ if (PluginConfig.INSTANCE.getBoolean(WailaConstants.CONFIG_SHOW_ITEM)) {
                        //$$     TooltipHandler.setStack(ComponentHandler.getDisplayItem(Objects.requireNonNull(traceWrapper.getBlockHitResult())));
                        //#else
                        //$$ if (PluginConfig.INSTANCE.get(WailaConstants.CONFIG_SHOW_ITEM)) {
                        //$$     Tooltip.setStack(new ItemStack(state.getBlock()));
                        //#endif
                        }
                        //#if MC >= 11900
                        WthitUtil.setStateRender(true);
                        TooltipRenderer.endBuild();
                        //#elseif MC >= 11700
                        //$$ WthitUtil.setShouldRender(true);
                        //$$ TooltipHandler.endBuild();
                        //#else
                        //$$ WthitUtil.setShouldRender(true);
                        //$$ Tooltip.finish();
                        //#endif
                    }
                } else {
                    WthitUtil.disableWthitRender = false;
                    //#if MC >= 11900
                    WthitUtil.setStateRender(false);
                    //#else
                    //$$ WthitUtil.setShouldRender(false);
                    //#endif
                }
            }
        }
    }
    //#endif
}
