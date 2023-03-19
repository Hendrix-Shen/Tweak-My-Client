package top.hendrixshen.tweakmyclient.mixin.patch.forcePistonWithoutAffectByTool;

import org.spongepowered.asm.mixin.Mixin;
//#if MC > 11502
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.config.Configs;
//#else
//$$ import net.minecraft.client.Minecraft;
//$$ import top.hendrixshen.magiclib.compat.preprocess.api.DummyClass;
//#endif

//#if MC > 11502
@Mixin(DiggerItem.class)
//#else
//$$ @Mixin(DummyClass.class)
//#endif
public class MixinMiningToolItem {
    //#if MC > 11502
    @Inject(
            method = "getDestroySpeed",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onGetMiningSpeedMultiplier(ItemStack itemStack, BlockState blockState, CallbackInfoReturnable<Float> cir) {
        if (Configs.forcePistonWithoutAffectByTool && (blockState.getBlock() instanceof PistonBaseBlock || blockState.getBlock() instanceof MovingPistonBlock || blockState.getBlock() instanceof PistonHeadBlock)) {
            cir.setReturnValue(1.0F);
        }
    }
    //#endif
}
