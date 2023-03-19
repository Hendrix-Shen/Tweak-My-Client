package top.hendrixshen.tweakmyclient.mixin.feature.featureAutoTotem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer extends LivingEntity {
    protected MixinLocalPlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onTick(CallbackInfo ci) {
        if (Configs.featureAutoTotem) {
            Minecraft mc = TweakMyClient.getMinecraftClient();

            if (mc.player == null || mc.player.getOffhandItem().isCompat(Items.TOTEM_OF_UNDYING) || mc.gameMode == null ||
                    mc.screen instanceof AbstractContainerScreen && !(mc.screen instanceof InventoryScreen)) {
                return;
            }

            Inventory inv = mc.player.getInventory();

            for (int slot = 9; slot < 45; slot++) {
                if (inv.getItem(slot).isCompat(Items.TOTEM_OF_UNDYING)) {
                    MultiPlayerGameMode multiPlayerGameMode = mc.gameMode;
                    boolean isOffhandEmpty = mc.player.getOffhandItem().isEmpty();
                    multiPlayerGameMode.handleInventoryMouseClick(0, slot, 0, ClickType.PICKUP, mc.player);
                    multiPlayerGameMode.handleInventoryMouseClick(0, 45, 0, ClickType.PICKUP, mc.player);

                    if (!isOffhandEmpty) {
                        multiPlayerGameMode.handleInventoryMouseClick(0, slot, 0, ClickType.PICKUP, mc.player);
                    }

                    break;
                }
            }
        }
    }
}
