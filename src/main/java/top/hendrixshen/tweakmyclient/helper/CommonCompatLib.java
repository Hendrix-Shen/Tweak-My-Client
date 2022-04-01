package top.hendrixshen.tweakmyclient.helper;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Registry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

public class CommonCompatLib {
    public static void disableSlowdown(Entity entity, CallbackInfo ci) {
        if (Configs.disableSlowdown && entity instanceof LocalPlayer) {
            Vec3 vec3 = entity.getDeltaMovement();
            if (vec3.y < 0 && vec3.y > -0.0792) { // Vertical momentum at 2x steady state.
                entity.setDeltaMovement(vec3.x, 0, vec3.z);
                ci.cancel();
            }
        }
    }

    public static boolean disableClientEntity(Entity entity) {
        if (Configs.disableClientEntityInListRendering) {
            String entityID = Registry.ENTITY_TYPE.getKey(entity.getType()).toString();
            String entityName = entity.getName().getString();
            if (Configs.listDisableClientEntityRendering.stream().anyMatch(s -> entityID.contains(s) || entityName.contains(s)) && !(entity instanceof Player)) {
                return true;
            }
        }

        return (Configs.disableClientEntityTNTUpdates && entity.getType() == EntityType.TNT)
                || (Configs.disableClientEntityWitherUpdates && entity.getType() == EntityType.WITHER)
                || (Configs.disableClientEntityZombieVillagerUpdates && entity.getType() == EntityType.ZOMBIE_VILLAGER);
    }

    public static boolean disableAttackEntity(HitResult hitResult, Player player) {
        Entity entity = ((EntityHitResult) hitResult).getEntity();
        String entityID = Registry.ENTITY_TYPE.getKey(entity.getType()).toString();
        String entityName = entity.getName().getString();
        if (Configs.disableAttackEntity && Configs.listDisableAttackEntity.stream().anyMatch(s -> entityID.contains(s) || entityName.contains(s))) {
            player.swing(InteractionHand.MAIN_HAND);
            return true;
        }
        return false;
    }
}
