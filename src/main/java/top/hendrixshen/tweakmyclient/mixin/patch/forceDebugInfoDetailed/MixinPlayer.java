package top.hendrixshen.tweakmyclient.mixin.patch.forceDebugInfoDetailed;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(Player.class)
public class MixinPlayer {
    @Inject(
            method = "isReducedDebugInfo",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void isReducedDebugInfo(CallbackInfoReturnable<Boolean> cir) {
        if (Configs.forceDebugInfoDetailed) {
            cir.setReturnValue(false);
        }
    }
}
