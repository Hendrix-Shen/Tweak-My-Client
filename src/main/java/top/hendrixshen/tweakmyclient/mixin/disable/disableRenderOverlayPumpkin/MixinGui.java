package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayPumpkin;

import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

//#if MC > 11904
import net.minecraft.client.gui.GuiGraphics;
//#elseif MC > 11903
//$$ import com.mojang.blaze3d.vertex.PoseStack;
//#endif

@Mixin(Gui.class)
public abstract class MixinGui {
    @Shadow
    @Final
    private static ResourceLocation PUMPKIN_BLUR_LOCATION;

    @Inject(
            //#if MC > 11700
            method = "renderTextureOverlay",
            //#else
            //$$ method = "renderPumpkin",
            //#endif
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    //#if MC > 11904
    private void onRenderTextureOverlay(GuiGraphics guiGraphics, ResourceLocation resourceLocation, float f, CallbackInfo ci) {
        if (Configs.disableRenderOverlayPumpkin && resourceLocation.equals(PUMPKIN_BLUR_LOCATION)) {
    //#elseif MC > 11903
    //$$ private void onRenderTextureOverlay(PoseStack poseStack, ResourceLocation resourceLocation, float f, CallbackInfo ci) {
    //$$     if (Configs.disableRenderOverlayPumpkin && resourceLocation.equals(PUMPKIN_BLUR_LOCATION)) {
    //#elseif MC > 11700
    //$$ private void onRenderTextureOverlay(ResourceLocation resourceLocation, float f, CallbackInfo ci) {
    //$$     if (Configs.disableRenderOverlayPumpkin && resourceLocation.equals(PUMPKIN_BLUR_LOCATION)) {
    //#else
    //$$ private void onRenderPumpkinOverlay(CallbackInfo ci) {
    //$$     if (Configs.disableRenderOverlayPumpkin) {
    //#endif
            ci.cancel();
        }
    }
}
