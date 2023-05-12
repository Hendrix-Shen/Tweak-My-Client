package top.hendrixshen.tweakmyclient.mixin.feature.featureCustomWindowIcon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC > 11404
import net.minecraft.client.main.Main;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.IconUtil;
//#else
//$$ import net.minecraft.client.Minecraft;
//#endif

//#if MC > 11404
@Mixin(Main.class)
//#else
//$$ @Mixin(Minecraft.class)
//#endif
public class MixinMain {
    //#if MC > 11404
    @Inject(
            //#if MC > 11903 || MC < 11900
            method = "main",
            remap = false,
            //#else
            //$$ method = "run",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;finishInitialization()V",
                    remap = false
            )
    )
    //#if MC > 11903 || MC < 11900
    private static void finishInitializationRenderSystem(String[] strings, CallbackInfo ci) {
    //#else
    //$$ private static void finishInitializationRenderSystem(String[] strings, boolean bl, CallbackInfo ci) {
    //#endif
        if (Configs.featureCustomWindowIcon) {
            IconUtil.updateIcon();
        }
    }
    //#endif
}
