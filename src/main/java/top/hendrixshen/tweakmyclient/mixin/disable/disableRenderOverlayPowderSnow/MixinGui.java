package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayPowderSnow;

import org.spongepowered.asm.mixin.Mixin;

//#if MC > 11605
//#if MC > 11904
import net.minecraft.client.gui.GuiGraphics;
//#elseif MC > 11503
//$$ import com.mojang.blaze3d.vertex.PoseStack;
//#endif
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;
//#else
//$$ import top.hendrixshen.magiclib.compat.preprocess.api.DummyClass;
//#endif

//#if MC > 11605
@Mixin(Gui.class)
//#else
//$$ @Mixin(DummyClass.class)
//#endif
public abstract class MixinGui {
    //#if MC > 11605
    @Shadow
    @Final
    private static ResourceLocation POWDER_SNOW_OUTLINE_LOCATION;

    @Inject(
            method = "renderTextureOverlay",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    //#if MC > 11904
    private void onRenderPowderSnowOverlay(GuiGraphics guiGraphics, ResourceLocation resourceLocation, float f, CallbackInfo ci) {
    //#elseif MC > 11903
    //$$ private void onRenderPowderSnowOverlay(PoseStack poseStack, ResourceLocation resourceLocation, float f, CallbackInfo ci) {
    //#else
    //$$ private void onRenderPowderSnowOverlay(ResourceLocation resourceLocation, float f, CallbackInfo ci) {
    //#endif
        if (Configs.disableRenderOverlayPowderSnow && resourceLocation.equals(POWDER_SNOW_OUTLINE_LOCATION)) {
            ci.cancel();
        }
    }
    //#endif
}
