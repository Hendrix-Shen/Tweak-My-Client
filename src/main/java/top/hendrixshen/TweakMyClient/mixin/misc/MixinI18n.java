package top.hendrixshen.tweakmyclient.mixin.misc;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.language.Locale;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* Fix the dumb "Caused by: java.lang.NullPointerException: Cannot invoke
    "net.minecraft.client.resources.language.Locale.get(String, Object[])" because
    "net.minecraft.client.resources.language.I18n.locale" is null"
*/
@Mixin(I18n.class)
public class MixinI18n {
    @Shadow private static Locale locale;

    @Inject(
            method = "get",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private static void get(String string, Object[] objects, CallbackInfoReturnable<String> cir) {
        if (locale == null) {
            cir.setReturnValue(string);
        }
    }
}
