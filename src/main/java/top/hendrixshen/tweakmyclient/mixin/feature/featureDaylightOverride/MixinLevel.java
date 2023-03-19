package top.hendrixshen.tweakmyclient.mixin.feature.featureDaylightOverride;

import org.spongepowered.asm.mixin.Mixin;

//#if MC > 11404
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import top.hendrixshen.tweakmyclient.config.Configs;
//#else
//$$ import top.hendrixshen.magiclib.compat.preprocess.api.DummyClass;
//#endif

//#if MC > 11404
@Mixin(Level.class)
//#else
//$$ @Mixin(DummyClass.class)
//#endif
public class MixinLevel {
    //#if MC > 11404
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
    //#endif
}
