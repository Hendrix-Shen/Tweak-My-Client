package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.BlockEventS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
    @Inject(
            method = "onBlockEvent",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onBlockEvents(BlockEventS2CPacket packet, CallbackInfo ci) {
        if (Configs.Disable.DISABLE_CLIENT_BLOCK_EVENTS.getBooleanValue()) {
            ci.cancel();
        }
    }
}
