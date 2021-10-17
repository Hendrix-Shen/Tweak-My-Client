package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.util.AutoReconnectUtils;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {
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
}
