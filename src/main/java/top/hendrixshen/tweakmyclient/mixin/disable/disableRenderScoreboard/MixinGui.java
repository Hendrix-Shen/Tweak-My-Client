package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderScoreboard;

import net.minecraft.client.gui.Gui;
import net.minecraft.world.scores.Objective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

//#if MC > 11502
import com.mojang.blaze3d.vertex.PoseStack;
//#endif

@Mixin(Gui.class)
public abstract class MixinGui {
    @Inject(
            method = "displayScoreboardSidebar",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    //#if MC > 11502
    private void onRenderScoreboardSidebar(PoseStack poseStack, Objective objective, CallbackInfo ci) {
    //#else
    //$$ private void onRenderScoreboardSidebar(Objective objective, CallbackInfo ci) {
    //#endif
        if (Configs.disableRenderScoreboard) {
            ci.cancel();
        }
    }
}
