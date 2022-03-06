package top.hendrixshen.TweakMyClient.mixin.disable.disableClientEntityUpdates;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;

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
        if (Configs.Disable.DISABLE_CLIENT_ENTITY_IN_LIST_UPDATES.getBooleanValue()) {
            String entityID = Registry.ENTITY_TYPE.getKey(entity.getType()).toString();
            String entityName = entity.getName().getString();
            if (Configs.List.LIST_DISABLE_CLIENT_ENTITY_UPDATES.getStrings().stream().anyMatch(s -> entityID.contains(s) || entityName.contains(s)) && !(entity instanceof Player)) {
                ci.cancel();
            }
        }
        if (Configs.Disable.DISABLE_CLIENT_ENTITY_TNT_UPDATES.getBooleanValue() && entity.getType() == EntityType.TNT) {
            ci.cancel();
        }
        if (Configs.Disable.DISABLE_CLIENT_ENTITY_WITHER_UPDATES.getBooleanValue() && entity.getType() == EntityType.WITHER) {
            ci.cancel();
        }
        if (Configs.Disable.DISABLE_CLIENT_ENTITY_ZOMBIE_VILLAGER_UPDATES.getBooleanValue() && entity.getType() == EntityType.ZOMBIE_VILLAGER) {
            ci.cancel();
        }
    }
}
