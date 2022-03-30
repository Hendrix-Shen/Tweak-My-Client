package top.hendrixshen.tweakmyclient.compat.proxy.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class EntityCompatImpl extends EntityCompatApi {
    public static void initCompat() {
        INSTANCE = new EntityCompatImpl();
    }

    @Override
    public boolean onClimbable(LivingEntity entity) {
        return entity.onClimbable();
    }

    @Override
    public boolean isCrouching(LivingEntity entity) {
        return entity.isCrouching();
    }

    @Override
    public float getXRot(Entity entity) {
        return entity.xRot;
    }
}
