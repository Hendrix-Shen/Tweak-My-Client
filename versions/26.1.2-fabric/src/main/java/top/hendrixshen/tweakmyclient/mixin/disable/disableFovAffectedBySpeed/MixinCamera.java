package top.hendrixshen.tweakmyclient.mixin.disable.disableFovAffectedBySpeed;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.Camera;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc1.21.11: subproject 1.16.5 (main project) [dummy]</li>
 * <li>mc26.1+           : subproject 26.1        &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(Camera.class)
public class MixinCamera {
    @WrapOperation(
            method = "calculateFov",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Mth;lerp(FFF)F"
            )
    )
    private float onGetFov(float partialTicks, float oldFov, float fov, Operation<Float> original) {
        if (Configs.disableFovAffectedBySpeed.getBooleanValue()) {
            return 1.0F;
        }

        return original.call(partialTicks, oldFov, fov);
    }
}
