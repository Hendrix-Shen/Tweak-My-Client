package top.hendrixshen.TweakMyClient.mixin.patch.endPortalRendererFix;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.TweakMyClient.config.Configs;
import top.hendrixshen.TweakMyClient.util.render.EnderPortalRenderMode;

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
        if (Configs.Patch.ENDER_PORTAL_RENDERER_FIX.getBooleanValue()) {
            if (Configs.Generic.ENDER_PORTAL_RENDER_MODE.getOptionListValue() == EnderPortalRenderMode.MODERN) {
                cir.setReturnValue(direction.getAxis() == Direction.Axis.Y);
            } else if (Configs.Generic.ENDER_PORTAL_RENDER_MODE.getOptionListValue() != EnderPortalRenderMode.LEGACY) {
                cir.setReturnValue(true);
            }
        }
    }
}
