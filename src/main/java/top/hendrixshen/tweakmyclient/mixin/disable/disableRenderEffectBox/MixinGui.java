package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderEffectBox;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.gui.Gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class MixinGui {
    @Inject(method = "renderEffects", at = @At("HEAD"), cancellable = true)
    private void onRender(CallbackInfo ci) {
        if (Configs.disableEffectBoxRender.getBooleanValue()) {
            ci.cancel();
        }
    }
}
