package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.util.AutoReconnect;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(
            method = "setCurrentServerEntry",
            at = @At(
                    "HEAD"
            )
    )
    private void setCurrentServerEntry(ServerInfo serverInfo, CallbackInfo info) {
        AutoReconnect.ReconnectTimer = 100;
        if (serverInfo != null) {
            AutoReconnect.setLastServer(serverInfo);
        }
    }
}
