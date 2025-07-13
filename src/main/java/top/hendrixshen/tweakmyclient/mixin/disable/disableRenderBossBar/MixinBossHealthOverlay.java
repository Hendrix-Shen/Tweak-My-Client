package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderBossBar;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.gui.components.BossHealthOverlay;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossHealthOverlay.class)
public abstract class MixinBossHealthOverlay {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(CallbackInfo ci) {
        if (Configs.disableBossBarRender.getBooleanValue()) {
            ci.cancel();
        }
    }
}
