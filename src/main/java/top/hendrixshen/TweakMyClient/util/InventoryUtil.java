package top.hendrixshen.tweakmyclient.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
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
            ItemStack stack = minecraftClient.player.getInventory().getItem(adjustedSlot);

            if (stack.isEmpty())
                continue;

            AutoDropListType mode = (AutoDropListType) Configs.listAutoDropType.getOptionListValue();
            switch (mode) {
                case BLACKLIST:
                    if (!ListCache.itemAutoDropBlackList.contains(stack.getItem())) {
                        assert interactionManager != null;
                        interactionManager.handleInventoryMouseClick(0, slot, 1, ClickType.THROW, TweakMyClient.getMinecraftClient().player);
                    }
                    break;
                case WHITELIST:
                    if (ListCache.itemAutoDropWhiteList.contains(stack.getItem())) {
                        assert interactionManager != null;
                        interactionManager.handleInventoryMouseClick(0, slot, 1, ClickType.THROW, TweakMyClient.getMinecraftClient().player);
                    }
                    break;
            }
        }
    }

    public static void refreshInventory() {
        LocalPlayer localPlayer = TweakMyClient.getMinecraftClient().player;
        if (localPlayer != null) {
            ItemStack stack = new ItemStack(Items.BEDROCK);
            Int2ObjectOpenHashMap<ItemStack> int2ObjectMap = new Int2ObjectOpenHashMap<>();
            Objects.requireNonNull(TweakMyClient.getMinecraftClient().getConnection()).send(
                    new ServerboundContainerClickPacket(0, 0, 0, 0, ClickType.QUICK_MOVE, stack, int2ObjectMap)
            );
        }
    }
}
