package top.hendrixshen.tweakmyclient.mixin.patch.litematicaSchematicWailaCompat.jade;

import org.spongepowered.asm.mixin.Mixin;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;

//#if MC > 11701
import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import snownee.jade.overlay.OverlayRenderer;
import top.hendrixshen.tweakmyclient.util.JadeUtil;
//#else
//$$ import top.hendrixshen.magiclib.compat.preprocess.api.DummyClass;
//#endif

@Dependencies(
        and = {
                @Dependency("jade"),
                @Dependency("litematica")
        }
)
//#if MC > 11701
@Mixin(value = OverlayRenderer.class, remap = false)
//#else
//$$ @Mixin(DummyClass.class)
//#endif
public class MixinOverlayRenderer {
    //#if MC > 11701
    @Inject(
            //#if MC > 11901
            method = "renderOverlay478757",
            //#else
            //$$ method = "renderOverlay(Lcom/mojang/blaze3d/vertex/PoseStack;)V",
            //#endif
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
