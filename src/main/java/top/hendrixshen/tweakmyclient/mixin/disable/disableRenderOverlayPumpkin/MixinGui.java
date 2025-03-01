package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayPumpkin;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.game.Configs;

//#if MC > 11904
//$$ import net.minecraft.client.gui.GuiGraphics;
//#elseif MC > 11903
//$$ import com.mojang.blaze3d.vertex.PoseStack;
//#endif

//#if MC > 11605
//$$ import net.minecraft.resources.ResourceLocation;
//$$ import org.spongepowered.asm.mixin.Final;
//$$ import org.spongepowered.asm.mixin.Shadow;
//#endif

@Mixin(Gui.class)
public abstract class MixinGui {
    //#if MC > 11700
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
            //#if MC > 11904
            //$$ GuiGraphics guiGraphics,
            //#elseif MC > 11903
            //$$ PoseStack poseStack,
            //#endif
            //#if MC > 11700
            //$$ ResourceLocation resourceLocation,
            //$$ float alpha,
            //#endif
            CallbackInfo ci
    ) {
        if (Configs.disablePumpkinOverlayRender.getBooleanValue()
                //#if MC > 11700
                //$$ && resourceLocation.equals(MixinGui.PUMPKIN_BLUR_LOCATION)
                //#endif
        ) {
            ci.cancel();
        }
    }
}
