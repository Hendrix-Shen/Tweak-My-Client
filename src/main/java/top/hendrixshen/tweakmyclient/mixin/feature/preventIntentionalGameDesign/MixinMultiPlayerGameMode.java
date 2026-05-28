package top.hendrixshen.tweakmyclient.mixin.feature.preventIntentionalGameDesign;

import top.hendrixshen.magiclib.api.compat.minecraft.world.entity.player.PlayerCompat;
import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

// CHECKSTYLE.OFF: ImportOrder
//#if MC >= 1.21.11
//$$ import net.minecraft.world.attribute.BedRule;
//$$ import net.minecraft.world.attribute.EnvironmentAttributes;
//#endif

//#if MC >= 1.16
import net.minecraft.world.level.block.RespawnAnchorBlock;
//#else
//$$ import net.minecraft.world.level.biome.Biomes;
//#endif
// CHECKSTYLE.ON: ImportOrder

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.sugar.Local;

@Mixin(MultiPlayerGameMode.class)
public class MixinMultiPlayerGameMode {
    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void onUseItemOn(
            CallbackInfoReturnable<InteractionResult> cir,
            @Local(argsOnly = true) LocalPlayer player,
            @Local(argsOnly = true) BlockHitResult hitResult) {
        if (!Configs.preventIntentionalGameDesign.getBooleanValue()) {
            return;
        }

        PlayerCompat playerCompat = PlayerCompat.of(player);
        Level level = playerCompat.getLevel();
        BlockPos blockPos = hitResult.getBlockPos();
        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();
        //#if MC >= 1.21.11
        //$$ BedRule bedRule = level.environmentAttributes().getValue(EnvironmentAttributes.BED_RULE, blockPos);
        //$$ Boolean respawnAnchorWorks = level.environmentAttributes().getValue(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, blockPos);
        //#endif

        if (block instanceof BedBlock
                //#if MC >= 1.21.11
                //$$ && bedRule.explodes()
                //#elseif MC >= 1.16
                && !level.dimensionType().bedWorks()
                //#else
                //$$ && !(level.dimension.mayRespawn() && level.getBiome(blockPos) != Biomes.NETHER)
                //#endif
        ) {
            cir.setReturnValue(InteractionResult.SUCCESS);
        }

        //#if MC >= 1.16
        if (block instanceof RespawnAnchorBlock
                //#if MC >= 1.21.11
                //$$ && !respawnAnchorWorks
                //#else
                && !level.dimensionType().respawnAnchorWorks()
                //#endif
        ) {
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
        //#endif
    }
}
