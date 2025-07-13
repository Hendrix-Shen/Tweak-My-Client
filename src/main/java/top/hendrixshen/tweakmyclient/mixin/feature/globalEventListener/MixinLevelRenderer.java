package top.hendrixshen.tweakmyclient.mixin.feature.globalEventListener;

import top.hendrixshen.tweakmyclient.impl.feature.globalEventListener.GlobalEventListener;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc1.21.2: subproject 1.16.5 (main project)        &lt;--------</li>
 * <li>mc1.21.3+        : subproject 1.21.3 [dummy]</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer {
    @Inject(method = "globalLevelEvent", at = @At("HEAD"))
    private void onProcessGlobalEvent(int eventId, BlockPos pos, int data, CallbackInfo ci) {
        GlobalEventListener.onGlobalEvent(eventId, pos);
    }
}
