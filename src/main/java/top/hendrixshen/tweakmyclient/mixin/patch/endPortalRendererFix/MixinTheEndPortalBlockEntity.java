package top.hendrixshen.tweakmyclient.mixin.patch.endPortalRendererFix;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.helper.EnderPortalRenderMode;

@Dependencies(and = @Dependency(value = "minecraft", versionPredicate = "<1.17"))
@Mixin(TheEndPortalBlockEntity.class)
public class MixinTheEndPortalBlockEntity {
    @Inject(
            method = "shouldRenderFace",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void shouldRenderFace(Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (Configs.endPortalRendererFix) {
            //#if MC >= 11700
            if (Configs.enderPortalRenderMode == EnderPortalRenderMode.LEGACY) {
                cir.setReturnValue(direction == Direction.UP);
            } else if (Configs.enderPortalRenderMode != EnderPortalRenderMode.MODERN) {
            //#else
            //$$ if (Configs.enderPortalRenderMode == EnderPortalRenderMode.MODERN) {
            //$$     cir.setReturnValue(direction.getAxis() == Direction.Axis.Y);
            //$$ } else if (Configs.enderPortalRenderMode != EnderPortalRenderMode.LEGACY) {
            //#endif
                cir.setReturnValue(true);
            }
        }
    }
}
