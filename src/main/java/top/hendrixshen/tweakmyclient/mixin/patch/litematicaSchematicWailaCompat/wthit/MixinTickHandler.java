package top.hendrixshen.tweakmyclient.mixin.patch.litematicaSchematicWailaCompat.wthit;

import org.spongepowered.asm.mixin.Mixin;
import top.hendrixshen.magiclib.compat.preprocess.api.DummyClass;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;

//#if MC > 11502 && MC < 11700
//$$ import mcp.mobius.waila.overlay.TickHandler;
//$$ import org.spongepowered.asm.mixin.injection.At;
//$$ import org.spongepowered.asm.mixin.injection.Inject;
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//$$ import top.hendrixshen.tweakmyclient.config.Configs;
//$$ import top.hendrixshen.tweakmyclient.util.wthit.WthitUtil_1_16;
//#endif

@Dependencies(
        and = {
                @Dependency("wthit"),
                @Dependency("litematica")
        }
)
//#if MC < 11600 || MC > 11605
@Mixin(DummyClass.class)
//#else
//$$ @Mixin(value = TickHandler.class,remap = false)
//#endif
public class MixinTickHandler {
    //#if MC > 11502 && MC < 11700
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
