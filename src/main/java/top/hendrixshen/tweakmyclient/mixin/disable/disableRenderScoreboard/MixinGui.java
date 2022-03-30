package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderScoreboard;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.scores.Objective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;

@Dependencies(and = @Dependency(value = "minecraft", versionPredicate = ">=1.16"))
@Mixin(Gui.class)
public abstract class MixinGui {
    @Inject(
            method = "displayScoreboardSidebar",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onRenderScoreboardSidebar(PoseStack poseStack, Objective objective, CallbackInfo ci) {
        if (Configs.disableRenderScoreboard) {
            ci.cancel();
        }
    }
}
