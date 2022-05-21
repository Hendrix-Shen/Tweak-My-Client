package top.hendrixshen.tweakmyclient.mixin.feature.featureCustomBlockOutsideColor;

import fi.dy.masa.malilib.util.Color4f;
import net.minecraft.client.Minecraft;
//#if MC >= 11500
import net.minecraft.client.multiplayer.ClientLevel;
//#else
//$$ import net.minecraft.client.multiplayer.MultiPlayerLevel;
//#endif
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.MiscUtil;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer {
    @Shadow @Final private Minecraft minecraft;

    @ModifyArgs(
            method = "renderHitOutline",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 11500
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;renderShape(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/phys/shapes/VoxelShape;DDDFFFF)V"
                    //#else
                    //$$ target = "Lnet/minecraft/client/renderer/LevelRenderer;renderShape(Lnet/minecraft/world/phys/shapes/VoxelShape;DDDFFFF)V"
                    //#endif
            )
    )
    private void onDrawBlockOutline(Args args) {
        if (Configs.featureCustomBlockHitBoxOverlayOutline) {
            Color4f color = Configs.colorBlockHitBoxOverlayOutline;
            float r, g, b;
            if (Configs.customBlockHitBoxOverlayOutlineRainbow) {
                float k = System.currentTimeMillis() % (100 * (101 - Configs.customBlockHitBoxOverlayOutlineRainbowSpeed)) / (50F * (101 - Configs.customBlockHitBoxOverlayOutlineRainbowSpeed));
                r = 0.5F + 0.5F * (float) Math.sin(k * Math.PI);
                g = 0.5F + 0.5F * (float) Math.sin((k + 2F / 3F) * Math.PI);
                b = 0.5F + 0.5F * (float) Math.sin((k + 6F / 3F) * Math.PI);
            } else {
                r = color.r;
                g = color.g;
                b = color.b;
            }
            if (Configs.customBlockHitBoxOverlayLinkedAdapter) {
                Minecraft minecraft = TweakMyClient.getMinecraftClient();
                HitResult hitResult = minecraft.hitResult;
                //#if MC >= 11500
                ClientLevel clientLevel = minecraft.level;
                //#else
                //$$ MultiPlayerLevel clientLevel = minecraft.level;
                //#endif
                if (hitResult instanceof BlockHitResult && clientLevel != null) {
                    BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
                    BlockState blockState = clientLevel.getBlockState(blockPos);
                    //#if MC >= 11500
                    VoxelShape voxelShape = args.get(2);
                    args.set(2, MiscUtil.linkedBlockAdapter(clientLevel, blockState, blockPos, voxelShape));
                    //#else
                    //$$ VoxelShape voxelShape = args.get(0);
                    //$$ args.set(0, MiscUtil.linkedBlockAdapter(clientLevel, blockState, blockPos, voxelShape));
                    //#endif
                }
            }
            //#if MC >= 11500
            args.set(6, r);
            args.set(7, g);
            args.set(8, b);
            args.set(9, color.a);
            //#else
            //$$ args.set(4, r);
            //$$ args.set(5, g);
            //$$ args.set(6, b);
            //$$ args.set(7, color.a);
            //#endif
        }
    }
}
