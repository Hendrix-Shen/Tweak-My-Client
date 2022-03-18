package top.hendrixshen.tweakmyclient.mixin.disable.disableClientEntityRendering;

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
import top.hendrixshen.tweakmyclient.config.Configs;

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
        if (Configs.disableClientEntityInListRendering.getBooleanValue()) {
            String entityID = Registry.ENTITY_TYPE.getKey(entity.getType()).toString();
            String entityName = entity.getName().getString();
            if (Configs.listDisableClientEntityRendering.getStrings().stream().anyMatch(s -> entityID.contains(s) || entityName.contains(s)) && !(entity instanceof Player)) {
                cir.setReturnValue(false);
            }
        }
        if (Configs.disableClientEntityTNTUpdates.getBooleanValue() && entity.getType() == EntityType.TNT) {
            cir.setReturnValue(false);
        }
        if (Configs.disableClientEntityWitherUpdates.getBooleanValue() && entity.getType() == EntityType.WITHER) {
            cir.setReturnValue(false);
        }
        if (Configs.disableClientEntityZombieVillagerUpdates.getBooleanValue() && entity.getType() == EntityType.ZOMBIE_VILLAGER) {
            cir.setReturnValue(false);
        }
    }
}
