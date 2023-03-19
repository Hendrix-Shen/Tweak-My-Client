package top.hendrixshen.tweakmyclient.util;

//#if MC < 11600
//$$ import net.minecraft.world.level.block.Block;
//$$ import net.minecraft.world.level.block.state.BlockState;
//$$ import fi.dy.masa.litematica.util.RayTraceUtils;
//$$ import fi.dy.masa.litematica.world.SchematicWorldHandler;
//$$ import mcp.mobius.waila.api.TooltipPosition;
//$$ import mcp.mobius.waila.api.impl.DataAccessor;
//$$ import mcp.mobius.waila.api.impl.MetaDataProvider;
//$$ import mcp.mobius.waila.api.impl.TaggableList;
//$$ import mcp.mobius.waila.api.impl.TaggedTextComponent;
//$$ import mcp.mobius.waila.overlay.Tooltip;
//$$ import mcp.mobius.waila.overlay.WailaTickHandler;
//$$ import net.minecraft.client.Minecraft;
//$$ import net.minecraft.client.player.LocalPlayer;
//$$ import net.minecraft.network.chat.Component;
//$$ import net.minecraft.world.entity.player.Player;
//$$ import net.minecraft.world.item.ItemStack;
//$$ import net.minecraft.world.level.Level;
//$$ import net.minecraft.world.level.block.Blocks;
//$$ import top.hendrixshen.tweakmyclient.TweakMyClient;
//$$
//$$ import java.lang.reflect.Field;
//$$ import java.lang.reflect.InvocationTargetException;
//$$ import java.lang.reflect.Method;
//$$ import java.util.List;
//#endif

public class HwylaUtil {
    //#if MC < 11600
    //$$ private static final Minecraft minecraft = TweakMyClient.getMinecraftClient();
    //$$
    //$$ private static final MetaDataProvider handler = new MetaDataProvider();
    //$$ public static Tooltip tooltip = null;
    //$$
    //$$ private static Method combinePositions;
    //$$ private static Field blockState;
    //$$ private static Field block;
    //$$
    //$$ private static boolean disableWthitRender = false;
    //$$
    //$$ static {
    //$$     try {
    //$$         combinePositions = Class.forName("mcp.mobius.waila.overlay.WailaTickHandler").getDeclaredMethod("combinePositions", Player.class, List.class, List.class, List.class, List.class);
    //$$         combinePositions.setAccessible(true);
    //$$         blockState = Class.forName("mcp.mobius.waila.api.impl.DataAccessor").getDeclaredField("state");
    //$$         blockState.setAccessible(true);
    //$$         block = Class.forName("mcp.mobius.waila.api.impl.DataAccessor").getDeclaredField("block");
    //$$         block.setAccessible(true);
    //$$     } catch (NoSuchMethodException | ClassNotFoundException | NoSuchFieldException ignore) {
    //$$         combinePositions = null;
    //$$     }
    //$$ }
    //$$
    //$$
    //$$ public static boolean shouldDisableWthitRender() {
    //$$     return HwylaUtil.disableWthitRender;
    //$$ }
    //$$
    //$$ public static void tick() {
    //$$     if (combinePositions != null) {
    //$$         if (minecraft.level != null && minecraft.cameraEntity != null) {
    //$$             RayTraceUtils.RayTraceWrapper traceWrapper = RayTraceUtils.getGenericTrace(minecraft.level, minecraft.cameraEntity, 10.0, true);
    //$$             Level worldSchematic = SchematicWorldHandler.getSchematicWorld();
    //$$
    //$$             if (traceWrapper != null && traceWrapper.getBlockHitResult() != null && worldSchematic != null && !worldSchematic.getBlockState(traceWrapper.getBlockHitResult().getBlockPos()).is(Blocks.AIR)) {
    //$$                 LocalPlayer localPlayer = minecraft.player;
    //$$
    //$$                 if (localPlayer != null) {
    //$$                     HwylaUtil.disableWthitRender = true;
    //$$                     List<Component> currentTip = new TaggableList<>(TaggedTextComponent::new);
    //$$                     List<Component> currentTipHead = new TaggableList<>(TaggedTextComponent::new);
    //$$                     List<Component> currentTipBody = new TaggableList<>(TaggedTextComponent::new);
    //$$                     List<Component> currentTipTail = new TaggableList<>(TaggedTextComponent::new);
    //$$
    //$$                     DataAccessor accessor = DataAccessor.INSTANCE;
    //$$                     accessor.set(minecraft.level, minecraft.player, traceWrapper.getBlockHitResult());
    //$$                     HwylaUtil.setBlockState(worldSchematic.getBlockState(traceWrapper.getBlockHitResult().getBlockPos()));
    //$$                     HwylaUtil.setBlock(worldSchematic.getBlockState(traceWrapper.getBlockHitResult().getBlockPos()).getBlock());
    //$$                     ItemStack targetStack = new ItemStack(worldSchematic.getBlockState(traceWrapper.getBlockHitResult().getBlockPos()).getBlock());
    //$$                     accessor.stack = targetStack;
    //$$                     // System.out.println(targetStack);
    //$$
    //$$                     HwylaUtil.handler.gatherBlockComponents(accessor, currentTipHead, TooltipPosition.HEAD);
    //$$                     HwylaUtil.handler.gatherBlockComponents(accessor, currentTipBody, TooltipPosition.BODY);
    //$$                     HwylaUtil.handler.gatherBlockComponents(accessor, currentTipTail, TooltipPosition.TAIL);
    //$$
    //$$                     HwylaUtil.combinePositions(minecraft.player, currentTip, currentTipHead, currentTipBody, currentTipTail);
    //$$                     HwylaUtil.tooltip = new Tooltip(currentTip, !targetStack.isEmpty());
    //$$                 }
    //$$             } else {
    //$$                 HwylaUtil.disableWthitRender = false;
    //$$             }
    //$$         }
    //$$     }
    //$$ }
    //$$
    //$$ private static void combinePositions(Player player, List<Component> currentTip, List<Component> currentTipHead, List<Component> currentTipBody, List<Component> currentTipTail) {
    //$$     try {
    //$$          HwylaUtil.combinePositions.invoke(WailaTickHandler.INSTANCE, player, currentTip, currentTipHead, currentTipBody, currentTipTail);
    //$$     } catch (IllegalAccessException | InvocationTargetException ignore) {
    //$$     }
    //$$ }
    //$$
    //$$ private static void setBlockState(BlockState blockState) {
    //$$     try {
    //$$         HwylaUtil.blockState.set(DataAccessor.INSTANCE, blockState);
    //$$     } catch (IllegalAccessException ignore) {
    //$$     }
    //$$ }
    //$$ private static void setBlock(Block block) {
    //$$     try {
    //$$         HwylaUtil.block.set(DataAccessor.INSTANCE, block);
    //$$     } catch (IllegalAccessException ignore) {
    //$$     }
    //$$ }
    //#endif
}
