package top.hendrixshen.tweakmyclient.mixin.patch.forcePistonWithoutAffectByTool;

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
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;

@Dependencies(and = @Dependency(value = "minecraft", versionPredicate = ">=1.16"))
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
        if (Configs.forcePistonWithoutAffectByTool && (blockState.getBlock() instanceof PistonBaseBlock || blockState.getBlock() instanceof MovingPistonBlock || blockState.getBlock() instanceof PistonHeadBlock)) {
            cir.setReturnValue(1.0F);
        }
    }
}
