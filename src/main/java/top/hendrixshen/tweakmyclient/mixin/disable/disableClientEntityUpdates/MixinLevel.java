package top.hendrixshen.tweakmyclient.mixin.disable.disableClientEntityUpdates;

import net.minecraft.core.Registry;
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

@Mixin(Level.class)
public class MixinLevel {
    @Inject(
            method = "guardEntityTick",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onTickEntity(Consumer<Entity> consumer, Entity entity, CallbackInfo ci) {
        if (Configs.disableClientEntityInListUpdates.getBooleanValue()) {
            String entityID = Registry.ENTITY_TYPE.getKey(entity.getType()).toString();
            String entityName = entity.getName().getString();
            if (Configs.listDisableClientEntityUpdates.getStrings().stream().anyMatch(s -> entityID.contains(s) || entityName.contains(s)) && !(entity instanceof Player)) {
                ci.cancel();
            }
        }
        if (Configs.disableClientEntityTNTRendering.getBooleanValue() && entity.getType() == EntityType.TNT) {
            ci.cancel();
        }
        if (Configs.disableClientEntityWitherRendering.getBooleanValue() && entity.getType() == EntityType.WITHER) {
            ci.cancel();
        }
        if (Configs.disableClientEntityZombieVillagerRendering.getBooleanValue() && entity.getType() == EntityType.ZOMBIE_VILLAGER) {
            ci.cancel();
        }
    }
}
