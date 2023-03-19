package top.hendrixshen.tweakmyclient.mixin.exp.expXiBao;

import fudge.notenoughcrashes.gui.CrashScreen;
import fudge.notenoughcrashes.gui.ProblemScreen;
import net.minecraft.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;

@Dependencies(and = @Dependency("notenoughcrashes"))
@Mixin(value = CrashScreen.class)
public abstract class MixinCrashScreen extends ProblemScreen {
    protected MixinCrashScreen(CrashReport report) {
        super(report);
    }

    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    //#if MC > 11605
                    target = "Lfudge/notenoughcrashes/gui/CrashScreen;drawCenteredString(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V"
                    //#elseif MC > 11502
                    //$$ target = "Lfudge/notenoughcrashes/gui/CrashScreen;drawCenteredString(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"
                    //#else
                    //$$ target = "Lfudge/notenoughcrashes/gui/CrashScreen;drawCenteredString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V"
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

    @ModifyVariable(
            method = "render",
            at = @At(
                    value = "STORE"
            ),
            ordinal = 4
    )
    private int offsetContent(int value) {
        return Configs.expXiBao ? value + 15 : value;
    }
}
