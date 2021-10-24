package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.util.AutoReconnectUtils;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Inject(
            method = "setCurrentServer",
            at = @At(
                    "HEAD"
            )
    )
    private void setCurrentServerEntry(ServerData serverData, CallbackInfo ci) {
        AutoReconnectUtils.ReconnectTimer = Configs.Generic.AUTO_RECONNECT_TIMER.getIntegerValue() * 20;
        if (serverData != null) {
            AutoReconnectUtils.setLastServer(serverData);
        }
    }
}
