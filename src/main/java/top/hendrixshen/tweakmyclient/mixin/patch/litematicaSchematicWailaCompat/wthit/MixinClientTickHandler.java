package top.hendrixshen.tweakmyclient.mixin.patch.litematicaSchematicWailaCompat.wthit;

//#if MC >= 11800
import mcp.mobius.waila.gui.hud.TooltipHandler;
//#elseif MC >= 11700
//$$ import mcp.mobius.waila.hud.ClientTickHandler;
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
//#if MC >= 11900
import top.hendrixshen.tweakmyclient.util.wthit.WthitUtil_1_19;
//#elseif MC >= 11800
//$$ import top.hendrixshen.tweakmyclient.util.wthit.WthitUtil_1_18;
//#else
//$$ import top.hendrixshen.tweakmyclient.util.wthit.WthitUtil_1_17;
//#endif

@Dependencies(and = @Dependency(value = "wthit"))
//#endif
//#if MC >= 11800
@Mixin(value = TooltipHandler.class, remap = false)
//#elseif MC >= 11700
//$$ @Mixin(value = ClientTickHandler.class, remap = false)
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
        if(Configs.litematicaSchematicWailaCompat &&
                //#if MC >= 11900
                WthitUtil_1_19.shouldDisableWthitRender()) {
                //#elseif MC >= 11800
                //$$ WthitUtil_1_18.shouldDisableWthitRender()) {
                //#else
                //$$ WthitUtil_1_17.shouldDisableWthitRender()) {
                //#endif
            ci.cancel();
        }
    }
    //#endif
}
