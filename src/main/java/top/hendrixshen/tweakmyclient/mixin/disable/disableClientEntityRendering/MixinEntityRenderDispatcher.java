package top.hendrixshen.tweakmyclient.mixin.disable.disableClientEntityRendering;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.config.Configs;

//#if MC > 11404
import net.minecraft.client.renderer.culling.Frustum;
//#else
//$$ import net.minecraft.client.renderer.culling.Culler;
//#endif

//#if MC > 11902
import net.minecraft.core.registries.BuiltInRegistries;
//#else
//$$ import net.minecraft.core.Registry;
//#endif

@Mixin(EntityRenderDispatcher.class)
public abstract class MixinEntityRenderDispatcher {
    @Inject(
            method = "shouldRender",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    //#if MC > 11404
    private void onShouldRender(Entity entity, Frustum frustum, double d, double e, double f, CallbackInfoReturnable<Boolean> cir) {
    //#else
    //$$ private void onShouldRender(Entity entity, Culler culler, double d, double e, double f, CallbackInfoReturnable<Boolean> cir) {
    //#endif
        if (Configs.disableClientEntityInListRendering) {
            //#if MC > 11902
            String entityID = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
            //#else
            //$$ String entityID = Registry.ENTITY_TYPE.getKey(entity.getType()).toString();
            //#endif
            String entityName = entity.getName().getString();

            if (Configs.listDisableClientEntityRendering.stream().anyMatch(s -> entityID.contains(s) || entityName.contains(s)) && !(entity instanceof Player)) {
                cir.setReturnValue(false);
            }
        }

        if ((Configs.disableClientEntityTNTUpdates && entity.getType() == EntityType.TNT)
                || (Configs.disableClientEntityWitherUpdates && entity.getType() == EntityType.WITHER)
                || (Configs.disableClientEntityZombieVillagerUpdates && entity.getType() == EntityType.ZOMBIE_VILLAGER)) {
            cir.setReturnValue(false);
        }
    }
}
