package top.hendrixshen.tweakmyclient.mixin.disable.disableItemGlowing;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.helper.Cache;

//#if MC > 11902
import net.minecraft.core.registries.BuiltInRegistries;
//#else
//$$ import net.minecraft.core.Registry;
//#endif

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract Component getDisplayName();

    @Inject(
            method = "hasFoil",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void isFoil(CallbackInfoReturnable<Boolean> cir) {
        if (Configs.disableItemGlowing) {
            //#if MC > 11902
            String itemStackID = BuiltInRegistries.ITEM.getKey(this.getItem()).toString();
            //#else
            //$$ String itemStackID = Registry.ITEM.getKey(this.getItem()).toString();
            //#endif
            String itemStackName = this.getDisplayName().getString();
            if (Cache.getInstance().getItemGlowingBlackList().contains(this.getItem()) ||
                    Configs.listItemGlowingBlacklist.stream().anyMatch((s -> itemStackID.contains(s) || itemStackName.contains(s)))) {
                cir.setReturnValue(false);
            }
        }
    }
}
