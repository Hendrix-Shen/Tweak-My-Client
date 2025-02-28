package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderScoreboard;

import net.minecraft.client.gui.Gui;
import net.minecraft.world.scores.Objective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.game.Configs;

//#if MC > 11904
//$$ import net.minecraft.client.gui.GuiGraphics;
//#elseif MC > 11502
import com.mojang.blaze3d.vertex.PoseStack;
//#endif

@Mixin(Gui.class)
public abstract class MixinGui {
    @Inject(method = "displayScoreboardSidebar", at = @At("HEAD"), cancellable = true)
    private void onRenderScoreboardSidebar(
            //#if MC > 11904
            //$$ GuiGraphics guiGraphics,
            //#elseif MC > 11502
            PoseStack poseStack,
            //#endif
            Objective objective,
            CallbackInfo ci
    ) {
        if (Configs.disableScoreboardRender.getBooleanValue()) {
            ci.cancel();
        }
    }
}
