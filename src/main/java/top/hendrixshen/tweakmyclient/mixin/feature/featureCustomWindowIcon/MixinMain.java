package top.hendrixshen.tweakmyclient.mixin.feature.featureCustomWindowIcon;

//#if MC < 11500
//$$ import net.minecraft.client.Minecraft;
//#else
import net.minecraft.client.main.Main;
//#endif
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.util.CustomWindowUtil;

//#if MC >= 11500
@Mixin(Main.class)
//#else
//$$ @Mixin(Minecraft.class)
//#endif
public class MixinMain {
    @Inject(
            //#if MC >= 11900 || MC < 11500
            method = "run",
            //#else
            //$$ method = "main",
            //#endif
            at = @At(
                    value = "INVOKE",
            //#if MC >= 11500
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;finishInitialization()V"
            ),
            remap = false
            //#else
            //$$         target = "Lnet/minecraft/client/Minecraft;init()V",
            //$$         shift = At.Shift.AFTER
            //$$ )
            //#endif
    )
    //#if MC >= 11900
    private static void finishInitializationRenderSystem(String[] strings, boolean bl, CallbackInfo ci) {
    //#elseif MC >= 11500
    //$$ private static void finishInitializationRenderSystem(String[] strings, CallbackInfo ci) {
        CustomWindowUtil.updateIcon(TweakMyClient.getMinecraftClient().getWindow());
    //#else
    //$$ private void afterInit(CallbackInfo ci) {
    //$$     CustomWindowUtil.updateIcon(TweakMyClient.getMinecraftClient().window);
    //#endif
    }
}
