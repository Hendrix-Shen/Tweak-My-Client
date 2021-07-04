package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.util.AutoReconnectUtils;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {
    @Shadow
    @Nullable
    public ClientPlayerInteractionManager interactionManager;

    @Shadow
    public abstract ToastManager getToastManager();

    @Inject(
            method = "setCurrentServerEntry",
            at = @At(
                    "HEAD"
            )
    )
    private void setCurrentServerEntry(ServerInfo serverInfo, CallbackInfo info) {
        AutoReconnectUtils.ReconnectTimer = Configs.Generic.AUTO_RECONNECT_TIMER.getIntegerValue() * 20;
        if (serverInfo != null) {
            AutoReconnectUtils.setLastServer(serverInfo);
        }
    }

    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/toast/ToastManager;draw(Lnet/minecraft/client/util/math/MatrixStack;)V"
            )
    )
    private void onRenderToast(ToastManager toastManager, MatrixStack matrices) {
        if (!Configs.Disable.DISABLE_RENDER_TOAST.getBooleanValue()) {
            toastManager.draw(matrices);
        } else {
            this.getToastManager().clear();
        }
    }
}
