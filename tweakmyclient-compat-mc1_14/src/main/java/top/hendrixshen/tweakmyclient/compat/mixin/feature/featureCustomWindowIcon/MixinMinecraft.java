package top.hendrixshen.tweakmyclient.compat.mixin.feature.featureCustomWindowIcon;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.util.CustomWindowUtil;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow
    public Window window;

    @Inject(
            method = "run",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;init()V",
                    shift = At.Shift.AFTER
            )
    )
    private void afterInit(CallbackInfo ci) {
        CustomWindowUtil.updateIcon(this.window);
    }
}
