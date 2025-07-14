package top.hendrixshen.tweakmyclient.mixin.feature.autoReconnect;

import top.hendrixshen.tweakmyclient.impl.feature.autoReconnect.AutoReconnectUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc1.19.2: subproject 1.16.5 (main project)        &lt;--------</li>
 * <li>mc1.19.3+        : subproject 1.21.3 [dummy]</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Inject(method = "setCurrentServer(Lnet/minecraft/client/multiplayer/ServerData;)V", at = @At("HEAD"))
    private void setCurrentServerEntry(ServerData serverData, CallbackInfo ci) {
        AutoReconnectUtil.setLastServer(serverData);
    }
}
