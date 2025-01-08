package top.hendrixshen.tweakmyclient.mixin.patch.legacyCarpetHandshake;

import carpet.network.ClientNetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.api.annotation.Dependency;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.network.legacyCarpetHandshake.LegacyCarpetDataPayload;
import top.hendrixshen.tweakmyclient.network.legacyCarpetHandshake.LegacyCarpetVersionPayload;
import top.hendrixshen.tweakmyclient.network.legacyCarpetHandshake.LegacyClientNetworkHandler;

@Dependencies(and = {
        @Dependency(value = "carpet", versionPredicate = ">1.4.113"),
        @Dependency(value = "minecraft", versionPredicate = ">1.20.1")
})
@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {
    @Inject(
            method = "handleUnknownCustomPayload",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onHandleUnknownCustomPayload(CustomPacketPayload packet, CallbackInfo ci) {
        if (Configs.legacyCarpetHandshake) {
            if (packet instanceof LegacyCarpetVersionPayload) {
                LegacyClientNetworkHandler.handleVersion(((LegacyCarpetVersionPayload) packet));
                ci.cancel();
            }

            if (packet instanceof LegacyCarpetDataPayload) {
                TweakMyClientReference.getLogger().error(((LegacyCarpetDataPayload) packet).data().toString());
                ClientNetworkHandler.onServerData(((LegacyCarpetDataPayload) packet).data(), Minecraft.getInstance().player);
                ci.cancel();
            }
        }
    }
}
