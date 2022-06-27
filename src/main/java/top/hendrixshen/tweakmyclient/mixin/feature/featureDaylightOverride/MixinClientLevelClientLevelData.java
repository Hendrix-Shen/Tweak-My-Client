package top.hendrixshen.tweakmyclient.mixin.feature.featureDaylightOverride;

//#if MC >= 11600
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.config.Configs;
//#else
//$$ import net.minecraft.client.Minecraft;
//#endif
import org.spongepowered.asm.mixin.Mixin;

//#if MC >=11600
@Mixin(ClientLevel.ClientLevelData.class)
//#else
//$$ @Mixin(Minecraft.class)
//#endif
public class MixinClientLevelClientLevelData {
    //#if MC >=11600
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
