package top.hendrixshen.tweakmyclient.util.wthit;

//#if MC > 11502 && MC < 11700
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
//$$ import top.hendrixshen.magiclib.compat.minecraft.api.network.chat.ComponentCompatApi;
//$$ import top.hendrixshen.magiclib.language.api.I18n;
//$$ import top.hendrixshen.tweakmyclient.TweakMyClient;
//$$
//$$ import java.lang.reflect.Field;
//$$ import java.util.Objects;
//#endif

public class WthitUtil_1_16 {
    //#if MC > 11502 && MC < 11700
    //$$ private static final List<Component> TOOLTIP = new TaggableList<>(TaggedText::new);
    //$$ private static final Component SNEAK_DETAIL = ComponentCompatApi.literal(I18n.get("tooltip.waila.sneak_for_details")).withStyle(ChatFormatting.ITALIC);
    //$$ private static final Minecraft minecraft = TweakMyClient.getMinecraftClient();
    //$$ private static Field shouldRender;
    //$$ private static boolean disableWthitRender = false;
    //$$
    //$$ static {
    //$$     try {
    //$$         shouldRender = Class.forName("mcp.mobius.waila.overlay.Tooltip").getDeclaredField("shouldRender");
    //$$         shouldRender.setAccessible(true);
    //$$     } catch (NoSuchFieldException | ClassNotFoundException ignore) {
    //$$     }
    //$$ }
    //$$
    //$$ private static void setShouldRender(boolean shouldRender) {
    //$$     try {
    //$$         WthitUtil_1_16.shouldRender.set(null, shouldRender);
    //$$     } catch (IllegalAccessException ignore) {
    //$$     }
    //$$ }
    //$$
    //$$ public static boolean shouldDisableWthitRender() {
    //$$     return WthitUtil_1_16.disableWthitRender;
    //$$ }
    //$$
    //$$ public static void tick() {
    //$$     WthitUtil_1_16.disableWthitRender = false;
    //$$     WthitUtil_1_16.setShouldRender(false);
    //$$
    //$$     if (WthitUtil_1_16.shouldRender == null || minecraft.level == null || minecraft.cameraEntity == null) {
    //$$         return;
    //$$     }
    //$$
    //$$     RayTraceUtils.RayTraceWrapper traceWrapper = RayTraceUtils.getGenericTrace(minecraft.level,
    //$$             minecraft.cameraEntity, 10.0, true, true, true);
    //$$     Level worldSchematic = SchematicWorldHandler.getSchematicWorld();
    //$$
    //$$     if (traceWrapper == null || traceWrapper.getBlockHitResult() == null || worldSchematic == null ||
    //$$             worldSchematic.getBlockState(traceWrapper.getBlockHitResult().getBlockPos()).is(Blocks.AIR)) {
    //$$         return;
    //$$     }
    //$$
    //$$     LocalPlayer localPlayer = minecraft.player;
    //$$
    //$$     if (localPlayer == null) {
    //$$         return;
    //$$     }
    //$$
    //$$     WthitUtil_1_16.disableWthitRender = true;
    //$$     DataAccessor accessor = DataAccessor.INSTANCE;
    //$$     accessor.set(worldSchematic, localPlayer, traceWrapper.getBlockHitResult(),
    //$$             minecraft.cameraEntity, minecraft.getFrameTime());
    //$$     Tooltip.start();
    //$$     Block block = accessor.getBlock();
    //$$
    //$$     if (!PluginConfig.INSTANCE.get(WailaConstants.CONFIG_SHOW_BLOCK) ||
    //$$             block instanceof LiquidBlock && !PluginConfig.INSTANCE.get(WailaConstants.CONFIG_SHOW_FLUID) ||
    //$$             Waila.blockBlacklist.contains(block)) {
    //$$         return;
    //$$     }
    //$$
    //$$     BlockState state = worldSchematic.getBlockState(traceWrapper.getBlockHitResult().getBlockPos());
    //$$     accessor.setState(state);
    //$$     WthitUtil_1_16.TOOLTIP.clear();
    //$$     ComponentProvider.gatherBlock(accessor, WthitUtil_1_16.TOOLTIP, TooltipPosition.HEAD);
    //$$     Tooltip.addLines(WthitUtil_1_16.TOOLTIP);
    //$$     WthitUtil_1_16.TOOLTIP.clear();
    //$$     ComponentProvider.gatherBlock(accessor, WthitUtil_1_16.TOOLTIP, TooltipPosition.BODY);
    //$$
    //$$     if (Waila.CONFIG.get().getGeneral().shouldShiftForDetails() && !TOOLTIP.isEmpty() &&
    //$$             !minecraft.player.isShiftKeyDown()) {
    //$$         Tooltip.addLine(WthitUtil_1_16.SNEAK_DETAIL);
    //$$     } else {
    //$$         Tooltip.addLines(WthitUtil_1_16.TOOLTIP);
    //$$     }
    //$$
    //$$     WthitUtil_1_16.TOOLTIP.clear();
    //$$     ComponentProvider.gatherBlock(accessor, WthitUtil_1_16.TOOLTIP, TooltipPosition.TAIL);
    //$$     Tooltip.addLines(WthitUtil_1_16.TOOLTIP);
    //$$
    //$$     if (PluginConfig.INSTANCE.get(WailaConstants.CONFIG_SHOW_ITEM)) {
    //$$         Tooltip.setStack(new ItemStack(state.getBlock()));
    //$$     }
    //$$
    //$$     Tooltip.finish();
    //$$     WthitUtil_1_16.setShouldRender(true);
    //$$ }
    //#endif
}
