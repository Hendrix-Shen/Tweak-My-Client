package top.hendrixshen.TweakMyClient.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.hendrixshen.TweakMyClient.TweakMyClient;
import top.hendrixshen.TweakMyClient.config.Configs;

import java.util.HashSet;

public class AutoDropUtils {
    public static int waitTime;
    public static HashSet<Item> itemStacksWhitelist;
    public static HashSet<Item> itemStacksBlackList;

    public static void doDrop() {
        Minecraft minecraftClient = TweakMyClient.getMinecraftClient();
        MultiPlayerGameMode interactionManager = minecraftClient.gameMode;
        if (minecraftClient.screen instanceof AbstractContainerScreen && !(minecraftClient.screen instanceof InventoryScreen)) {
            return;
        }

        for (int slot = 9; slot < 45; slot++) {
            int adjustedSlot = slot;
            if (adjustedSlot >= 36)
                adjustedSlot -= 36;
            assert minecraftClient.player != null;
            ItemStack stack = minecraftClient.player.inventory.getItem(adjustedSlot);

            if (stack.isEmpty())
                continue;

            Configs.AutoDropListType mode = (Configs.AutoDropListType) Configs.List.LIST_AUTO_DROP_TYPE.getOptionListValue();
            switch (mode) {
                case BLACKLIST:
                    if (!itemStacksBlackList.contains(stack.getItem())) {
                        assert interactionManager != null;
                        interactionManager.handleInventoryMouseClick(0, slot, 1, ClickType.THROW, TweakMyClient.getMinecraftClient().player);
                    }
                    break;
                case WHITELIST:
                    if (itemStacksWhitelist.contains(stack.getItem())) {
                        assert interactionManager != null;
                        interactionManager.handleInventoryMouseClick(0, slot, 1, ClickType.THROW, TweakMyClient.getMinecraftClient().player);
                    }
                    break;
            }
        }
    }
}
