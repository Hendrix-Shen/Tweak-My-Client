package top.hendrixshen.TweakMyClient.mixin.disable.disableClientEntityUpdates;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.hendrixshen.TweakMyClient.config.Configs;

import java.util.function.Consumer;

@Mixin(ClientLevel.class)
public class MixinClientLevel {
    @Redirect(
            method = "tickEntities",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/ClientLevel;guardEntityTick(Ljava/util/function/Consumer;Lnet/minecraft/world/entity/Entity;)V"
            )
    )
    private void onTickEntity(ClientLevel instance, Consumer<Entity> consumer, Entity entity) {
        if (Configs.Disable.DISABLE_CLIENT_ENTITY_IN_LIST_UPDATES.getBooleanValue()) {
            String entityID = Registry.ENTITY_TYPE.getKey(entity.getType()).toString();
            String entityName = entity.getName().getString();
            if (Configs.List.LIST_DISABLE_CLIENT_ENTITY_UPDATES.getStrings().stream().anyMatch(s -> entityID.contains(s) || entityName.contains(s)) && !(entity instanceof Player)) {
                return;
            }
        }
        if (Configs.Disable.DISABLE_CLIENT_ENTITY_TNT_UPDATES.getBooleanValue() && entity.getType() == EntityType.TNT) {
            return;
        }
        if (Configs.Disable.DISABLE_CLIENT_ENTITY_WITHER_UPDATES.getBooleanValue() && entity.getType() == EntityType.WITHER) {
            return;
        }
        if (Configs.Disable.DISABLE_CLIENT_ENTITY_ZOMBIE_VILLAGER_UPDATES.getBooleanValue() && entity.getType() == EntityType.ZOMBIE_VILLAGER) {
            return;
        }
        instance.guardEntityTick(consumer, entity);
    }
}
