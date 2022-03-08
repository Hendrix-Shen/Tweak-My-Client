package top.hendrixshen.TweakMyClient.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import top.hendrixshen.TweakMyClient.TweakMyClient;

public class AntiGhostBlocksUtils {
    public static int manualRefreshTimer;
    public static int automaticRefreshTimer;

    public static void refreshBlocksAroundPlayer() {
        Minecraft minecraftClient = TweakMyClient.getMinecraftClient();
        ClientPacketListener clientPlayNetworkHandler = minecraftClient.getConnection();
        assert minecraftClient.player != null;
        assert clientPlayNetworkHandler != null;
        BlockPos blockPos = new BlockPos(minecraftClient.player);
        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();
        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                for (int k = -3; k <= 3; k++) {
                    clientPlayNetworkHandler.send(new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.ABORT_DESTROY_BLOCK, new BlockPos(x + i, y + j, z + k), Direction.UP));
                }
            }
        }
    }
}
