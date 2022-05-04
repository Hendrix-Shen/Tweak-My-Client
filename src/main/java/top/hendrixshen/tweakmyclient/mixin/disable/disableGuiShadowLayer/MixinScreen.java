package top.hendrixshen.tweakmyclient.mixin.disable.disableGuiShadowLayer;

//#if MC >= 11600
import com.mojang.blaze3d.vertex.PoseStack;
//#endif
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(Screen.class)
public abstract class MixinScreen extends AbstractContainerEventHandler {
    @Inject(
            //#if MC >= 11600
            method = "renderBackground(Lcom/mojang/blaze3d/vertex/PoseStack;I)V",
            //#else
            //$$ method = "renderBackground(I)V",
            //#endif
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 11600
                    target = "Lnet/minecraft/client/gui/screens/Screen;fillGradient(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V"
                    //#else
                    //$$ target = "Lnet/minecraft/client/gui/screens/Screen;fillGradient(IIIIII)V"
                    //#endif
            ),
            cancellable = true
    )
    //#if MC >= 11600
    private void onFillGradient(PoseStack poseStack, int i, CallbackInfo ci) {
    //#else
    //$$ private void onFillGradient(int i, CallbackInfo ci) {
    //#endif
        if (Configs.disableGuiShadowLayer) {
            ci.cancel();
        }
    }
}
