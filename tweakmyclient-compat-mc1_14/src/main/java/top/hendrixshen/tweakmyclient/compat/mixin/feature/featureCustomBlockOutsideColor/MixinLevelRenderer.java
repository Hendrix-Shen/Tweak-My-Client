package top.hendrixshen.tweakmyclient.compat.mixin.feature.featureCustomBlockOutsideColor;

import fi.dy.masa.malilib.util.Color4f;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer {
    @ModifyArgs(
            method = "renderHitOutline",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;renderShape(Lnet/minecraft/world/phys/shapes/VoxelShape;DDDFFFF)V"
            )
    )
    private void onDrawBlockOutline(Args args) {
        if (Configs.featureCustomBlockOutsideColor) {
            Color4f color = Configs.colorBlockOutside;
            args.set(4, color.r);
            args.set(5, color.g);
            args.set(6, color.b);
            args.set(7, color.a);
        }
    }
}
