package top.hendrixshen.tweakmyclient.compat.proxy.entity;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public abstract class EntityCompatApi {
    protected static EntityCompatApi INSTANCE;

    public static EntityCompatApi getInstance() {
        return INSTANCE;
    }

    public abstract boolean onClimbable(LivingEntity entity);

    public abstract boolean isCrouching(LivingEntity entity);

    public abstract float getXRot(Entity entity);
}
