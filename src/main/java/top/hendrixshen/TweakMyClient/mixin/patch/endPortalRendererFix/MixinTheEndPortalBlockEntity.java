package top.hendrixshen.TweakMyClient.mixin.patch.endPortalRendererFix;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.TweakMyClient.config.Configs;

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
            cir.setReturnValue(true);
        }
    }
}
