package top.hendrixshen.tweakmyclient.mixin.feature.customWindowIcon;

import top.hendrixshen.tweakmyclient.game.Configs;
import top.hendrixshen.tweakmyclient.impl.feature.customWindowIcon.CustomIconHelper;

import net.minecraft.client.main.Main;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 : subproject 1.14.4 [dummy]</li>
 * <li>mc1.15+: subproject 1.16.5 (main project)        &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(Main.class)
public abstract class MixinMain {
    @Inject(
            //#if MC > 11903 || MC < 11900
            method = "main",
            remap = false,
            //#else
            //$$ method = "run",
            //#endif
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 12105
                    //$$ target = "Lcom/mojang/blaze3d/systems/RenderSystem;initRenderThread()V",
                    //#else
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;finishInitialization()V",
                    //#endif
                    remap = false
            )
    )
    private static void finishInitializationRenderSystem(CallbackInfo ci) {
        if (Configs.customWindowIcon.getBooleanValue()) {
            CustomIconHelper.updateIcon();
        }
    }
}
