package top.hendrixshen.TweakMyClient.util;

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
            short playerNextActionId = localPlayer.containerMenu.backup(localPlayer.inventory);
            ItemStack itemStack = new ItemStack(Items.BEDROCK);
            Objects.requireNonNull(TweakMyClient.getMinecraftClient().getConnection()).send(
                    new ServerboundContainerClickPacket(0, 0, 0, ClickType.QUICK_MOVE, itemStack, playerNextActionId)
            );
        }
    }
}
