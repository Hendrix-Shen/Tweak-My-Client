package top.hendrixshen.tweakmyclient.impl.generic.syncInventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class InventoryRefresher {
    public static void refresh() {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer localPlayer = mc.player;
        ClientPacketListener clientPacketListener = mc.getConnection();

        if (localPlayer == null || clientPacketListener == null) {
            return;
        }

        ItemStack itemStack = new ItemStack(Items.BEDROCK);
        //#if MC > 11605
        //$$ Int2ObjectOpenHashMap<ItemStack> int2ObjectMap = new Int2ObjectOpenHashMap<>();
        //$$ clientPacketListener.send(new ServerboundContainerClickPacket(0, 0, 0, 0, ClickType.QUICK_MOVE, itemStack, int2ObjectMap));
        //#else
        short playerNextActionId = localPlayer.containerMenu.backup(localPlayer.inventory);
        clientPacketListener.send(new ServerboundContainerClickPacket(0, 0, 0, ClickType.QUICK_MOVE, itemStack, playerNextActionId));
        //#endif
    }
}
