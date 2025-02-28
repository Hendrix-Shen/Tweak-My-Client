package top.hendrixshen.tweakmyclient.mixin.disable.disableItemGlowing;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.magiclib.util.MiscUtil;
import top.hendrixshen.tweakmyclient.game.Configs;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
    @Inject(method = "hasFoil", at = @At("HEAD"), cancellable = true)
    private void isFoil(CallbackInfoReturnable<Boolean> cir) {
        if (!Configs.itemGlintRestriction.getBooleanValue() ||
                Configs.itemGlintRestrictionList.isAllowed(MiscUtil.cast(this))) {
            return;
        }

        cir.setReturnValue(false);
    }
}
