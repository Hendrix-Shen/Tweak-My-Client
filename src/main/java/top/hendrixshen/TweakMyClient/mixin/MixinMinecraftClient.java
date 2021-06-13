package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.network.ServerInfo;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.interfaces.IClientPlayerInteractionManager;
import top.hendrixshen.TweakMyClient.interfaces.IMinecraftClient;
import top.hendrixshen.TweakMyClient.util.AutoReconnect;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient implements IMinecraftClient {
    @Shadow @Nullable public ClientPlayerInteractionManager interactionManager;

    @Inject(
            method = "setCurrentServerEntry",
            at = @At(
                    "HEAD"
            )
    )
    private void setCurrentServerEntry(ServerInfo serverInfo, CallbackInfo info) {
        AutoReconnect.ReconnectTimer = Configs.Generic.AUTO_RECONNECT_TIMER.getIntegerValue() * 20;
        if (serverInfo != null) {
            AutoReconnect.setLastServer(serverInfo);
        }
    }

    @Override
    public IClientPlayerInteractionManager getInteractionManager()
    {
        return (IClientPlayerInteractionManager)interactionManager;
    }
}
