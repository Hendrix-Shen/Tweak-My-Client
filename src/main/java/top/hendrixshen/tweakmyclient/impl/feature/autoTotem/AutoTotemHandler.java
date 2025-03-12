package top.hendrixshen.tweakmyclient.impl.feature.autoTotem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.Items;
import top.hendrixshen.magiclib.api.compat.minecraft.world.entity.player.PlayerCompat;
import top.hendrixshen.magiclib.api.compat.minecraft.world.item.ItemStackCompat;
import top.hendrixshen.tweakmyclient.api.event.LocalPlayerListener;
import top.hendrixshen.tweakmyclient.game.Configs;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AutoTotemHandler implements LocalPlayerListener {
    @Getter(lazy = true)
    private static final AutoTotemHandler instance = new AutoTotemHandler();

    @Override
    public void onGameJoin(LocalPlayer localPlayer) {

    }

    @Override
    public void onTick(LocalPlayer localPlayer) {
        if (!Configs.autoTotem.getBooleanValue()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        ItemStackCompat offhandItemCompat = ItemStackCompat.of(localPlayer.getOffhandItem());

        if (offhandItemCompat.is(Items.TOTEM_OF_UNDYING)) {
            return;
        }

        if (mc.screen instanceof AbstractContainerScreen && !(mc.screen instanceof InventoryScreen)) {
            return;
        }

        MultiPlayerGameMode multiPlayerGameMode = mc.gameMode;
        assert multiPlayerGameMode != null;
        PlayerCompat playerCompat = PlayerCompat.of(localPlayer);
        Inventory inv = playerCompat.getInventory();

        for (int slot = 9; slot < 45; slot++) {
            ItemStackCompat slotStackCompat = ItemStackCompat.of(inv.getItem(slot));

            if (!slotStackCompat.is(Items.TOTEM_OF_UNDYING)) {
                continue;
            }

            boolean isOffhandEmpty = localPlayer.getOffhandItem().isEmpty();
            multiPlayerGameMode.handleInventoryMouseClick(0, slot, 0, ClickType.PICKUP, mc.player);
            multiPlayerGameMode.handleInventoryMouseClick(0, 45, 0, ClickType.PICKUP, mc.player);

            if (!isOffhandEmpty) {
                multiPlayerGameMode.handleInventoryMouseClick(0, slot, 0, ClickType.PICKUP, mc.player);
            }

            break;
        }
    }
}
