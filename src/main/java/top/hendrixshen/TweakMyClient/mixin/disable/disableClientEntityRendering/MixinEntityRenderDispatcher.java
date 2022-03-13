package top.hendrixshen.TweakMyClient.mixin.disable.disableClientEntityRendering;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
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
                    target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;getRenderer(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/client/renderer/entity/EntityRenderer;",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void onShouldRender(Entity entity, Frustum frustum, double d, double e, double f, CallbackInfoReturnable<Boolean> cir) {
        if (Configs.Disable.DISABLE_CLIENT_ENTITY_IN_LIST_RENDERING.getBooleanValue()) {
            String entityID = Registry.ENTITY_TYPE.getKey(entity.getType()).toString();
            String entityName = entity.getName().getString();
            if (Configs.List.LIST_DISABLE_CLIENT_ENTITY_RENDERING.getStrings().stream().anyMatch(s -> entityID.contains(s) || entityName.contains(s)) && !(entity instanceof Player)) {
                cir.setReturnValue(false);
            }
        }
        if (Configs.Disable.DISABLE_CLIENT_ENTITY_TNT_RENDERING.getBooleanValue() && entity.getType() == EntityType.TNT) {
            cir.setReturnValue(false);
        }
        if (Configs.Disable.DISABLE_CLIENT_ENTITY_WITHER_RENDERING.getBooleanValue() && entity.getType() == EntityType.WITHER) {
            cir.setReturnValue(false);
        }
        if (Configs.Disable.DISABLE_CLIENT_ENTITY_ZOMBIE_VILLAGER_RENDERING.getBooleanValue() && entity.getType() == EntityType.ZOMBIE_VILLAGER) {
            cir.setReturnValue(false);
        }
    }
}
