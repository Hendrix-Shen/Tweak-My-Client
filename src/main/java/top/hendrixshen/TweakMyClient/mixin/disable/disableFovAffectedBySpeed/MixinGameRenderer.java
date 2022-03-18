package top.hendrixshen.tweakmyclient.mixin.disable.disableFovAffectedBySpeed;

import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @ModifyArgs(
            method = "getFov",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Mth;lerp(FFF)F"
            )
    )
    private void onGetFovFov(Args args) {
        if (Configs.disableFovAffectedBySpeed.getBooleanValue()) {
            args.set(1, 1.0F);
            args.set(2, 1.0F);
        }
    }
}
