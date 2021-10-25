package top.hendrixshen.TweakMyClient.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import top.hendrixshen.TweakMyClient.TweakMyClient;

import java.util.Objects;

public class AntiGhostItemsUtils {
    public static int manualRefreshTimer;
    public static int automaticRefreshTimer;

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
