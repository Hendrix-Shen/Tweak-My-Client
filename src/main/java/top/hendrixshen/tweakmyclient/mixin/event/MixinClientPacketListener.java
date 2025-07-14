package top.hendrixshen.tweakmyclient.mixin.event;

import top.hendrixshen.magiclib.impl.event.EventManager;
import top.hendrixshen.tweakmyclient.impl.event.LocalPlayerEvent.LocalPlayerGameJoinEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener {
    @Inject(method = "handleLogin", at = @At(value = "RETURN"))
    private void onGameJoin(ClientboundLoginPacket clientboundLoginPacket, CallbackInfo ci) {
        EventManager.dispatch(new LocalPlayerGameJoinEvent(Minecraft.getInstance().player));
    }
}
