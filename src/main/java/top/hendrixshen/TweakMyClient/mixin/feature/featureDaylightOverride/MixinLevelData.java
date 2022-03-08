package top.hendrixshen.TweakMyClient.mixin.feature.featureDaylightOverride;

import net.minecraft.world.level.storage.LevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.TweakMyClient.config.Configs;

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
        if (Configs.Feature.FEATURE_DAYLIGHT_OVERRIDE.getBooleanValue()) {
            cir.setReturnValue((long) Configs.Generic.DAYLIGHT_OVERRIDE_TIME.getIntegerValue());
        }
    }
}
