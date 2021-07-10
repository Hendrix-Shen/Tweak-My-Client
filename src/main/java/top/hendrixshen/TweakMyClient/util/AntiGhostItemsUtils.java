package top.hendrixshen.TweakMyClient.util;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import top.hendrixshen.TweakMyClient.TweakMyClient;

import java.util.Objects;

public class AntiGhostItemsUtils {
    public static int manualRefreshTimer;
    public static int automaticRefreshTimer;

    public static void refreshInventory() {
        ClientPlayerEntity playerEntity = TweakMyClient.minecraftClient.player;
        if (playerEntity != null) {
            short playerNextActionId = playerEntity.currentScreenHandler.getNextActionId(playerEntity.inventory);
            ItemStack stack = new ItemStack(Items.BEDROCK);
            Objects.requireNonNull(TweakMyClient.minecraftClient.getNetworkHandler()).sendPacket(
                    new ClickSlotC2SPacket(0, 0, 0, SlotActionType.QUICK_MOVE, stack, playerNextActionId)
            );
        }
    }
}
