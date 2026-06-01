package top.hendrixshen.tweakmyclient.mixin.feature.instantBreakingCooldown;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

// CHECKSTYLE.OFF: ImportOrder
//#if MC >= 1.19
//$$ import net.minecraft.network.protocol.Packet;
//$$ import net.minecraft.network.protocol.game.ServerGamePacketListener;
//$$ import net.minecraft.world.level.block.state.BlockState;
//#endif
// CHECKSTYLE.ON: ImportOrder

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class MixinMultiPlayerGameMode {
    @Shadow
    private int destroyDelay;

    //#if MC >= 1.19
    //$$ @Inject(
    //$$         //#if MC >= 26.1
    //$$         //$$ method = "lambda$startDestroyBlock$1",
    //$$         //#else
    //$$         method = "method_41930",
    //$$         //#endif
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;destroyBlock(Lnet/minecraft/core/BlockPos;)Z",
    //$$                 remap = true,
    //$$                 ordinal = 0
    //$$         ),
    //$$         remap = false
    //$$ )
    //$$ private void addBreakingCooldown(BlockState blockState, BlockPos blockPos, Direction direction, int i, CallbackInfoReturnable<Packet<ServerGamePacketListener>> cir) {
    //$$     if (Configs.instantBreakingCooldown.getBooleanValue()) {
    //$$         this.destroyDelay = 5;
    //$$     }
    //$$ }
    //#else
    @Inject(
            method = "startDestroyBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;destroyBlock(Lnet/minecraft/core/BlockPos;)Z",
                    //#if MC >= 1.16
                    ordinal = 1
                    //#else
                    //$$ ordinal = 0
                    //#endif
            )
    )
    private void addCooldownForInstantBreak(BlockPos blockPos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (Configs.instantBreakingCooldown.getBooleanValue()) {
            this.destroyDelay = 5;
        }
    }
    //#endif
}
