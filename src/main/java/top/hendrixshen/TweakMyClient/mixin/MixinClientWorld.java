package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
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
        if (Configs.Disable.DISABLE_CLIENT_ENTITY_TNT_UPDATES.getBooleanValue() && entity instanceof TntEntity) {
            return;
        }
        if (Configs.Disable.DISABLE_CLIENT_ENTITY_WITHER_UPDATES.getBooleanValue() && entity instanceof WitherEntity) {
            return;
        }
        if (Configs.Disable.DISABLE_CLIENT_ENTITY_ZOMBIE_VILLAGER_UPDATES.getBooleanValue() && entity instanceof ZombieVillagerEntity) {
            return;
        }
        clientWorld.tickEntity(tickConsumer, entity);
    }
}
