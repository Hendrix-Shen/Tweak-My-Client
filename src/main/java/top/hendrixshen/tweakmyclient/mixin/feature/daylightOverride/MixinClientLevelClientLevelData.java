package top.hendrixshen.tweakmyclient.mixin.feature.daylightOverride;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.multiplayer.ClientLevel;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc1.15: subproject 1.15.2 [dummy]</li>
 * <li>mc1.16+        : subproject 1.16.5 (main project)        &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(ClientLevel.ClientLevelData.class)
public abstract class MixinClientLevelClientLevelData {
    @Inject(method = "getDayTime", at = @At("RETURN"), cancellable = true)
    private void onGetTimeOfDay(CallbackInfoReturnable<Long> cir) {
        if (Configs.daylightOverride.getBooleanValue()) {
            cir.setReturnValue((long) Configs.daylightOverrideTime.getIntegerValue());
        }
    }
}
