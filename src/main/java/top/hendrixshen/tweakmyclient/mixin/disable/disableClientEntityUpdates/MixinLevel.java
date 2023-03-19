package top.hendrixshen.tweakmyclient.mixin.disable.disableClientEntityUpdates;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

import java.util.function.Consumer;

//#if MC > 11902
import net.minecraft.core.registries.BuiltInRegistries;
//#else
//$$ import net.minecraft.core.Registry;
//#endif

@Mixin(Level.class)
public class MixinLevel {
    @Inject(
            method = "guardEntityTick",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onGuardEntityTick(Consumer<Entity> consumer, Entity entity, CallbackInfo ci) {
        if (Configs.disableClientEntityInListUpdates) {
            //#if MC > 11902
            String entityID = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
            //#else
            //$$ String entityID = Registry.ENTITY_TYPE.getKey(entity.getType()).toString();
            //#endif
            String entityName = entity.getName().getString();

            if (Configs.listDisableClientEntityUpdates.stream().anyMatch(s -> entityID.contains(s) ||
                    entityName.contains(s)) && !(entity instanceof Player)) {
                ci.cancel();
            }
        }

        if ((Configs.disableClientEntityTNTRendering && entity.getType() == EntityType.TNT) ||
                (Configs.disableClientEntityWitherRendering && entity.getType() == EntityType.WITHER) ||
                (Configs.disableClientEntityZombieVillagerRendering && entity.getType() == EntityType.ZOMBIE_VILLAGER)) {
            ci.cancel();
        }
    }
}
