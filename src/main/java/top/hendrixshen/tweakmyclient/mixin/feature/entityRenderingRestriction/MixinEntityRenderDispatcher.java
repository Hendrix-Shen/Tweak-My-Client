package top.hendrixshen.tweakmyclient.mixin.feature.entityRenderingRestriction;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.sugar.Local;

@Mixin(EntityRenderDispatcher.class)
public abstract class MixinEntityRenderDispatcher {
    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void onShouldRender(CallbackInfoReturnable<Boolean> cir, @Local(argsOnly = true) Entity entity) {
        if (!Configs.entityRenderingRestriction.getBooleanValue()
                || entity instanceof Player
                || Configs.entityRenderingRestrictionList.isAllowed(entity)
        ) {
            return;
        }

        cir.setReturnValue(false);
    }
}
