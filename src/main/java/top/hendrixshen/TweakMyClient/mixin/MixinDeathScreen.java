package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.gui.screen.DeathScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.TweakMyClient;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(DeathScreen.class)
public class MixinDeathScreen {
    @Inject(
            method = "tick",
            at = @At(
                    value = "TAIL"
            )
    )
    private void onTick(CallbackInfo ci) {
        if (Configs.Feature.FEATURE_AUTO_RESPAWN.getBooleanValue()) {
            assert TweakMyClient.getMinecraftClient().player != null;
            TweakMyClient.getMinecraftClient().player.requestRespawn();
            TweakMyClient.getMinecraftClient().openScreen(null);
        }
    }
}
