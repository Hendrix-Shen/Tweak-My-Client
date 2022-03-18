package top.hendrixshen.tweakmyclient.mixin.disable.disableGuiShadowLayer;

import com.mojang.blaze3d.vertex.PoseStack;
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
            method = "renderBackground(Lcom/mojang/blaze3d/vertex/PoseStack;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/Screen;fillGradient(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V"
            ),
            cancellable = true
    )
    private void onFillGradient(PoseStack poseStack, int i, CallbackInfo ci) {
        if (Configs.disableGuiShadowLayer.getBooleanValue()) {
            ci.cancel();
        }
    }
}
