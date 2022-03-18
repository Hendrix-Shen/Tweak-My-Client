package top.hendrixshen.tweakmyclient.mixin.disable.disableClientBlockEvents;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {
    @Inject(
            method = "handleBlockEvent",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onBlockEvents(ClientboundBlockEventPacket clientboundBlockEventPacket, CallbackInfo ci) {
        if (Configs.disableClientBlockEvents.getBooleanValue()) {
            ci.cancel();
        }
    }
}
