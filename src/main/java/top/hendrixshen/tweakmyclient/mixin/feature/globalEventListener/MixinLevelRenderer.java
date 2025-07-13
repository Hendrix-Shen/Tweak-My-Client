package top.hendrixshen.tweakmyclient.mixin.feature.globalEventListener;

import top.hendrixshen.tweakmyclient.impl.feature.globalEventListener.GlobalEventListener;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer {
    @Inject(method = "globalLevelEvent", at = @At("HEAD"))
    private void onProcessGlobalEvent(int eventId, BlockPos pos, int data, CallbackInfo ci) {
        GlobalEventListener.onGlobalEvent(eventId, pos);
    }
}
