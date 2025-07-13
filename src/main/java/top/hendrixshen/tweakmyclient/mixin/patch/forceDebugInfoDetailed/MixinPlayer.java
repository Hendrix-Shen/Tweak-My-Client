package top.hendrixshen.tweakmyclient.mixin.patch.forceDebugInfoDetailed;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class MixinPlayer {
    @Inject(method = "isReducedDebugInfo", at = @At("HEAD"), cancellable = true)
    private void isReducedDebugInfo(CallbackInfoReturnable<Boolean> cir) {
        if (Configs.forceDebugInfoDetailed.getBooleanValue()) {
            cir.setReturnValue(false);
        }
    }
}
