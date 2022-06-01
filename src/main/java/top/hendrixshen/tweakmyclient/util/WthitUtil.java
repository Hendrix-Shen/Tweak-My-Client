package top.hendrixshen.tweakmyclient.util;

//#if MC >= 11600
import fi.dy.masa.litematica.util.RayTraceUtils;
import fi.dy.masa.litematica.world.SchematicWorldHandler;
import mcp.mobius.waila.Waila;
//#if MC >= 11800
import mcp.mobius.waila.access.DataAccessor;
//#endif
//#if MC >= 11700
import mcp.mobius.waila.api.IBlacklistConfig;
import mcp.mobius.waila.api.IBlockComponentProvider;
//#endif
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaConstants;
//#if MC >= 11700
import mcp.mobius.waila.config.PluginConfig;
import mcp.mobius.waila.config.WailaConfig;
//#else
//$$ import mcp.mobius.waila.api.impl.config.PluginConfig;
//$$ import mcp.mobius.waila.api.impl.config.WailaConfig;
//#endif
//#if MC < 11800
//#if MC >= 11700
//$$ import mcp.mobius.waila.data.DataAccessor;
//#endif
//#endif
//#if MC >= 11700
import mcp.mobius.waila.hud.ComponentHandler;
//#endif
//#if MC >= 11800
import mcp.mobius.waila.hud.Line;
//#endif
//#if MC >= 11700
import mcp.mobius.waila.hud.Tooltip;
import mcp.mobius.waila.hud.TooltipHandler;
//#else
//$$ import mcp.mobius.waila.overlay.ComponentProvider;
//$$ import mcp.mobius.waila.overlay.DataAccessor;
//$$ import mcp.mobius.waila.overlay.Tooltip;
//$$ import mcp.mobius.waila.utils.TaggableList;
//$$ import mcp.mobius.waila.utils.TaggedText;
//#endif
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
//#if MC < 11800
//$$ import net.minecraft.network.chat.Component;
//#endif
import net.minecraft.network.chat.TranslatableComponent;
//#if MC < 11700
//$$ import net.minecraft.world.item.ItemStack;
//#endif
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import top.hendrixshen.tweakmyclient.TweakMyClient;

import java.lang.reflect.Field;
//#if MC < 11700
//$$ import java.util.List;
//#endif
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
    private static final Line SNEAK_DETAIL = (new Line(null)).with((new TranslatableComponent("tooltip.waila.sneak_for_details")).withStyle(ChatFormatting.ITALIC));
    //#else if MC >= 11600
    //$$ private static final Component SNEAK_DETAIL = (new TranslatableComponent("tooltip.waila.sneak_for_details")).withStyle(ChatFormatting.ITALIC);
    //#endif
    private static final Minecraft minecraft = TweakMyClient.getMinecraftClient();
    private static Field shouldRender;

    private static boolean disableWthitRender = false;

    static {
        try {
            //#if MC >= 11700
            shouldRender = Class.forName("mcp.mobius.waila.hud.TooltipHandler").getDeclaredField("shouldRender");
            //#else
            //$$ shouldRender = Class.forName("mcp.mobius.waila.overlay.Tooltip").getDeclaredField("shouldRender");
            //#endif
            shouldRender.setAccessible(true);
        } catch (NoSuchFieldException | ClassNotFoundException ignore) {
        }
    }

    private static void setShouldRender(boolean shouldRender) {
        try {
            WthitUtil.shouldRender.set(null, shouldRender);
        } catch (IllegalAccessException ignore) {
        }
    }

    public static boolean shouldDisableWthitRender() {
        return WthitUtil.disableWthitRender;
    }

    public static void tick() {
        // Check reflect.
        if (shouldRender != null) {
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
                        //#if MC >= 11700
                        TooltipHandler.beginBuild();
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
                        TooltipHandler.add(WthitUtil.TOOLTIP);
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
                            //#if MC >= 11700
                            TooltipHandler.add(WthitUtil.SNEAK_DETAIL);
                            //#else
                            //$$ Tooltip.addLine(WthitUtil.SNEAK_DETAIL);
                            //#endif
                        } else {
                            //#if MC >= 11700
                            TooltipHandler.add(WthitUtil.TOOLTIP);
                            //#else
                            //$$ Tooltip.addLines(WthitUtil.TOOLTIP);
                            //#endif
                        }

                        WthitUtil.TOOLTIP.clear();
                        //#if MC >= 11700
                        ComponentHandler.gatherBlock(accessor, WthitUtil.TOOLTIP, TooltipPosition.TAIL);
                        TooltipHandler.add(WthitUtil.TOOLTIP);
                        //#else
                        //$$ ComponentProvider.gatherBlock(accessor, WthitUtil.TOOLTIP, TooltipPosition.TAIL);
                        //$$ Tooltip.addLines(WthitUtil.TOOLTIP);
                        //#endif

                        WthitUtil.setShouldRender(true);

                        //#if MC >= 11800
                        if (PluginConfig.INSTANCE.getBoolean(WailaConstants.CONFIG_SHOW_ICON)) {
                            TooltipHandler.setIcon(ComponentHandler.getIcon(Objects.requireNonNull(traceWrapper.getBlockHitResult())));
                        //#elseif MC >= 11700
                        //$$ if (PluginConfig.INSTANCE.getBoolean(WailaConstants.CONFIG_SHOW_ITEM)) {
                        //$$     TooltipHandler.setStack(ComponentHandler.getDisplayItem(Objects.requireNonNull(traceWrapper.getBlockHitResult())));
                        //#else
                        //$$ if (PluginConfig.INSTANCE.get(WailaConstants.CONFIG_SHOW_ITEM)) {
                        //$$     Tooltip.setStack(new ItemStack(state.getBlock()));
                        //#endif
                        }
                        //#if MC >= 11700
                        TooltipHandler.endBuild();
                        //#else
                        //$$ Tooltip.finish();
                        //#endif
                        WthitUtil.setShouldRender(true);
                    }
                } else {
                    WthitUtil.disableWthitRender = false;
                    WthitUtil.setShouldRender(false);
                }
            }
        }
    }
    //#endif
}
