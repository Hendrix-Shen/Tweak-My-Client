package top.hendrixshen.tweakmyclient.mixin.patch.litematicaSchematicWAILACompat;

//#if MC >= 11700
import mcp.mobius.waila.hud.ClientTickHandler;
//#else
//$$ import net.minecraft.client.Minecraft;
//#endif
import org.spongepowered.asm.mixin.Mixin;
//#if MC >= 11700
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.WthitUtil;

@Dependencies(and = @Dependency(value = "wthit"))
@Mixin(value = ClientTickHandler.class, remap = false)
//#else
//$$ @Mixin(Minecraft.class)
//#endif
public class MixinClientTickHandler {
    //#if MC >= 11700
    @Inject(
            method = "tick",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private static void onTick(CallbackInfo ci) {
        if(Configs.litematicaSchematicWAILACompat && WthitUtil.shouldDisableWthitRender()) {
            ci.cancel();
        }
    }
    //#endif
}