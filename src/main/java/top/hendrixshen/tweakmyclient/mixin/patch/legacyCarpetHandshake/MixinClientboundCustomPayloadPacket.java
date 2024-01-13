package top.hendrixshen.tweakmyclient.mixin.patch.legacyCarpetHandshake;

import carpet.network.CarpetClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.network.legacyCarpetHandshake.LegacyCarpetVersionPayload;

@Dependencies(and = {
        @Dependency(value = "carpet", versionPredicate = ">1.4.113"),
        @Dependency(value = "minecraft", versionPredicate = ">1.20.1")
})
@Mixin(value = ClientboundCustomPayloadPacket.class, priority = 990)
public class MixinClientboundCustomPayloadPacket {
    @Inject(
            method = "readPayload",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/protocol/common/ClientboundCustomPayloadPacket;readUnknownPayload(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/network/protocol/common/custom/DiscardedPayload;"
            ),
            cancellable = true
    )
    private static void onReadPayload(@NotNull ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf,
                           @NotNull CallbackInfoReturnable<CustomPacketPayload> cir) {
        if (Configs.legacyCarpetHandshake && resourceLocation.equals(CarpetClient.CARPET_CHANNEL)) {
            try {
                cir.setReturnValue(new LegacyCarpetVersionPayload(new FriendlyByteBuf(friendlyByteBuf.copy())));
            } catch (Exception ignore) {
            }
        }
    }
}
