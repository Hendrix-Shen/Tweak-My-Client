package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderToast;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToastComponent.class)
public abstract class MixinToastComponent {
    @Inject(method = "addToast", at = @At("HEAD"), cancellable = true)
    private void onAddToToastQueue(Toast toast, CallbackInfo ci) {
        if (Configs.disableToastRender.getBooleanValue()) {
            ci.cancel();
        }
    }
}
