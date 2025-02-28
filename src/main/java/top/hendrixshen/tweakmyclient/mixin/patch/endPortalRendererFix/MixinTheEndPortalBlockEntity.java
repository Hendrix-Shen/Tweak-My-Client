package top.hendrixshen.tweakmyclient.mixin.patch.endPortalRendererFix;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.tweakmyclient.game.Configs;
import top.hendrixshen.tweakmyclient.impl.patch.endPortalRendererFix.EnderPortalRenderMode;

@Mixin(TheEndPortalBlockEntity.class)
public abstract class MixinTheEndPortalBlockEntity {
    @Inject(
            method = "shouldRenderFace",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void shouldRenderFace(Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (Configs.endPortalRendererFix.getBooleanValue()) {
            //#if MC > 11605
            //$$ if (Configs.enderPortalRenderMode == EnderPortalRenderMode.LEGACY) {
            //$$     cir.setReturnValue(direction == Direction.UP);
            //$$ } else if (Configs.enderPortalRenderMode != EnderPortalRenderMode.MODERN) {
            //#else
            if (Configs.enderPortalRenderMode.getOptionListValue() == EnderPortalRenderMode.MODERN) {
                cir.setReturnValue(direction.getAxis() == Direction.Axis.Y);
            } else if (Configs.enderPortalRenderMode.getOptionListValue() != EnderPortalRenderMode.LEGACY) {
            //#endif
                cir.setReturnValue(true);
            }
        }
    }
}
