package top.hendrixshen.tweakmyclient.mixin.patch.litematicaSchematicWAILACompat;

//#if MC >= 11600
//#if MC < 11700
//$$ import mcp.mobius.waila.overlay.TickHandler;
//#else
import net.minecraft.client.Minecraft;
//#endif
//#else
//$$ import net.minecraft.client.Minecraft;
//#endif
import org.spongepowered.asm.mixin.Mixin;
//#if MC >= 11600
//#if MC < 11700
//$$ import org.spongepowered.asm.mixin.injection.At;
//$$ import org.spongepowered.asm.mixin.injection.Inject;
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//$$ import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
//$$ import top.hendrixshen.magiclib.dependency.annotation.Dependency;
//$$ import top.hendrixshen.tweakmyclient.config.Configs;
//$$ import top.hendrixshen.tweakmyclient.util.WthitUtil;
//$$
//$$ @Dependencies(and = @Dependency(value = "wthit"))
//$$ @Mixin(value = TickHandler.class,remap = false)
//#else
@Mixin(Minecraft.class)
//#endif
//#else
//$$ @Mixin(Minecraft.class)
//#endif
public class MixinTickHandler {
    //#if MC >= 11600
    //#if MC < 11700
    //$$ @Inject(
    //$$         method = "tickClient",
    //$$         at = @At(
    //$$                 value = "HEAD"
    //$$         ),
    //$$         cancellable = true
    //$$ )
    //$$ private static void onTick(CallbackInfo ci) {
    //$$     if(Configs.litematicaSchematicWAILACompat && WthitUtil.shouldDisableWthitRender()) {
    //$$         ci.cancel();
    //$$     }
    //$$ }
    //#endif
    //#endif
}
