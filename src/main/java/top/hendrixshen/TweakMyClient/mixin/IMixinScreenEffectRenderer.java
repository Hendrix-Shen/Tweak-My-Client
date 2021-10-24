package top.hendrixshen.TweakMyClient.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ScreenEffectRenderer.class)
public interface IMixinScreenEffectRenderer {
    @Invoker("renderFire")
    static void invokeRenderFireOverlay(Minecraft minecraft, PoseStack poseStack) {
        throw new AssertionError();
    }
}
