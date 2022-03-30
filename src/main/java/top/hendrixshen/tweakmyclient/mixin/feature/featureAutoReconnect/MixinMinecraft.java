package top.hendrixshen.tweakmyclient.mixin.feature.featureAutoReconnect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.AutoReconnectUtil;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Inject(
            method = "setCurrentServer",
            at = @At(
                    "HEAD"
            )
    )
    private void setCurrentServerEntry(ServerData serverData, CallbackInfo ci) {
        AutoReconnectUtil.ReconnectTimer = Configs.autoReconnectTimer * 20;
        if (serverData != null) {
            AutoReconnectUtil.setLastServer(serverData);
        }
    }
}
