package top.hendrixshen.tweakmyclient.mixin.event;

import top.hendrixshen.magiclib.impl.event.EventManager;
import top.hendrixshen.magiclib.util.MiscUtil;
import top.hendrixshen.tweakmyclient.impl.event.LocalPlayerEvent.LocalPlayerTickEvent;

import net.minecraft.client.player.LocalPlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer {
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        EventManager.dispatch(new LocalPlayerTickEvent(MiscUtil.cast(this)));
    }
}
