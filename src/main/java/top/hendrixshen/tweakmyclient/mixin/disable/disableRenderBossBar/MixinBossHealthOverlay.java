package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderBossBar;

import net.minecraft.client.gui.components.BossHealthOverlay;
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

@Mixin(BossHealthOverlay.class)
public abstract class MixinBossHealthOverlay {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(
            //#if MC > 11904
            //$$ GuiGraphics guiGraphics,
            //#elseif MC > 11502
            PoseStack poseStack,
            //#endif
            CallbackInfo ci
    ) {
        if (Configs.disableBossBarRender.getBooleanValue()) {
            ci.cancel();
        }
    }
}
