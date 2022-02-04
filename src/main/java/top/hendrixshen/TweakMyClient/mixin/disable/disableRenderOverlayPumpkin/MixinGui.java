package top.hendrixshen.TweakMyClient.mixin.disable.disableRenderOverlayPumpkin;

import net.minecraft.client.gui.Gui;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(Gui.class)
public abstract class MixinGui {
    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private boolean onRenderPumpkinOverlay(ItemStack instance, Item item) {
        if (!Configs.Disable.DISABLE_RENDER_OVERLAY_PUMPKIN.getBooleanValue()) {
            return instance.is(item);
        }
        return false;
    }
}
