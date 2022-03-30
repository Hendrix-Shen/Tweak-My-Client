package top.hendrixshen.tweakmyclient.compat.mixin.feature.featureDaylightOverride;

import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(Level.class)
public class MixinLevel {
    @Inject(
            method = "getDayTime",
            at = @At(
                    value = "RETURN"
            ),
            cancellable = true
    )
    private void onGetTimeOfDay(CallbackInfoReturnable<Long> cir) {
        if (Configs.featureDaylightOverride) {
            cir.setReturnValue((long) Configs.daylightOverrideTime);
        }
    }
}
