package top.hendrixshen.tweakmyclient.mixin.disable.disableSlowdown.tweakeroo;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlimeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.game.Configs;

@Mixin(SlimeBlock.class)
public abstract class MixinSlimeBlock extends Block {
    public MixinSlimeBlock(Properties properties) {
        super(properties);
    }

    @Inject(method = "stepOn", at = @At("HEAD"), cancellable = true)
    private void stepOn(
            Level level,
            BlockPos blockPos,
            //#if MC > 11605
            //$$ BlockState blockState,
            //#endif
            Entity entity,
            CallbackInfo ci) {
        if (Configs.disableSlowdown.getBooleanValue() && entity instanceof LocalPlayer) {
            ci.cancel();
        }
    }
}
