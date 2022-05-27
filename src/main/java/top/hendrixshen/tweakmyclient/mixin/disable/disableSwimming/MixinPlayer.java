package top.hendrixshen.tweakmyclient.mixin.disable.disableSwimming;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(Player.class)
public abstract class MixinPlayer {
    @Inject(
            method = "isSwimming",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void isSwimming(CallbackInfoReturnable<Boolean> cir) {
        if (Configs.disableSwimming && (Object) this instanceof LocalPlayer) {
            cir.setReturnValue(false);
        }
    }
}
