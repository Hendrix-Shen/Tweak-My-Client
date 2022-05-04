package top.hendrixshen.tweakmyclient.mixin.disable.disableSlowdown;

//#if MC >= 11500
import net.minecraft.client.Minecraft;
//#else
//$$ import net.minecraft.client.player.LocalPlayer;
//$$ import net.minecraft.core.BlockPos;
//$$ import net.minecraft.world.entity.Entity;
//$$ import net.minecraft.world.level.Level;
//$$ import net.minecraft.world.level.block.SoulsandBlock;
//$$ import net.minecraft.world.level.block.state.BlockState;
//#endif
import org.spongepowered.asm.mixin.Mixin;
//#if MC < 11500
//$$ import org.spongepowered.asm.mixin.injection.At;
//$$ import org.spongepowered.asm.mixin.injection.Inject;
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//$$ import top.hendrixshen.tweakmyclient.config.Configs;
//$$
//$$ @Mixin(SoulsandBlock.class)
//#else
@Mixin(Minecraft.class)
//#endif
public class MixinSoulsandBlock {
    //#if MC < 11500
    //$$ @Inject(
    //$$         method = "entityInside",
    //$$         at = @At(
    //$$                 value = "HEAD"
    //$$        ),
    //$$         cancellable = true
    //$$ )
    //$$ private void onEntityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity, CallbackInfo ci) {
    //$$     if (Configs.disableSlowdown && entity instanceof LocalPlayer) {
    //$$         ci.cancel();
    //$$     }
    //$$ }
    //#endif
}
