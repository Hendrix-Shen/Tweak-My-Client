package top.hendrixshen.TweakMyClient.util;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import top.hendrixshen.TweakMyClient.TweakMyClient;

public class AntiGhostItemsUtils {
    public static int manualRefreshTimer;

    public static void refreshInventory() {
        /*
        *
        short s = player.currentScreenHandler.getNextActionId(player.inventory);
        int syncId = screen.getScreenHandler().syncId;
        ItemStack fakeStack = new ItemStack(Items.BEDROCK);         // some item the player "shouldn't" have in their inventory, much less use it to craft
        MinecraftClient.getInstance().getNetworkHandler().sendPacket(
                new ClickSlotC2SPacket(syncId, 0, 0, SlotActionType.QUICK_MOVE, fakeStack, s)
        );*/
        ClientPlayerEntity playerEntity = TweakMyClient.minecraftClient.player;
        if (playerEntity != null) {
            short playerNextActionId = playerEntity.currentScreenHandler.getNextActionId(playerEntity.inventory);
            ItemStack stack = new ItemStack(Items.BEDROCK);
            TweakMyClient.minecraftClient.getNetworkHandler().sendPacket(
                    new ClickSlotC2SPacket(0, 0, 0, SlotActionType.QUICK_MOVE, stack, playerNextActionId)
            );
        }
    }
}
