package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.tag.FluidTags;
import org.objectweb.asm.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;

import static org.objectweb.asm.Opcodes.*;

@Mixin(value = BackgroundRenderer.class, priority = 500)
public class MixinBackgroundRenderer {
    @Inject(
            method = "applyFog",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/tag/Tag;)Z"
            ),
            cancellable = true
    )
    private static void adjustLavaFog1(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci) {
        if (Configs.Disable.DISABLE_RENDER_FOG_LAVA.getBooleanValue() && camera.getSubmergedFluidState().isIn(FluidTags.LAVA)) {
        }
        //ci.cancel();
    }
    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/tag/Tag;)Z"
            ),
            cancellable = true
    )
    private static void adjustLavaFog2(Camera camera, float tickDelta, ClientWorld world, int i, float f, CallbackInfo ci) {
        if (Configs.Disable.DISABLE_RENDER_FOG_LAVA.getBooleanValue() && camera.getSubmergedFluidState().isIn(FluidTags.LAVA)) {
        }
        //ci.cancel();
    }
    @ModifyVariable(
            method = "applyFog",
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/entity/effect/StatusEffects;FIRE_RESISTANCE:Lnet/minecraft/entity/effect/StatusEffect;"
                    ),
                    to = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/entity/effect/StatusEffects;BLINDNESS:Lnet/minecraft/entity/effect/StatusEffect;"
                    )
            ),
            at = @At(
                    value = "STORE"
            )
    )
    private static float adjustLavaFog3(float x) {
        return 100.0F;
    }
    @ModifyVariable(
            method = "applyFog",
            at = @At(
                    value = "STORE"
            )
    )
    private static float adjustLavaFog4(float x) {
        return x;
    }
}
