package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;

import java.util.Deque;

@Mixin(ToastManager.class)
public class MixinToastManager {
    @Shadow
    @Final
    private Deque<Toast> toastQueue;

    @Inject(
            method = "add",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onAddToToastQueue(Toast toast, CallbackInfo ci) {
        if (Configs.Disable.DISABLE_RENDER_TOAST.getBooleanValue()) {
            toastQueue.clear();
            ci.cancel();
        }
    }
}
