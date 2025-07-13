package top.hendrixshen.tweakmyclient.mixin.feature.autoReconnect;

import top.hendrixshen.tweakmyclient.impl.feature.autoReconnect.AutoReconnectUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Inject(method = "setCurrentServer(Lnet/minecraft/client/multiplayer/ServerData;)V", at = @At("HEAD"))
    private void setCurrentServerEntry(ServerData serverData, CallbackInfo ci) {
        AutoReconnectUtil.setLastServer(serverData);
    }
}
