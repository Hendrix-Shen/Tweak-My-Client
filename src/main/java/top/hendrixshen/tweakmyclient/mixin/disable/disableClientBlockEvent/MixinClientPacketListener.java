package top.hendrixshen.tweakmyclient.mixin.disable.disableClientBlockEvent;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener {
    @Inject(method = "handleBlockEvent", at = @At("HEAD"), cancellable = true)
    private void onHandleBlockEvent(ClientboundBlockEventPacket clientboundBlockEventPacket, CallbackInfo ci) {
        if (Configs.disableClientBlockEvent.getBooleanValue()) {
            ci.cancel();
        }
    }
}
