package top.hendrixshen.tweakmyclient.mixin.patch.litematicaSchematicWailaCompat.wthit;

//#if MC >= 11600 && MC < 11700
//$$ import mcp.mobius.waila.overlay.TickHandler;
//$$ import org.spongepowered.asm.mixin.injection.At;
//$$ import org.spongepowered.asm.mixin.injection.Inject;
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//$$ import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
//$$ import top.hendrixshen.magiclib.dependency.annotation.Dependency;
//$$ import top.hendrixshen.tweakmyclient.config.Configs;
//$$ import top.hendrixshen.tweakmyclient.util.wthit.WthitUtil_1_16;
//#else
import net.minecraft.client.Minecraft;
//#endif
import org.spongepowered.asm.mixin.Mixin;

//#if MC >= 11600 && MC < 11700
//$$ @Dependencies(
//$$         and = {
//$$                 @Dependency("wthit"),
//$$                 @Dependency("litematica")
//$$         }
//$$ )
//$$ @Mixin(value = TickHandler.class,remap = false)
//#else
@Mixin(Minecraft.class)
//#endif
public class MixinTickHandler {
    //#if MC >= 11600 && MC < 11700
    //$$ @Inject(
    //$$         method = "tickClient",
    //$$         at = @At(
    //$$                 value = "HEAD"
    //$$         ),
    //$$         cancellable = true
    //$$ )
    //$$ private static void onTick(CallbackInfo ci) {
    //$$     if(Configs.litematicaSchematicWailaCompat && WthitUtil_1_16.shouldDisableWthitRender()) {
    //$$         ci.cancel();
    //$$     }
    //$$ }
    //#endif
}
