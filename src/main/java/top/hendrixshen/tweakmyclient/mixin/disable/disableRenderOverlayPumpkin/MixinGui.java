package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayPumpkin;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;

@Dependencies(and = @Dependency(value = "minecraft", versionPredicate = "<1.17"))
@Mixin(Gui.class)
public abstract class MixinGui {
    @Inject(
            method = "renderPumpkin",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onRenderPumpkinOverlay(CallbackInfo ci) {
        if (Configs.disableRenderOverlayPumpkin) {
            ci.cancel();
        }
    }
}
