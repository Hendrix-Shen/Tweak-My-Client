package top.hendrixshen.tweakmyclient.mixin.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FishingHook.class)
public interface FishingHookAccessor {
    @Invoker("calculateOpenWater")
    boolean tmc$invokeCalculateOpenWater(BlockPos blockPos);
}
