package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayPowderSnow;

import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.game.Configs;

//#if MC > 11904
//$$ import net.minecraft.client.gui.GuiGraphics;
//#elseif MC > 11503
//$$ import com.mojang.blaze3d.vertex.PoseStack;
//#endif

@Mixin(Gui.class)
public abstract class MixinGui {
    @Shadow
    @Final
    private static ResourceLocation POWDER_SNOW_OUTLINE_LOCATION;

    @Inject(method = "renderTextureOverlay", at = @At(value = "HEAD"), cancellable = true)
    private void onRenderPowderSnowOverlay(
            //#if MC > 11904
            //$$ GuiGraphics guiGraphics,
            //#elseif MC > 11903
            //$$ PoseStack poseStack,
            //#endif
            ResourceLocation resourceLocation,
            float alpha,
            CallbackInfo ci
    ) {
        if (Configs.disablePowderSnowOverlayRender.getBooleanValue()
                && resourceLocation.equals(MixinGui.POWDER_SNOW_OUTLINE_LOCATION)) {
            ci.cancel();
        }
    }
}
