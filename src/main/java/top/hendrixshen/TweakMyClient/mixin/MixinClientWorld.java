package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(ClientWorld.class)
public abstract class MixinClientWorld {
    @Inject(
            method = "tickEntity",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onTickEntity(Entity entity, CallbackInfo ci) {
        if (Configs.Disable.DISABLE_CLIENT_ENTITY_IN_LIST_UPDATES.getBooleanValue()) {
            String entityName = entity.getType().getTranslationKey().split("\\.")[2];
            if (Configs.List.LIST_DISABLE_CLIENT_ENTITY_UPDATES.getStrings().contains(entityName) && !(entity instanceof PlayerEntity)) {
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
