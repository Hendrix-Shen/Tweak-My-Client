package top.hendrixshen.tweakmyclient.mixin.feature.unfocusedCPU;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {
    //#if MC >= 26.1
    //$$ @Inject(method = "extract", at = @At("HEAD"), cancellable = true)
    //$$ private void onExtract(CallbackInfo ci) {
    //$$     if (Configs.unfocusedCPU.getBooleanValue() && !Minecraft.getInstance().isWindowActive()) {
    //$$         ci.cancel();
    //$$     }
    //$$ }
    //$$
    //#endif

    @Inject(
            //#if MC > 11404
            method = "render",
            //#else
            //$$ method = "render(FJZ)V",
            //#endif
            at = @At("HEAD"),
            cancellable = true
    )
    private void onRenderHead(CallbackInfo ci) {
        if (Configs.unfocusedCPU.getBooleanValue() && !Minecraft.getInstance().isWindowActive()) {
            ci.cancel();
        }
    }
}
