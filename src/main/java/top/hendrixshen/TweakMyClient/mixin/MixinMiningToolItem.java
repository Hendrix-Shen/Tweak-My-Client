package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(DiggerItem.class)
public class MixinMiningToolItem {
    @Inject(
            method = "getDestroySpeed",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onGetMiningSpeedMultiplier(ItemStack itemStack, BlockState blockState, CallbackInfoReturnable<Float> cir) {
        if (Configs.Patch.FORCE_PISTON_WITHOUT_AFFECT_BY_TOOL.getBooleanValue() && (blockState.getBlock() instanceof PistonBaseBlock || blockState.getBlock() instanceof MovingPistonBlock || blockState.getBlock() instanceof PistonHeadBlock)) {
            cir.setReturnValue(1.0F);
        }
    }
}
