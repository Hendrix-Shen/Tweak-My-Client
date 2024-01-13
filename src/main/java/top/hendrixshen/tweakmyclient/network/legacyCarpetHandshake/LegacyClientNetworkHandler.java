package top.hendrixshen.tweakmyclient.network.legacyCarpetHandshake;

import carpet.CarpetSettings;
import carpet.network.CarpetClient;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import org.jetbrains.annotations.NotNull;

public class LegacyClientNetworkHandler {
    public static final int HI = 69;
    public static final int HELLO = 420;
    public static final int DATA = 1;

    public static void handleData(@NotNull LegacyCarpetVersionPayload data) {
        if (data.command() == LegacyClientNetworkHandler.HI) {
            LegacyClientNetworkHandler.onHi(data.data());
        }
    }

    public static void onHi(@NotNull String version) {
        CarpetClient.setCarpet();
        CarpetClient.serverCarpetVersion = version;

        if (CarpetSettings.carpetVersion.equals(CarpetClient.serverCarpetVersion)) {
            CarpetSettings.LOG.info("Joined carpet server with matching carpet version");
        } else {
            CarpetSettings.LOG.warn("Joined carpet server with another carpet version: " + CarpetClient.serverCarpetVersion);
        }

        // We can ensure that this packet is
        // processed AFTER the player has joined
        LegacyClientNetworkHandler.respondHello();
    }

    public static void respondHello() {
        CarpetClient.getPlayer().connection.send(new ServerboundCustomPayloadPacket(
                new LegacyCarpetVersionPayload(LegacyClientNetworkHandler.HELLO, CarpetSettings.carpetVersion)
        ));
    }
}
