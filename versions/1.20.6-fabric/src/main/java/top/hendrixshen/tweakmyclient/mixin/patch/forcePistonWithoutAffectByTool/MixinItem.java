package top.hendrixshen.tweakmyclient.mixin.patch.forcePistonWithoutAffectByTool;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc1.20.4: subproject 1.16.5 (main project) [dummy]</li>
 * <li>mc1.20.5+        : subproject 1.20.6        &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(Item.class)
public abstract class MixinItem {
    @Inject(method = "getDestroySpeed", at = @At("HEAD"), cancellable = true)
    private void onGetMiningSpeedMultiplier(ItemStack itemStack, BlockState blockState, CallbackInfoReturnable<Float> cir) {
        if (!Configs.forcePistonWithoutAffectByTool.getBooleanValue()) {
            return;
        }

        if (blockState.getBlock() instanceof PistonBaseBlock
                || blockState.getBlock() instanceof MovingPistonBlock
                || blockState.getBlock() instanceof PistonHeadBlock) {
            cir.setReturnValue(1.0F);
        }
    }
}
