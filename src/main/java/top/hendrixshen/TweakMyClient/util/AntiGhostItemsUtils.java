package top.hendrixshen.TweakMyClient.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import top.hendrixshen.TweakMyClient.TweakMyClient;

import java.util.Objects;

public class AntiGhostItemsUtils {
    public static int automaticRefreshTimer;
    public static int manualRefreshTimer;

    public static void refreshInventory() {
        ClientPlayerEntity playerEntity = TweakMyClient.minecraftClient.player;
        if (playerEntity != null) {
            ItemStack stack = new ItemStack(Items.BEDROCK);
            Int2ObjectOpenHashMap<ItemStack> int2ObjectMap = new Int2ObjectOpenHashMap<>();
            Objects.requireNonNull(TweakMyClient.minecraftClient.getNetworkHandler()).sendPacket(
                    new ClickSlotC2SPacket(0, 0, 0, 0, SlotActionType.QUICK_MOVE, stack, int2ObjectMap)
            );
        }
    }
}
