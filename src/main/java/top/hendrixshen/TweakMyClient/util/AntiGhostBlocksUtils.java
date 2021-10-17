package top.hendrixshen.TweakMyClient.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import top.hendrixshen.TweakMyClient.TweakMyClient;

import java.util.Objects;

public class AntiGhostBlocksUtils {
    public static int manualRefreshTimer;
    public static int automaticRefreshTimer;

    public static void refreshBlocksAroundPlayer() {
        MinecraftClient minecraftClient = TweakMyClient.getMinecraftClient();
        ClientPlayNetworkHandler clientPlayNetworkHandler = minecraftClient.getNetworkHandler();
        assert minecraftClient.player != null;
        assert clientPlayNetworkHandler != null;
        BlockPos blockPos = minecraftClient.player.getBlockPos();
        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();
        for (int i = - 3; i <= 3; i++) {
            for (int j = - 3; j <= 3; j++) {
                for (int k = - 3; k <= 3; k++) {
                    clientPlayNetworkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, new BlockPos(x + i, y + j, z + k), Direction.UP));
                }
            }
        }
    }
}
