package top.hendrixshen.tweakmyclient.mixin.feature.featureDaylightOverride;

//#if MC < 11500
//$$ import net.minecraft.client.Minecraft;
//#else
import net.minecraft.world.level.Level;
//#endif
import org.spongepowered.asm.mixin.Mixin;
//#if MC >= 11500
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(Level.class)
//#else
//$$ @Mixin(Minecraft.class)
//#endif
public class MixinLevel {
    //#if MC >= 11500
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
