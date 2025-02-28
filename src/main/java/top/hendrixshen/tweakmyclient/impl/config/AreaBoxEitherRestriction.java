package top.hendrixshen.tweakmyclient.impl.config;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import top.hendrixshen.magiclib.api.malilib.config.option.ConfigVec3iTupleList.Entry;
import top.hendrixshen.tweakmyclient.util.AreaBox;

public class AreaBoxEitherRestriction extends EitherUsageRestriction<Entry, AreaBox> {
    @Override
    protected AreaBox convert(Entry entry) {
        Vec3i pos1 = entry.getFirstVec3i();
        Vec3i pos2 = entry.getSecondVec3i();
        return new AreaBox(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
    }

    public boolean isAllowed(BlockPos blockPos) {
        switch (this.listType) {
            case BLACKLIST:
                return this.blackList.stream().noneMatch(areaBox -> areaBox.contains(blockPos));
            case WHITELIST:
                return this.whiteList.stream().anyMatch(areaBox -> areaBox.contains(blockPos));
            default:
                throw new IllegalStateException();
        }
    }
}
