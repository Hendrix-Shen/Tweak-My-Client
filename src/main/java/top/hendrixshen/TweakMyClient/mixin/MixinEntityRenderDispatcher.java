package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(EntityRenderDispatcher.class)
public abstract class MixinEntityRenderDispatcher {
    @Inject(
            method = "shouldRender",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/EntityRenderDispatcher;getRenderer(Lnet/minecraft/entity/Entity;)Lnet/minecraft/client/render/entity/EntityRenderer;",
                    shift = At.Shift.BEFORE
            ),

            cancellable = true
    )
    private void onShouldRender(Entity entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        if (Configs.Disable.DISABLE_CLIENT_ENTITY_IN_LIST_RENDERING.getBooleanValue()) {
            String entityID = Registry.ENTITY_TYPE.getId(entity.getType()).toString();
            String entityName = entity.getName().getString();
            if (Configs.List.LIST_DISABLE_CLIENT_ENTITY_RENDERING.getStrings().stream().anyMatch(s -> entityID.contains(s) || entityName.contains(s)) && !(entity instanceof PlayerEntity)) {
                cir.setReturnValue(false);
            }
        }
        if (Configs.Disable.DISABLE_ENTITY_TNT_RENDERING.getBooleanValue() && entity.getType() == EntityType.TNT) {
            cir.setReturnValue(false);
        }
        if (Configs.Disable.DISABLE_ENTITY_WITHER_RENDERING.getBooleanValue() && entity.getType() == EntityType.WITHER) {
            cir.setReturnValue(false);
        }
        if (Configs.Disable.DISABLE_ENTITY_ZOMBIE_VILLAGER_RENDERING.getBooleanValue() && entity.getType() == EntityType.ZOMBIE_VILLAGER) {
            cir.setReturnValue(false);
        }
    }
}
