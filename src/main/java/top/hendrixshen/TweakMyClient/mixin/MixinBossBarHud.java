package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(BossBarHud.class)
public class MixinBossBarHud {
    @Inject(
            method = "render",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onRender(MatrixStack matrices, CallbackInfo ci) {
        if (Configs.Disable.DISABLE_RENDER_BOSS_BAR.getBooleanValue()) {
            ci.cancel();
        }
    }
}
