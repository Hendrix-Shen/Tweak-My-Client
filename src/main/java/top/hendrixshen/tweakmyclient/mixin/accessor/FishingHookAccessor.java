package top.hendrixshen.tweakmyclient.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;

//#if MC > 11502
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.gen.Invoker;
//#else
//$$ import top.hendrixshen.magiclib.compat.preprocess.api.DummyClass;
//#endif

//#if MC > 11502
@Mixin(FishingHook.class)
//#else
//$$ @Mixin(DummyClass.class)
//#endif
public interface FishingHookAccessor {
    //#if MC > 11502
    @Invoker
    boolean invokeCalculateOpenWater(BlockPos blockPos);
    //#endif
}
