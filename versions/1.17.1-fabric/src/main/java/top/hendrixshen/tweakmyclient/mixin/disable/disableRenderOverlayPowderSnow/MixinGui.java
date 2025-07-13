package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayPowderSnow;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.sugar.Local;

@Mixin(Gui.class)
public abstract class MixinGui {
    @Shadow
    @Final
    private static ResourceLocation POWDER_SNOW_OUTLINE_LOCATION;

    @Inject(method = "renderTextureOverlay", at = @At(value = "HEAD"), cancellable = true)
    private void onRenderPowderSnowOverlay(CallbackInfo ci, @Local(argsOnly = true) ResourceLocation resourceLocation) {
        if (Configs.disablePowderSnowOverlayRender.getBooleanValue()
                && resourceLocation.equals(MixinGui.POWDER_SNOW_OUTLINE_LOCATION)) {
            ci.cancel();
        }
    }
}
