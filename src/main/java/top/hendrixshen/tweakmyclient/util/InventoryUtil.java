package top.hendrixshen.tweakmyclient.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.helper.AutoDropListType;
import top.hendrixshen.tweakmyclient.helper.Cache;

//#if MC > 11605
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
//#endif

public class InventoryUtil {
    public static void doDrop() {
        Minecraft mc = TweakMyClient.getMinecraftClient();
        MultiPlayerGameMode interactionManager = mc.gameMode;
        LocalPlayer localPlayer = mc.player;

        if (mc.screen instanceof AbstractContainerScreen && !(mc.screen instanceof InventoryScreen)) {
            return;
        }

        if (localPlayer != null && interactionManager != null) {
            for (int slot = 9; slot < 45; slot++) {
                int adjustedSlot = slot;

                if (adjustedSlot >= 36) {
                    adjustedSlot -= 36;
                }


                if (mc.player == null) {
                    return;
                }

                //#if MC > 11605
                ItemStack stack = localPlayer.getInventory().getItem(adjustedSlot);
                //#else
                //$$ ItemStack stack = localPlayer.inventory.getItem(adjustedSlot);
                //#endif

                if (stack.isEmpty()) {
                    continue;
                }

                AutoDropListType mode = Configs.listAutoDropType;

                switch (mode) {
                    case BLACKLIST:
                        if (!Cache.getInstance().getAutoDropBlackList().contains(stack.getItem())) {
                            // handleInventoryMouseClick(MultiPlayerGameMode interactionManager, int containerId, int slot, int button, ClickType clickType, Player player)
                            interactionManager.handleInventoryMouseClick(0, slot, 1, ClickType.THROW, localPlayer);
                        }
                        break;
                    case WHITELIST:
                        if (Cache.getInstance().getAutoDropWhiteList().contains(stack.getItem())) {
                            interactionManager.handleInventoryMouseClick(0, slot, 1, ClickType.THROW, localPlayer);
                        }
                        break;
                }
            }
        }
    }

    public static void refreshInventory() {
        Minecraft minecraft = TweakMyClient.getMinecraftClient();
        LocalPlayer localPlayer = minecraft.player;
        ClientPacketListener clientPacketListener = minecraft.getConnection();

        if (localPlayer != null && clientPacketListener != null) {
            ItemStack itemStack = new ItemStack(Items.BEDROCK);
            //#if MC > 11605
            Int2ObjectOpenHashMap<ItemStack> int2ObjectMap = new Int2ObjectOpenHashMap<>();
            clientPacketListener.send(new ServerboundContainerClickPacket(0, 0, 0, 0, ClickType.QUICK_MOVE, itemStack, int2ObjectMap));
            //#else
            //$$ short playerNextActionId = localPlayer.containerMenu.backup(localPlayer.inventory);
            //$$ clientPacketListener.send(new ServerboundContainerClickPacket(0, 0, 0, ClickType.QUICK_MOVE, itemStack, playerNextActionId));
            //#endif
        }
    }
}
