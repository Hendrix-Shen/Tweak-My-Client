package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayPumpkin;

import top.hendrixshen.tweakmyclient.game.Configs;

// CHECKSTYLE.OFF: ImportOrder
//#if MC > 12101
//$$ import top.hendrixshen.magiclib.api.compat.minecraft.resources.ResourceLocationCompat;
//#endif
// CHECKSTYLE.ON: ImportOrder

import net.minecraft.client.gui.Gui;

// CHECKSTYLE.OFF: ImportOrder
//#if MC > 11605
//$$ import net.minecraft.resources.ResourceLocation;
//#endif
// CHECKSTYLE.ON: ImportOrder

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// CHECKSTYLE.OFF: ImportOrder
//#if 12102 > MC && MC > 11605
//$$ import org.spongepowered.asm.mixin.Final;
//$$ import org.spongepowered.asm.mixin.Shadow;
//#endif

//#if MC > 11605
//$$ import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.sugar.Local;
//#endif
// CHECKSTYLE.ON: ImportOrder

@Mixin(Gui.class)
public abstract class MixinGui {
    //#if 12102 > MC && MC > 11700
    //$$ @Shadow
    //$$ @Final
    //$$ private static ResourceLocation PUMPKIN_BLUR_LOCATION;
    //#endif

    @Inject(
            //#if MC > 11700
            //$$ method = "renderTextureOverlay",
            //#else
            method = "renderPumpkin",
            //#endif
            at = @At("HEAD"),
            cancellable = true
    )
    private void onRenderPumpkinOverlay(
            CallbackInfo ci
            // CHECKSTYLE.OFF: NoWhitespaceBefore
            // CHECKSTYLE.OFF: SeparatorWrap
            //#if MC >= 11700
            //$$ , @Local(argsOnly = true) ResourceLocation resourceLocation
            // CHECKSTYLE.ON: SeparatorWrap
            // CHECKSTYLE.ON: NoWhitespaceBefore
            //#endif
    ) {
        if (Configs.disablePumpkinOverlayRender.getBooleanValue()
                //#if MC > 11700
                //$$ && resourceLocation.equals(
                //$$         //#if MC > 12101
                //$$         //$$ ResourceLocationCompat.withDefaultNamespace("misc/pumpkinblur")
                //$$         //#else
                //$$         MixinGui.PUMPKIN_BLUR_LOCATION
                //$$         //#endif
                //$$ )
                //#endif
        ) {
            ci.cancel();
        }
    }
}
