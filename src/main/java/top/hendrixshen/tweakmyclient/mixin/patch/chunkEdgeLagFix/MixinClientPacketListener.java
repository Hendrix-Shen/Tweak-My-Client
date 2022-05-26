package top.hendrixshen.tweakmyclient.mixin.patch.chunkEdgeLagFix;

import net.minecraft.client.multiplayer.ClientPacketListener;
//#if MC < 11800
//$$ import net.minecraft.core.SectionPos;
//#else
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
//#endif
//#if MC < 11800
//$$ import net.minecraft.world.level.lighting.LevelLightEngine;
//#endif
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//#if MC >= 11800
import org.spongepowered.asm.mixin.injection.Inject;
//#else
//$$ import org.spongepowered.asm.mixin.injection.Redirect;
//#endif
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {
    //#if MC >= 11800
    @Inject(
            method = "handleForgetLevelChunk",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;queueLightUpdate(Lnet/minecraft/network/protocol/game/ClientboundForgetLevelChunkPacket;)V"
            ),
            cancellable = true
    )
    private void chunkEdgeLagFix(ClientboundForgetLevelChunkPacket clientboundForgetLevelChunkPacket, CallbackInfo ci) {
        if (Configs.chunkEdgeLagFix) {
            ci.cancel();
        }
    }
    //#else
    //$$ @Redirect(
    //$$         method = "handleForgetLevelChunk",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Lnet/minecraft/world/level/lighting/LevelLightEngine;updateSectionStatus(Lnet/minecraft/core/SectionPos;Z)V"
    //$$         )
    //$$ )
    //$$ private void chunkEdgeLagFix(LevelLightEngine instance, SectionPos sectionPos, boolean bl) {
    //$$     if (!Configs.chunkEdgeLagFix) {
    //$$          instance.updateSectionStatus(sectionPos, bl);
    //$$     }
    //$$ }
    //#endif
}
