package top.hendrixshen.tweakmyclient.mixin.disable.disableClientEntityRendering;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.helper.CommonCompatLib;

@Dependencies(and = @Dependency(value = "minecraft", versionPredicate = ">=1.15"))
@Mixin(EntityRenderDispatcher.class)
public abstract class MixinEntityRenderDispatcher {
    @Inject(
            method = "shouldRender",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onShouldRender(Entity entity, Frustum frustum, double d, double e, double f, CallbackInfoReturnable<Boolean> cir) {
        if (CommonCompatLib.disableClientEntity(entity)) {
            cir.cancel();
        }
    }
}
