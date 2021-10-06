package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(MiningToolItem.class)
public class MixinMiningToolItem {
    @Inject(
            method = "getMiningSpeedMultiplier",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onGetMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
        if (Configs.Patch.FORCE_PISTON_WITHOUT_AFFECT_BY_TOOL.getBooleanValue() && (state.getBlock() instanceof PistonBlock || state.getBlock() instanceof PistonExtensionBlock || state.getBlock() instanceof PistonHeadBlock)) {
            cir.setReturnValue(1.0F);
        }
    }
}
