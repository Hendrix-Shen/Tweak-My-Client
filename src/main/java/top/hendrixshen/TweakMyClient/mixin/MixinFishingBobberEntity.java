package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.hendrixshen.TweakMyClient.interfaces.IFishingBobberEntity;

@Mixin(FishingBobberEntity.class)
public class MixinFishingBobberEntity implements IFishingBobberEntity {
    @Override
    public boolean checkOpenWaterAround(BlockPos pos) {
        return isOpenOrWaterAround(pos);
    }

    @Shadow
    private boolean isOpenOrWaterAround(BlockPos pos) {
        return false;
    }
}
