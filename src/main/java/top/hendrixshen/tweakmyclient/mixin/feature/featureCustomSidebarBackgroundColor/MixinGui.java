package top.hendrixshen.tweakmyclient.mixin.feature.featureCustomSidebarBackgroundColor;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(Gui.class)
public abstract class MixinGui {
    @ModifyArgs(
            //#if MC > 12002
            method = "method_55440",
            //#else
            //$$ method = "displayScoreboardSidebar",
            //#endif
            at = @At(
                    value = "INVOKE",
                    //#if MC > 11904
                    target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V",
                    //#elseif MC > 11502
                    //$$ target = "Lnet/minecraft/client/gui/Gui;fill(Lcom/mojang/blaze3d/vertex/PoseStack;IIIII)V",
                    //#else
                    //$$ target = "Lnet/minecraft/client/gui/Gui;fill(IIIII)V",
                    //#endif
                    //#if MC > 12002
                    ordinal = 0
                    //#else
                    //$$ ordinal = 1
                    //#endif
            )
    )
    private void changeSidebarTitleBackgroundColor(Args args) {
        if (Configs.featureCustomSidebarBackgroundColor) {
            //#if MC > 11502 && MC < 12000
            //$$ args.set(5, Configs.colorSidebarTitle.intValue);
            //#else
            args.set(4, Configs.colorSidebarTitle.intValue);
            //#endif
        }
    }

    @ModifyArgs(
            //#if MC > 12002
            method = "method_55440",
            //#else
            //$$ method = "displayScoreboardSidebar",
            //#endif
            at = @At(
                    value = "INVOKE",
                    //#if MC > 11904
                    target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V",
                    //#elseif MC > 11502
                    //$$ target = "Lnet/minecraft/client/gui/Gui;fill(Lcom/mojang/blaze3d/vertex/PoseStack;IIIII)V",
                    //#else
                    //$$ target = "Lnet/minecraft/client/gui/Gui;fill(IIIII)V",
                    //#endif
                    //#if MC > 12002
                    ordinal = 1
                    //#else
                    //$$ ordinal = 0
                    //#endif
            )
    )
    private void changeSidebarContentBackgroundColor_1(Args args) {
        if (Configs.featureCustomSidebarBackgroundColor) {
            //#if MC > 11502 && MC < 12000
            //$$ args.set(5, Configs.colorSidebarContent.intValue);
            //#else
            args.set(4, Configs.colorSidebarContent.intValue);
            //#endif
        }
    }

    //#if MC < 12002
    //$$ @ModifyArgs(
    //$$         method = "displayScoreboardSidebar",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //#if MC > 11904
    //$$                 target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V",
    //#elseif MC > 11502
    //$$                 target = "Lnet/minecraft/client/gui/Gui;fill(Lcom/mojang/blaze3d/vertex/PoseStack;IIIII)V",
    //#else
    //$$                 target = "Lnet/minecraft/client/gui/Gui;fill(IIIII)V",
    //#endif
    //$$                 ordinal = 2
    //$$         )
    //$$ )
    //$$ private void changeSidebarContentBackgroundColor_2(Args args) {
    //$$     if (Configs.featureCustomSidebarBackgroundColor) {
    //#if MC > 11502 && MC < 12000
    //$$         args.set(5, Configs.colorSidebarContent.intValue);
    //#else
    //$$         args.set(4, Configs.colorSidebarContent.intValue);
    //#endif
    //$$     }
    //$$ }
    //#endif
}
