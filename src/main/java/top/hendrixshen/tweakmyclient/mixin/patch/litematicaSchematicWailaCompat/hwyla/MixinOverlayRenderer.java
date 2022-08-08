package top.hendrixshen.tweakmyclient.mixin.patch.litematicaSchematicWailaCompat.hwyla;

//#if MC < 11600
//$$ import mcp.mobius.waila.api.impl.DataAccessor;
//$$ import mcp.mobius.waila.overlay.OverlayRenderer;
//#else
import net.minecraft.client.Minecraft;
//#endif
//#if MC < 11600
//$$ import net.minecraft.world.item.ItemStack;
//#endif
import org.spongepowered.asm.mixin.Mixin;
//#if MC < 11600
//$$ import org.spongepowered.asm.mixin.injection.At;
//$$ import org.spongepowered.asm.mixin.injection.Inject;
//$$ import org.spongepowered.asm.mixin.injection.ModifyArg;
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//$$ import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
//$$ import top.hendrixshen.magiclib.dependency.annotation.Dependency;
//$$ import top.hendrixshen.tweakmyclient.util.HwylaUtil;
//$$
//$$ @Dependencies(and = @Dependency(value = "waila"))
//$$ @Mixin(value = OverlayRenderer.class, remap = false)
//#else
@Mixin(Minecraft.class)
//#endif
public class MixinOverlayRenderer {
    //#if MC < 11600
    //$$ @Inject(
    //$$         method = "renderOverlay()V",
    //$$         at = @At(
    //$$                 value = "HEAD"
    //$$         ),
    //$$         cancellable = true
    //$$ )
    //$$ private static void onRenderOverlay(CallbackInfo ci) {
    //$$     if (HwylaUtil.shouldDisableWthitRender()) {
    //$$         OverlayRenderer.renderOverlay(HwylaUtil.tooltip);
    //$$         ci.cancel();
    //$$     }
    //$$ }
    //$$
    //$$ @ModifyArg(
    //$$         method = "renderOverlay(Lmcp/mobius/waila/overlay/Tooltip;)V",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Lmcp/mobius/waila/overlay/DisplayUtil;renderStack(IILnet/minecraft/world/item/ItemStack;)V"
    //$$         ),
    //$$         index = 2
    //$$ )
    //$$ private static ItemStack onrenderOverlay(ItemStack stack) {
    //$$     return HwylaUtil.shouldDisableWthitRender() ? DataAccessor.INSTANCE.stack : stack;
    //$$ }
    //#endif
}
