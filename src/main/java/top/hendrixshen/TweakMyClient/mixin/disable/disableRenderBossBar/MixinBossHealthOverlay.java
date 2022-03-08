package top.hendrixshen.TweakMyClient.mixin.disable.disableRenderBossBar;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.BossHealthOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(BossHealthOverlay.class)
public class MixinBossHealthOverlay {
    @Inject(
            method = "render",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onRender(CallbackInfo ci) {
        if (Configs.Disable.DISABLE_RENDER_BOSS_BAR.getBooleanValue()) {
            ci.cancel();
        }
    }
}
