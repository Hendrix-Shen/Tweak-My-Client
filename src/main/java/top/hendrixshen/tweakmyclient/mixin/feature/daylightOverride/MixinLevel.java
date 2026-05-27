package top.hendrixshen.tweakmyclient.mixin.feature.daylightOverride;

import top.hendrixshen.magiclib.util.MiscUtil;
import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class MixinLevel {
    @Inject(
            //#if MC >= 26.1
            //$$ method = "getOverworldClockTime",
            //#else
            method = "getDayTime",
            //#endif
            at = @At("HEAD"),
            cancellable = true
    )
    private void onGetTimeOfDay(CallbackInfoReturnable<Long> cir) {
        if (MiscUtil.cast(this) instanceof ClientLevel) {
            if (Configs.daylightOverride.getBooleanValue()) {
                cir.setReturnValue((long) Configs.daylightOverrideTime.getIntegerValue());
            }
        }
    }
}
