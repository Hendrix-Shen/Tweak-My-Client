package top.hendrixshen.tweakmyclient.util.wthit;

//#if MC >= 11700 && MC < 11800
//$$ import mcp.mobius.waila.data.DataAccessor;
//$$ import mcp.mobius.waila.api.IBlacklistConfig;
//$$ import mcp.mobius.waila.api.IBlockComponentProvider;
//$$ import mcp.mobius.waila.config.PluginConfig;
//$$ import mcp.mobius.waila.hud.ComponentHandler;
//$$ import mcp.mobius.waila.hud.Tooltip;
//$$ import mcp.mobius.waila.hud.TooltipHandler;
//$$ import net.minecraft.network.chat.Component;
//$$ import net.minecraft.world.level.block.entity.BlockEntity;
//$$ import fi.dy.masa.litematica.util.RayTraceUtils;
//$$ import fi.dy.masa.litematica.world.SchematicWorldHandler;
//$$ import mcp.mobius.waila.Waila;
//$$ import mcp.mobius.waila.api.TooltipPosition;
//$$ import mcp.mobius.waila.api.WailaConstants;
//$$ import net.minecraft.ChatFormatting;
//$$ import net.minecraft.client.Minecraft;
//$$ import net.minecraft.client.player.LocalPlayer;
//$$ import net.minecraft.world.level.Level;
//$$ import net.minecraft.world.level.block.Block;
//$$ import net.minecraft.world.level.block.Blocks;
//$$ import net.minecraft.world.level.block.LiquidBlock;
//$$ import net.minecraft.world.level.block.state.BlockState;
//$$ import top.hendrixshen.magiclib.compat.minecraft.network.chat.ComponentCompatApi;
//$$ import top.hendrixshen.magiclib.language.I18n;
//$$ import top.hendrixshen.tweakmyclient.TweakMyClient;
//$$
//$$ import java.lang.reflect.Field;
//$$ import java.util.Objects;
//#endif

public class WthitUtil_1_17 {
    //#if MC >= 11700 && MC < 11800
    //$$ private static final Tooltip TOOLTIP = new Tooltip();
    //$$ private static final Component SNEAK_DETAIL = ComponentCompatApi.literal(I18n.get("tooltip.waila.sneak_for_details")).withStyle(ChatFormatting.ITALIC);
    //$$ private static final Minecraft minecraft = TweakMyClient.getMinecraftClient();
    //$$ private static Field shouldRender;
    //$$
    //$$ private static boolean disableWthitRender = false;
    //$$
    //$$ static {
    //$$     try {
    //$$         shouldRender = Class.forName("mcp.mobius.waila.hud.TooltipHandler").getDeclaredField("shouldRender");
    //$$         shouldRender.setAccessible(true);
    //$$     } catch (NoSuchFieldException | ClassNotFoundException ignore) {
    //$$     }
    //$$ }
    //$$
    //$$ private static void setShouldRender(boolean shouldRender) {
    //$$     try {
    //$$         WthitUtil_1_17.shouldRender.set(null, shouldRender);
    //$$     } catch (IllegalAccessException ignore) {
    //$$     }
    //$$ }
    //$$
    //$$ public static boolean shouldDisableWthitRender() {
    //$$     return WthitUtil_1_17.disableWthitRender;
    //$$ }
    //$$
    //$$ public static void tick() {
    //$$     WthitUtil_1_17.disableWthitRender = false;
    //$$     WthitUtil_1_17.setShouldRender(false);
    //$$     if (WthitUtil_1_17.shouldRender == null || minecraft.level == null || minecraft.cameraEntity == null) {
    //$$         return;
    //$$     }
    //$$
    //$$     RayTraceUtils.RayTraceWrapper traceWrapper = RayTraceUtils.getGenericTrace(minecraft.level,
    //$$             minecraft.cameraEntity, 10.0, true, true, true);
    //$$     Level worldSchematic = SchematicWorldHandler.getSchematicWorld();
    //$$     if (traceWrapper == null || traceWrapper.getBlockHitResult() == null || worldSchematic == null ||
    //$$             worldSchematic.getBlockState(traceWrapper.getBlockHitResult().getBlockPos()).is(Blocks.AIR)) {
    //$$         return;
    //$$     }
    //$$
    //$$     LocalPlayer localPlayer = minecraft.player;
    //$$     if (localPlayer == null) {
    //$$         return;
    //$$     }
    //$$
    //$$     WthitUtil_1_17.disableWthitRender = true;
    //$$     DataAccessor accessor = DataAccessor.INSTANCE;
    //$$     accessor.set(minecraft.level, localPlayer, traceWrapper.getBlockHitResult(),
    //$$             minecraft.cameraEntity, minecraft.getFrameTime());
    //$$     TooltipHandler.beginBuild();
    //$$     Block block = accessor.getBlock();
    //$$
    //$$     if (!PluginConfig.INSTANCE.getBoolean(WailaConstants.CONFIG_SHOW_BLOCK) ||
    //$$             block instanceof LiquidBlock && !PluginConfig.INSTANCE.getBoolean(WailaConstants.CONFIG_SHOW_FLUID)) {
    //$$         return;
    //$$     }
    //$$
    //$$     if (Waila.blockBlacklist.contains(block) || IBlacklistConfig.get().contains(block)) {
    //$$         return;
    //$$     }
    //$$
    //$$     BlockEntity blockEntity = accessor.getBlockEntity();
    //$$     if (blockEntity != null && IBlacklistConfig.get().contains(blockEntity)) {
    //$$         return;
    //$$     }
    //$$
    //$$     BlockState state = worldSchematic.getBlockState(traceWrapper.getBlockHitResult().getBlockPos());
    //$$     if (state == IBlockComponentProvider.EMPTY_BLOCK_STATE) {
    //$$         return;
    //$$     }
    //$$
    //$$     accessor.setState(state);
    //$$     WthitUtil_1_17.TOOLTIP.clear();
    //$$     ComponentHandler.gatherBlock(accessor, WthitUtil_1_17.TOOLTIP, TooltipPosition.HEAD);
    //$$     TooltipHandler.add(WthitUtil_1_17.TOOLTIP);
    //$$     WthitUtil_1_17.TOOLTIP.clear();
    //$$     ComponentHandler.gatherBlock(accessor, WthitUtil_1_17.TOOLTIP, TooltipPosition.BODY);
    //$$     if (Waila.config.get().getGeneral().isShiftForDetails() && !TOOLTIP.isEmpty() && !minecraft.player.isShiftKeyDown()) {
    //$$         TooltipHandler.add(WthitUtil_1_17.SNEAK_DETAIL);
    //$$     } else {
    //$$         TooltipHandler.add(WthitUtil_1_17.TOOLTIP);
    //$$     }
    //$$
    //$$     WthitUtil_1_17.TOOLTIP.clear();
    //$$     ComponentHandler.gatherBlock(accessor, WthitUtil_1_17.TOOLTIP, TooltipPosition.TAIL);
    //$$     TooltipHandler.add(WthitUtil_1_17.TOOLTIP);
    //$$
    //$$     if (PluginConfig.INSTANCE.getBoolean(WailaConstants.CONFIG_SHOW_ITEM)) {
    //$$         TooltipHandler.setStack(ComponentHandler.getDisplayItem(Objects.requireNonNull(traceWrapper.getBlockHitResult())));
    //$$     }
    //$$     WthitUtil_1_17.setShouldRender(true);
    //$$     TooltipHandler.endBuild();
    //$$ }
    //#endif
}
