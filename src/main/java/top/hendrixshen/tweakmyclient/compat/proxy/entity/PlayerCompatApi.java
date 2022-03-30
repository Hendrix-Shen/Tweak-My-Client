package top.hendrixshen.tweakmyclient.compat.proxy.entity;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;

public abstract class PlayerCompatApi {
    protected static PlayerCompatApi INSTANCE;

    public static PlayerCompatApi getInstance() {
        return INSTANCE;
    }

    public abstract Inventory getInventory(Player player);

    public abstract void handleInventoryMouseClick(MultiPlayerGameMode inventory, int containerId, int slot, int button, ClickType clickType, Player player);
}
