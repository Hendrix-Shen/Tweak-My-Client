package top.hendrixshen.tweakmyclient.mixin.feature.featureOpenWaterHelper;

//#if MC < 11600
//$$ import net.minecraft.client.Minecraft;
//#else
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.FishingHook;
//#endif
import org.spongepowered.asm.mixin.Mixin;
//#if MC >= 11600
import org.spongepowered.asm.mixin.Shadow;
import top.hendrixshen.tweakmyclient.fakeInterface.IFishingHookEntity;

@Mixin(FishingHook.class)
public class MixinFishingHook implements IFishingHookEntity {
//#else
//$$ @Mixin(Minecraft.class)
//$$ public class MixinFishingHook {
//#endif
    //#if MC >= 11600
    @Override
    public boolean checkOpenWaterAround(BlockPos pos) {
        return calculateOpenWater(pos);
    }

    @Shadow
    private boolean calculateOpenWater(BlockPos pos) {
        return false;
    }
    //#endif
}
