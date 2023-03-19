package top.hendrixshen.tweakmyclient.mixin.patch.litematicaSchematicWailaCompat.hwyla;

import org.spongepowered.asm.mixin.Mixin;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;

//#if MC > 11502
import top.hendrixshen.magiclib.compat.preprocess.api.DummyClass;
//#else
//$$ import net.minecraft.world.item.ItemStack;
//$$ import mcp.mobius.waila.api.impl.DataAccessor;
//$$ import mcp.mobius.waila.overlay.OverlayRenderer;
//$$ import org.spongepowered.asm.mixin.injection.At;
//$$ import org.spongepowered.asm.mixin.injection.Inject;
//$$ import org.spongepowered.asm.mixin.injection.ModifyArg;
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//$$ import top.hendrixshen.tweakmyclient.util.HwylaUtil;
//#endif

@Dependencies(
        and = {
                @Dependency("waila"),
                @Dependency("litematica")
        }
)
//#if MC > 11502
@Mixin(DummyClass.class)
//#else
//$$ @Mixin(value = OverlayRenderer.class, remap = false)
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
