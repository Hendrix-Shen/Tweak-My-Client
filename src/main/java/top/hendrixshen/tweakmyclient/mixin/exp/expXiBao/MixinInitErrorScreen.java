package top.hendrixshen.tweakmyclient.mixin.exp.expXiBao;

import fudge.notenoughcrashes.gui.InitErrorScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;

@Dependencies(and = @Dependency("notenoughcrashes"))
@Mixin(value = InitErrorScreen.class)
public class MixinInitErrorScreen {
    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lfudge/notenoughcrashes/gui/InitErrorScreen;drawCenteredString(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V"
            ),
            index = 4
    )
    private int offsetTitle(int value) {
        return Configs.expXiBao ? value + 25 : value;
    }

    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lfudge/notenoughcrashes/gui/InitErrorScreen;drawString(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V"
            ),
            index = 4
    )
    private int offsetContent(int value) {
        return Configs.expXiBao ? value + 15 : value;
    }

    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lfudge/notenoughcrashes/gui/InitErrorScreen;drawFileNameString(Lcom/mojang/blaze3d/vertex/PoseStack;I)V"
            ),
            index = 1
    )
    private int offsetFileName(int value) {
        return Configs.expXiBao ? value + 15 : value;
    }
}
