package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(ClientWorld.Properties.class)
public class MixinClientWorldProperties {
    @Inject(
            method = "getTimeOfDay",
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
