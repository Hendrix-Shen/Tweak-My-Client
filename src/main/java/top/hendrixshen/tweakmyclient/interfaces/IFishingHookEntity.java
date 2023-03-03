package top.hendrixshen.tweakmyclient.interfaces;

import net.minecraft.core.BlockPos;

public interface IFishingHookEntity {
    boolean checkOpenWaterAround(BlockPos pos);
}