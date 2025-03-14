package top.hendrixshen.tweakmyclient.mixin.feature.globalEventListener;

import net.minecraft.client.renderer.LevelEventHandler;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.impl.feature.globalEventListener.GlobalEventListener;

@Mixin(LevelEventHandler.class)
public abstract class MixinLevelEventHandler {
    @Inject(method = "globalLevelEvent", at = @At("HEAD"))
    private void onProcessGlobalEvent(int eventId, BlockPos pos, int data, CallbackInfo ci) {
        GlobalEventListener.onGlobalEvent(eventId, pos);
    }
}
