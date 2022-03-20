package top.hendrixshen.tweakmyclient.mixin.disable.disableSlowdown.tweakeroo;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.api.dependencyValidator.annotation.Dependencies;
import top.hendrixshen.magiclib.api.dependencyValidator.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;

@Dependencies(conflict = @Dependency(value = "tweakeroo", versionPredicates = "*"))
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
        if (Configs.disableSlowdown.getBooleanValue()) {
            ci.cancel();
        }
    }
}
