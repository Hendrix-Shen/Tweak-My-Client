package top.hendrixshen.tweakmyclient.mixin.patch.chunkEdgeLagFix;

import top.hendrixshen.magiclib.api.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.multiplayer.ClientPacketListener;

// CHECKSTYLE.OFF: ImportOrder
//#if MC > 11701
//$$ import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
//#else
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.lighting.LevelLightEngine;
//#endif
// CHECKSTYLE.ON: ImportOrder

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// CHECKSTYLE.OFF: ImportOrder
//#if MC > 11701
//$$ import org.spongepowered.asm.mixin.injection.Inject;
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//#else
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
//#endif
// CHECKSTYLE.ON: ImportOrder

@Dependencies(conflict = @Dependency(value = "forgetmechunk"))
@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener {
    //#if MC > 11701
    //$$ @Inject(
    //$$         method = "handleForgetLevelChunk",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;queueLightUpdate(Lnet/minecraft/network/protocol/game/ClientboundForgetLevelChunkPacket;)V"
    //$$         ),
    //$$         cancellable = true
    //$$ )
    //$$ private void chunkEdgeLagFix(ClientboundForgetLevelChunkPacket clientboundForgetLevelChunkPacket, CallbackInfo ci) {
    //$$     if (Configs.chunkEdgeLagFix.getBooleanValue()) {
    //$$         ci.cancel();
    //$$     }
    //$$ }
    //#else
    @WrapWithCondition(
            method = "handleForgetLevelChunk",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/lighting/LevelLightEngine;updateSectionStatus(Lnet/minecraft/core/SectionPos;Z)V"
            )
    )
    private boolean chunkEdgeLagFix(LevelLightEngine instance, SectionPos sectionPos, boolean bl) {
        return !Configs.chunkEdgeLagFix.getBooleanValue();
    }
    //#endif
}
