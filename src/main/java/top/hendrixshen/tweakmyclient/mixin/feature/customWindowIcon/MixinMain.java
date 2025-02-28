package top.hendrixshen.tweakmyclient.mixin.feature.customWindowIcon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC > 11404
import net.minecraft.client.main.Main;
import top.hendrixshen.tweakmyclient.game.Configs;
import top.hendrixshen.tweakmyclient.impl.feature.customWindowIcon.CustomIconHelper;
//#else
//$$ import net.minecraft.client.Minecraft;
//#endif

@Mixin(
        //#if MC > 11404
        Main.class
        //#else
        //$$ Minecraft.class
        //#endif
)
public abstract class MixinMain {
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
        if (Configs.customWindowIcon.getBooleanValue()) {
            CustomIconHelper.updateIcon();
        }
    }
    //#endif
}
