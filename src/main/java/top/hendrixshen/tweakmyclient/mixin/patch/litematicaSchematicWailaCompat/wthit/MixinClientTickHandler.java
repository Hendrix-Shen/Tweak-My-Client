package top.hendrixshen.tweakmyclient.mixin.patch.litematicaSchematicWailaCompat.wthit;

import org.spongepowered.asm.mixin.Mixin;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;

//#if MC > 11701
import mcp.mobius.waila.gui.hud.TooltipHandler;
//#elseif MC > 11605
//$$ import mcp.mobius.waila.hud.ClientTickHandler;
//#else
//$$ import top.hendrixshen.magiclib.compat.preprocess.api.DummyClass;
//#endif

//#if MC > 11605
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;
//#if MC > 11802
import top.hendrixshen.tweakmyclient.util.wthit.WthitUtil_1_19;
//#elseif MC > 11701
//$$ import top.hendrixshen.tweakmyclient.util.wthit.WthitUtil_1_18;
//#else
//$$ import top.hendrixshen.tweakmyclient.util.wthit.WthitUtil_1_17;
//#endif
//#endif

@Dependencies(
        and = {
                @Dependency("wthit"),
                @Dependency("litematica")
        }
)
//#if MC > 11701
@Mixin(value = TooltipHandler.class, remap = false)
//#elseif MC > 11605
//$$ @Mixin(value = ClientTickHandler.class, remap = false)
//#else
//$$ @Mixin(DummyClass.class)
//#endif
public class MixinClientTickHandler {
    //#if MC > 11605
    @Inject(
            method = "tick",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private static void onTick(CallbackInfo ci) {
        if (Configs.litematicaSchematicWailaCompat && WthitUtil_1_19.shouldDisableWthitRender()) {
            ci.cancel();
        }
    }
    //#endif
}
