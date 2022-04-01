package top.hendrixshen.tweakmyclient.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.compat.proxy.entity.PlayerCompatApi;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.helper.AutoDropListType;
import top.hendrixshen.tweakmyclient.helper.ListCache;

import java.util.Objects;

public class InventoryUtil {
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
            ItemStack stack = PlayerCompatApi.getInstance().getInventory(minecraftClient.player).getItem(adjustedSlot);

            if (stack.isEmpty())
                continue;

            AutoDropListType mode = (AutoDropListType) Configs.listAutoDropType;
            switch (mode) {
                case BLACKLIST:
                    if (!ListCache.itemAutoDropBlackList.contains(stack.getItem())) {
                        assert interactionManager != null;
                        PlayerCompatApi.getInstance().handleInventoryMouseClick(interactionManager, 0, slot, 1, ClickType.THROW, TweakMyClient.getMinecraftClient().player);
                    }
                    break;
                case WHITELIST:
                    if (ListCache.itemAutoDropWhiteList.contains(stack.getItem())) {
                        assert interactionManager != null;
                        PlayerCompatApi.getInstance().handleInventoryMouseClick(interactionManager, 0, slot, 1, ClickType.THROW, TweakMyClient.getMinecraftClient().player);
                    }
                    break;
            }
        }
    }

    public static void refreshInventory() {
        LocalPlayer localPlayer = TweakMyClient.getMinecraftClient().player;
        if (localPlayer != null) {
            short playerNextActionId = localPlayer.containerMenu.backup(localPlayer.inventory);
            ItemStack itemStack = new ItemStack(Items.BEDROCK);
            Objects.requireNonNull(TweakMyClient.getMinecraftClient().getConnection()).send(
                    new ServerboundContainerClickPacket(0, 0, 0, ClickType.QUICK_MOVE, itemStack, playerNextActionId)
            );
        }
    }
}
