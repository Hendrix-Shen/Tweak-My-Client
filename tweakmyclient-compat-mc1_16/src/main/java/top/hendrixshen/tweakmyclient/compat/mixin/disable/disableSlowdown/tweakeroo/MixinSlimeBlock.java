package top.hendrixshen.tweakmyclient.compat.mixin.disable.disableSlowdown.tweakeroo;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlimeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;

@Dependencies(not = @Dependency(value = "tweakeroo"))
@Mixin(SlimeBlock.class)
public class MixinSlimeBlock extends Block {
    public MixinSlimeBlock(Properties properties) {
        super(properties);
    }

    @Inject(
            method = "stepOn",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void stepOn(Level level, BlockPos blockPos, Entity entity, CallbackInfo ci) {
        if (Configs.disableSlowdown) {
            ci.cancel();
        }
    }
}
