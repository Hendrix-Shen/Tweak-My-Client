package top.hendrixshen.tweakmyclient.util;

//#if MC >= 11700
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
//#endif
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
import top.hendrixshen.tweakmyclient.helper.ListCache;

public class InventoryUtil {
    public static void doDrop() {
        Minecraft minecraftClient = TweakMyClient.getMinecraftClient();
        MultiPlayerGameMode interactionManager = minecraftClient.gameMode;
        LocalPlayer localPlayer = minecraftClient.player;
        if (minecraftClient.screen instanceof AbstractContainerScreen && !(minecraftClient.screen instanceof InventoryScreen)) {
            return;
        }

        if (localPlayer != null && interactionManager != null) {
            for (int slot = 9; slot < 45; slot++) {
                int adjustedSlot = slot;
                if (adjustedSlot >= 36)
                    adjustedSlot -= 36;
                assert minecraftClient.player != null;
                //#if MC >= 11700
                ItemStack stack = localPlayer.getInventory().getItem(adjustedSlot);
                //#else
                //$$ ItemStack stack = localPlayer.inventory.getItem(adjustedSlot);
                //#endif

                if (stack.isEmpty())
                    continue;

                AutoDropListType mode = Configs.listAutoDropType;
                switch (mode) {
                    case BLACKLIST:
                        if (!ListCache.itemAutoDropBlackList.contains(stack.getItem())) {
                            //handleInventoryMouseClick(MultiPlayerGameMode interactionManager, int containerId, int slot, int button, ClickType clickType, Player player)
                            interactionManager.handleInventoryMouseClick(0, slot, 1, ClickType.THROW, localPlayer);
                        }
                        break;
                    case WHITELIST:
                        if (ListCache.itemAutoDropWhiteList.contains(stack.getItem())) {
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
            //short playerNextActionId = localPlayer.containerMenu.backup(localPlayer.inventory);
            ItemStack itemStack = new ItemStack(Items.BEDROCK);
            //#if MC >= 11700
            Int2ObjectOpenHashMap<ItemStack> int2ObjectMap = new Int2ObjectOpenHashMap<>();
            clientPacketListener.send(new ServerboundContainerClickPacket(0, 0, 0, 0, ClickType.QUICK_MOVE, itemStack, int2ObjectMap));
            //#else
            //$$short playerNextActionId = localPlayer.containerMenu.backup(localPlayer.inventory);
            //$$clientPacketListener.send(new ServerboundContainerClickPacket(0, 0, 0, ClickType.QUICK_MOVE, itemStack, playerNextActionId));
            //#endif
        }
    }
}
