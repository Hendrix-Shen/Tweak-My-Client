package top.hendrixshen.tweakmyclient.mixin.patch.litematicaSchematicWailaCompat.jade;

import org.spongepowered.asm.mixin.Mixin;
//#if MC >= 11802
import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import snownee.jade.overlay.OverlayRenderer;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.util.JadeUtil;
//#else
//$$ import net.minecraft.client.Minecraft;
//#endif

//#if MC >= 11802
@Dependencies(
        and = {
                @Dependency("jade"),
                @Dependency("litematica")
        }
)
@Mixin(value = OverlayRenderer.class, remap = false)
//#else
//$$ @Mixin(Minecraft.class)
//#endif
public class MixinOverlayRenderer {
    //#if MC >= 11802
    @Inject(
            method = "renderOverlay(Lcom/mojang/blaze3d/vertex/PoseStack;)V",
            at = @At(
                    value = "HEAD"
            ),
            remap = true,
            cancellable = true
    )
    private static void onRenderOverlay(PoseStack poseStack, CallbackInfo ci) {
        if (JadeUtil.shouldDisableJadeRender()) {
            JadeUtil.render(poseStack);
            ci.cancel();
        }
    }
    //#endif
}
