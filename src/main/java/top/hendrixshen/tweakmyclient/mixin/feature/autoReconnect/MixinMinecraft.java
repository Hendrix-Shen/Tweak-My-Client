package top.hendrixshen.tweakmyclient.mixin.feature.autoReconnect;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.multiplayer.ServerData;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.impl.feature.autoReconnect.AutoReconnectUtil;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Inject(method = "setCurrentServer", at = @At("HEAD"))
    private void setCurrentServerEntry(ServerData serverData, CallbackInfo ci) {
        AutoReconnectUtil.setLastServer(serverData);
    }
}
