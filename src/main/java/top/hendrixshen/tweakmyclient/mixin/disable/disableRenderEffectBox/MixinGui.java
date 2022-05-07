package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderEffectBox;

//#if MC >= 11600
import com.mojang.blaze3d.vertex.PoseStack;
//#endif
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(Gui.class)
public class MixinGui {
    @Inject(
            method = "renderEffects",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    //#if MC >= 11600
    private void onRender(PoseStack poseStack, CallbackInfo ci) {
    //#else
    //$$ private void onRender(CallbackInfo ci) {
    //#endif
        if (Configs.disableRenderEffectBox) {
            ci.cancel();
        }
    }
}
