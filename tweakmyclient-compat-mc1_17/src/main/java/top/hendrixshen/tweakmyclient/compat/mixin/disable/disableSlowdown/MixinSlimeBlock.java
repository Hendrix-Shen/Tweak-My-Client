package top.hendrixshen.tweakmyclient.compat.mixin.disable.disableSlowdown;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlimeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.helper.CommonCompatLib;

@Mixin(SlimeBlock.class)
public class MixinSlimeBlock extends Block {
    public MixinSlimeBlock(Properties properties) {
        super(properties);
    }

    @Inject(
            method = "bounceUp",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onbounceUp(Entity entity, CallbackInfo ci) {
        CommonCompatLib.disableSlowdown(entity, ci);
    }
}
