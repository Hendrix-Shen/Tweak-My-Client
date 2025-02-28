package top.hendrixshen.tweakmyclient.mixin.disable.disableFovAffectedBySpeed;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.tweakmyclient.game.Configs;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {
    @WrapOperation(
            method = "getFov",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Mth;lerp(FFF)F"
            )
    )
    private float onGetFovFov(float partialTicks, float oldFov, float fov, Operation<Float> original) {
        if (Configs.disableFovAffectedBySpeed.getBooleanValue()) {
            return 1.0F;
        }

        return original.call(partialTicks, oldFov, fov);
    }
}
