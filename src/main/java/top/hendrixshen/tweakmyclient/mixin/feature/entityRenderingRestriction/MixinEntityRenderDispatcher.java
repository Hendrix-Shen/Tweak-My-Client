package top.hendrixshen.tweakmyclient.mixin.feature.entityRenderingRestriction;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.game.Configs;

//#if MC > 11404
import net.minecraft.client.renderer.culling.Frustum;
//#else
//$$ import net.minecraft.client.renderer.culling.Culler;
//#endif

@Mixin(EntityRenderDispatcher.class)
public abstract class MixinEntityRenderDispatcher {
    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void onShouldRender(
            Entity entity,
            //#if MC > 11404
            Frustum frustum,
            //#else
            //$$ Culler culler,
            //#endif
            double d,
            double e,
            double f,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (!Configs.entityRenderingRestriction.getBooleanValue()
                || entity instanceof Player
                || Configs.entityRenderingRestrictionList.isAllowed(entity)
        ) {
            return;
        }

        cir.setReturnValue(false);
    }
}
