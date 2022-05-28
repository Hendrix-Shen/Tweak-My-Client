package top.hendrixshen.tweakmyclient.mixin.patch.disableResourcePackCompatCheck;

import net.minecraft.server.packs.repository.PackCompatibility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(PackCompatibility.class)
public class MixinPackCompatibility {
    @Inject(
            method = "isCompatible",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void isCompat(CallbackInfoReturnable<Boolean> cir) {
        if (Configs.disableResourcePackCompatCheck) {
            cir.setReturnValue(true);
        }
    }
}
