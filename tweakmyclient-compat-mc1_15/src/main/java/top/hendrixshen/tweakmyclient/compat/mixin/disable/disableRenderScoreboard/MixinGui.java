package top.hendrixshen.tweakmyclient.compat.mixin.disable.disableRenderScoreboard;

import net.minecraft.client.gui.Gui;
import net.minecraft.world.scores.Objective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(Gui.class)
public abstract class MixinGui {
    @Inject(
            method = "displayScoreboardSidebar",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onRenderScoreboardSidebar(Objective objective, CallbackInfo ci) {
        if (Configs.disableRenderScoreboard) {
            ci.cancel();
        }
    }
}
