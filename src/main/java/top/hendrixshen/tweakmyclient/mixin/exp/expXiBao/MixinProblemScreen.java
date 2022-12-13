package top.hendrixshen.tweakmyclient.mixin.exp.expXiBao;

//#if MC > 11502
import com.mojang.blaze3d.vertex.PoseStack;
//#endif
import fudge.notenoughcrashes.gui.ProblemScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
//#if MC > 11502
import org.jetbrains.annotations.NotNull;
//#endif
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.AutoReconnectUtil;

@Dependencies(and = @Dependency("notenoughcrashes"))
@Mixin(value = ProblemScreen.class)
public abstract class MixinProblemScreen extends Screen {
    protected MixinProblemScreen(Component component) {
        super(component);
    }

    @ModifyArg(
            method = "addSuspectedModsWidget",
            at = @At(
                    value = "INVOKE",
                    target = "Lfudge/notenoughcrashes/gui/util/TextWidget;<init>(Lnet/minecraft/network/chat/Component;ILnet/minecraft/client/gui/Font;II)V"
            ),
            index = 4
    )
    private int offsetSuspectedMods(int value) {
        return Configs.expXiBao ? value + 15 : value;
    }

    @Override
    //#if MC > 11502
    public void renderBackground(@NotNull PoseStack poseStack, int i) {
    //#else
    //$$ public void renderBackground(int i) {
    //#endif
        if (Configs.expXiBao) {
            AutoReconnectUtil.renderXibao(this);
        } else {
            //#if MC > 11502
            super.renderBackground(poseStack, i);
            //#else
            //$$ super.renderBackground(i);
            //#endif
        }
    }
}
