package top.hendrixshen.tweakmyclient.mixin.feature.autoRespawn;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public abstract class MixinDeathScreen {
    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        if (Configs.autoRespawn.getBooleanValue()) {
            Minecraft mc = Minecraft.getInstance();
            assert mc.player != null;
            mc.player.respawn();
            mc.setScreen(null);
        }
    }
}
