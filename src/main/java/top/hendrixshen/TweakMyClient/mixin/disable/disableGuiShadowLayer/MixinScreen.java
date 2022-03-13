package top.hendrixshen.TweakMyClient.mixin.disable.disableGuiShadowLayer;

import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(Screen.class)
public abstract class MixinScreen extends AbstractContainerEventHandler {
    @Inject(
            method = "renderBackground(I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/Screen;fillGradient(IIIIII)V"
            ),
            cancellable = true
    )
    private void onFillGradient(int i, CallbackInfo ci) {
        if (Configs.Disable.DISABLE_GUI_SHADOW_LAYER.getBooleanValue()) {
            ci.cancel();
        }
    }
}
