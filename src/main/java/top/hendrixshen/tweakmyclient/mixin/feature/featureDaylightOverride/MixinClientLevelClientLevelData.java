package top.hendrixshen.tweakmyclient.mixin.feature.featureDaylightOverride;

import org.spongepowered.asm.mixin.Mixin;

//#if MC >= 11600
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.config.Configs;
//#else
//$$ import top.hendrixshen.magiclib.compat.preprocess.api.DummyClass;
//#endif

//#if MC > 11502
@Mixin(ClientLevel.ClientLevelData.class)
//#else
//$$ @Mixin(DummyClass.class)
//#endif
public class MixinClientLevelClientLevelData {
    //#if MC > 11502
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
