package top.hendrixshen.tweakmyclient.impl.generic.syncBlocks;

import top.hendrixshen.magiclib.api.compat.minecraft.world.entity.player.PlayerCompat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;

public class BlockRefresher {
    public static void refresh() {
        Minecraft mc = Minecraft.getInstance();
        ClientPacketListener clientPlayNetworkHandler = mc.getConnection();

        if (mc.player == null || clientPlayNetworkHandler == null) {
            return;
        }

        PlayerCompat playerCompat = PlayerCompat.of(mc.player);
        BlockPos blockPos = playerCompat.getBlockPosition();
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
