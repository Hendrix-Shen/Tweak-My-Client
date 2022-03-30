package top.hendrixshen.tweakmyclient.compat.proxy.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class EntityCompatImpl extends EntityCompatApi {
    public static void initCompat() {
        INSTANCE = new EntityCompatImpl();
    }

    @Override
    public boolean onClimbable(LivingEntity entity) {
        return entity.onLadder();
    }

    @Override
    public boolean isCrouching(LivingEntity entity) {
        return entity.isVisuallySneaking();
    }

    @Override
    public float getXRot(Entity entity) {
        return entity.xRot;
    }
}
