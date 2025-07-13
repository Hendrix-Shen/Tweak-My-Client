package top.hendrixshen.tweakmyclient.mixin.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.FishingHook;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc1.15: subproject 1.15.2 [dummy]</li>
 * <li>mc1.16+        : subproject 1.16.5 (main project)        &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(FishingHook.class)
public interface FishingHookAccessor {
    @Invoker("calculateOpenWater")
    boolean tmc$invokeCalculateOpenWater(BlockPos blockPos);
}
