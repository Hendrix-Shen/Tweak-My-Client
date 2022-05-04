package top.hendrixshen.tweakmyclient.mixin.patch.forcePistonWithoutAffectByTool;

//#if MC < 11600
//$$ import net.minecraft.client.Minecraft;
//#else
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
//#endif
import org.spongepowered.asm.mixin.Mixin;
//#if MC >= 11600
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(DiggerItem.class)
//#else
//$$ @Mixin(Minecraft.class)
//#endif
public class MixinMiningToolItem {
    //#if MC >= 11600
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
