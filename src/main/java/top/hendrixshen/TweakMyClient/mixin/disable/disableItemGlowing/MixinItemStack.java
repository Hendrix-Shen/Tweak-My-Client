package top.hendrixshen.TweakMyClient.mixin.disable.disableItemGlowing;

import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
    @Shadow public abstract Item getItem();

    @Shadow public abstract Component getDisplayName();

    @Inject(
            method = "hasFoil",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void isFoil(CallbackInfoReturnable<Boolean> cir) {
        if (Configs.Disable.DISABLE_ITEM_GLOWING.getBooleanValue()) {
            String itemStackID = Registry.ITEM.getKey(this.getItem()).toString();
            String itemStackName = this.getDisplayName().getString();
            if (Configs.getItemGlowingBlackList().contains(this.getItem()) ||
                    Configs.List.LIST_DISABLE_ITEM_GLOWING.getStrings().stream().anyMatch((s -> itemStackID.contains(s) || itemStackName.contains(s)))) {
                cir.setReturnValue(false);
            }
        }
    }
}
