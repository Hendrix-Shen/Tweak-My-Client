package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(InGameOverlayRenderer.class)
public interface IMixinInGameOverlayRenderer {
    @Invoker("renderFireOverlay")
    static void invokeRenderFireOverlay(MinecraftClient minecraftClient, MatrixStack matrixStack) {
        throw new AssertionError();
    }
}
