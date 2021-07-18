package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.hendrixshen.TweakMyClient.TweakMyClient;
import top.hendrixshen.TweakMyClient.config.Configs;

import java.util.function.Consumer;

@Mixin(ClientWorld.class)
public class MixinClientWorld {
    @Redirect(
            method = "tickEntities",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/world/ClientWorld;tickEntity(Ljava/util/function/Consumer;Lnet/minecraft/entity/Entity;)V"
            )
    )
    private void onTickEntity(ClientWorld clientWorld, Consumer<Entity> tickConsumer, Entity entity) {
        if (Configs.Disable.DISABLE_CLIENT_ENTITY_IN_LIST_UPDATES.getBooleanValue()) {
            String entityID = Registry.ENTITY_TYPE.getId(entity.getType()).toString();
            String entityName = entity.getName().getString();
            if (Configs.List.LIST_DISABLE_CLIENT_ENTITY_UPDATES.getStrings().stream().anyMatch(s -> entityID.contains(s) || entityName.contains(s)) && !(entity instanceof PlayerEntity)) {
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
        clientWorld.tickEntity(tickConsumer, entity);
    }
}
