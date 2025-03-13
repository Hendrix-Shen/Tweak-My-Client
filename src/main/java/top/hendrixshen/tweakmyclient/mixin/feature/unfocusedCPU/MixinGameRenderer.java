package top.hendrixshen.tweakmyclient.mixin.feature.unfocusedCPU;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.game.Configs;

//#if MC > 12006
//$$ import net.minecraft.client.DeltaTracker;
//#endif

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Inject(
            //#if MC > 11404
            method = "render",
            //#else
            //$$ method = "render(FJZ)V",
            //#endif
            at = @At("HEAD"),
            cancellable = true
    )
    private void onRenderHead(
            //#if MC > 12006
            //$$ DeltaTracker deltaTracker,
            //#else
            float tickDelta,
            long startTime,
            //#endifs
            boolean tick,
            CallbackInfo ci
    ) {
        if (Configs.unfocusedCPU.getBooleanValue() && !Minecraft.getInstance().isWindowActive()) {
            ci.cancel();
        }
    }
}
