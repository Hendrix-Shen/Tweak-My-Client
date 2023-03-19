package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderBossBar;

import net.minecraft.client.gui.components.BossHealthOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

//#if MC > 11502
import com.mojang.blaze3d.vertex.PoseStack;
//#endif

@Mixin(BossHealthOverlay.class)
public class MixinBossHealthOverlay {
    @Inject(
            method = "render",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    //#if MC > 11502
    private void onRender(PoseStack poseStack, CallbackInfo ci) {
    //#else
    //$$ private void onRender(CallbackInfo ci) {
    //#endif
        if (Configs.disableRenderBossBar) {
            ci.cancel();
        }
    }
}
