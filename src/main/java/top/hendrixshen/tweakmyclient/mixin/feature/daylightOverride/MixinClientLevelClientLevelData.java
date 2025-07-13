package top.hendrixshen.tweakmyclient.mixin.feature.daylightOverride;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.multiplayer.ClientLevel;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.ClientLevelData.class)
public abstract class MixinClientLevelClientLevelData {
    @Inject(method = "getDayTime", at = @At("RETURN"), cancellable = true)
    private void onGetTimeOfDay(CallbackInfoReturnable<Long> cir) {
        if (Configs.daylightOverride.getBooleanValue()) {
            cir.setReturnValue((long) Configs.daylightOverrideTime.getIntegerValue());
        }
    }
}
