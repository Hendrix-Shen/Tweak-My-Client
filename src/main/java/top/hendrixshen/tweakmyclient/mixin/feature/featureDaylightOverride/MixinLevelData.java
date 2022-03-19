package top.hendrixshen.tweakmyclient.mixin.feature.featureDaylightOverride;

import net.minecraft.world.level.storage.LevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(LevelData.class)
public class MixinLevelData {
    @Inject(
            method = "getDayTime",
            at = @At(
                    value = "RETURN"
            ),
            cancellable = true
    )
    private void onGetTimeOfDay(CallbackInfoReturnable<Long> cir) {
        if (Configs.featureDaylightOverride.getBooleanValue()) {
            cir.setReturnValue((long) Configs.daylightOverrideTime.getIntegerValue());
        }
    }
}
