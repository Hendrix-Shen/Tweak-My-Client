package top.hendrixshen.tweakmyclient.compat.mixin.disable.disableClientEntityRendering;

import net.minecraft.client.renderer.culling.Culler;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.helper.CommonCompatLib;

@Mixin(EntityRenderDispatcher.class)
public abstract class MixinEntityRenderDispatcher {
    @Inject(
            method = "shouldRender",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onShouldRender(Entity entity, Culler culler, double d, double e, double f, CallbackInfoReturnable<Boolean> cir) {
        if (CommonCompatLib.disableClientEntity(entity)) {
            cir.cancel();
        }
    }
}
