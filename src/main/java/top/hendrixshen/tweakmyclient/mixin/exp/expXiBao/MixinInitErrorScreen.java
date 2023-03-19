package top.hendrixshen.tweakmyclient.mixin.exp.expXiBao;

import fudge.notenoughcrashes.gui.InitErrorScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;

@Dependencies(and = @Dependency("notenoughcrashes"))
@Mixin(value = InitErrorScreen.class)
public class MixinInitErrorScreen {
    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    //#if MC > 11605
                    target = "Lfudge/notenoughcrashes/gui/InitErrorScreen;drawCenteredString(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V"
                    //#elseif MC > 11502
                    //$$ target = "Lfudge/notenoughcrashes/gui/InitErrorScreen;drawCenteredString(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"
                    //#else
                    //$$ target = "Lfudge/notenoughcrashes/gui/InitErrorScreen;drawCenteredString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V"
                    //#endif
            ),
            //#if MC > 11502
            index = 4
            //#else
            //$$ index = 3
            //#endif
    )
    private int offsetTitle(int value) {
        return Configs.expXiBao ? value + 25 : value;
    }

    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    //#if MC > 11502
                    target = "Lfudge/notenoughcrashes/gui/InitErrorScreen;drawString(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V"
                    //#else
                    //$$ target = "Lfudge/notenoughcrashes/gui/InitErrorScreen;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V"
                    //#endif
            ),
            //#if MC > 11502
            index = 4
            //#else
            //$$ index = 3
            //#endif
    )
    private int offsetContent(int value) {
        return Configs.expXiBao ? value + 15 : value;
    }

    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    //#if MC > 11502
                    target = "Lfudge/notenoughcrashes/gui/InitErrorScreen;drawFileNameString(Lcom/mojang/blaze3d/vertex/PoseStack;I)V"
                    //#else
                    //$$ target = "Lfudge/notenoughcrashes/gui/InitErrorScreen;drawFileNameString(I)V"
                    //#endif
            ),
            //#if MC > 11502
            index = 1
            //#else
            //$$ index = 0
            //#endif
    )
    private int offsetFileName(int value) {
        return Configs.expXiBao ? value + 15 : value;
    }
}
