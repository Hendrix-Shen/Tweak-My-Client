package top.hendrixshen.tweakmyclient.compat.proxy.entity;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;

public class PlayerCompatImpl extends PlayerCompatApi {
    public static void initCompat() {
        INSTANCE = new PlayerCompatImpl();
    }

    @Override
    public Inventory getInventory(Player player) {
        return player.inventory;
    }

    @Override
    public void handleInventoryMouseClick(MultiPlayerGameMode interactionManager, int containerId, int slot, int button, ClickType clickType, Player player) {
        interactionManager.handleInventoryMouseClick(containerId, slot, button, clickType, player);
    }
}
