package top.hendrixshen.tweakmyclient.mixin.feature.featureDaylightOverride;

import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;

@Dependencies(and = @Dependency(value = "minecraft", versionPredicate = ">=1.16"))
@Mixin(ClientLevel.ClientLevelData.class)
public class MixinClientLevelClientLevelData {
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
