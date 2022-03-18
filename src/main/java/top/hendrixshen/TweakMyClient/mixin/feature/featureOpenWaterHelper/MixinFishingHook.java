package top.hendrixshen.tweakmyclient.mixin.feature.featureOpenWaterHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.hendrixshen.tweakmyclient.fakeInterface.IFishingHookEntity;

@Mixin(FishingHook.class)
public class MixinFishingHook implements IFishingHookEntity {
    @Override
    public boolean checkOpenWaterAround(BlockPos pos) {
        return calculateOpenWater(pos);
    }

    @Shadow
    private boolean calculateOpenWater(BlockPos pos) {
        return false;
    }
}
