package top.hendrixshen.tweakmyclient.network.legacyCarpetHandshake;

import carpet.network.CarpetClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record LegacyCarpetVersionPayload(int command, String data) implements CustomPacketPayload {
    public LegacyCarpetVersionPayload(@NotNull FriendlyByteBuf input) throws RuntimeException {
        this(input.readVarInt(), input.readUtf());

        if (this.command != LegacyClientNetworkHandler.HI && this.command != LegacyClientNetworkHandler.DATA) {
            throw new RuntimeException();
        }
    }

    @Override
    public void write(@NotNull FriendlyByteBuf output) {
        output.writeVarInt(command);
        output.writeUtf(data);
    }

    @Override
    public @NotNull ResourceLocation id() {
        return CarpetClient.CARPET_CHANNEL;
    }
}
