package top.hendrixshen.tweakmyclient.mixin.disable.disableGuiShadowLayer;

import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

//#if MC > 11502
import com.mojang.blaze3d.vertex.PoseStack;
//#endif

@Mixin(Screen.class)
public abstract class MixinScreen extends AbstractContainerEventHandler {
    @Inject(
            //#if MC > 11903
            method = "renderBackground",
            //#elseif MC > 11502
            //$$ method = "renderBackground(Lcom/mojang/blaze3d/vertex/PoseStack;I)V",
            //#else
            //$$ method = "renderBackground(I)V",
            //#endif
            at = @At(
                    value = "INVOKE",
                    //#if MC > 11502
                    target = "Lnet/minecraft/client/gui/screens/Screen;fillGradient(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V"
                    //#else
                    //$$ target = "Lnet/minecraft/client/gui/screens/Screen;fillGradient(IIIIII)V"
                    //#endif
            ),
            cancellable = true
    )
    //#if MC > 11904
    private void onFillGradient(PoseStack poseStack, CallbackInfo ci) {
    //#elseif MC > 11502
    //$$ private void onFillGradient(PoseStack poseStack, int i, CallbackInfo ci) {
    //#else
    //$$ private void onFillGradient(int i, CallbackInfo ci) {
    //#endif
        if (Configs.disableGuiShadowLayer) {
            ci.cancel();
        }
    }
}
