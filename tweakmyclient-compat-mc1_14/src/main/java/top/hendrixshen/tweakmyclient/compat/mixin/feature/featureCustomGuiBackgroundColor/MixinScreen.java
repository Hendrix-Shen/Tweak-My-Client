package top.hendrixshen.tweakmyclient.compat.mixin.feature.featureCustomGuiBackgroundColor;

import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(Screen.class)
public abstract class MixinScreen extends AbstractContainerEventHandler {
    /**
     * args
     * 0 – startX
     * 1 – startY
     * 2 – endX
     * 3 – endY
     * 4 – colorStart
     * 5 – colorEnd
     */
    @ModifyArgs(
            method = "renderBackground(I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/Screen;fillGradient(IIIIII)V"
            )
    )
    private void onFillGradient(Args args) {
        if (Configs.featureCustomGuiBackgroundColor) {
            args.set(4, Configs.colorGuiStart.intValue);
            args.set(5, Configs.colorGuiStop.intValue);
        }
    }
}
