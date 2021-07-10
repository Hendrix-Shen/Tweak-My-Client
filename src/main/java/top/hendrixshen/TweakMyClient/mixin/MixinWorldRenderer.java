package top.hendrixshen.TweakMyClient.mixin;

import fi.dy.masa.malilib.util.Color4f;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.TweakMyClient;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(WorldRenderer.class)
public abstract class MixinWorldRenderer {
    @Shadow
    private static void drawShapeOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, VoxelShape voxelShape, double d, double e, double f, float g, float h, float i, float j) {
    }

    @Shadow
    private ClientWorld world;

    @Inject(
            method = "drawBlockOutline",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/WorldRenderer;drawShapeOutline(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/util/shape/VoxelShape;DDDFFFF)V"
            ),
            cancellable = true
    )
    private void onDrawBlockOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, Entity entity, double d, double e, double f, BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
        if (Configs.Feature.FEATURE_CUSTOM_BLOCK_OUTSIDE_COLOR.getBooleanValue()) {
            Color4f color = Color4f.fromColor(Configs.Color.COLOR_BLOCK_OUTSIDE.getIntegerValue());
            drawShapeOutline(matrixStack, vertexConsumer, blockState.getOutlineShape(this.world, blockPos, ShapeContext.of(entity)), (double) blockPos.getX() - d, (double) blockPos.getY() - e, (double) blockPos.getZ() - f, color.r, color.g, color.b, color.a);
            ci.cancel();
        }
    }

    @Inject(
            method = "processGlobalEvent",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onProcessGlobalEvent(int eventId, BlockPos pos, int i, CallbackInfo ci) {
        if (Configs.Feature.FEATURE_GLOBAL_EVENT_LISTENER.getBooleanValue()) {
            ClientPlayerEntity player = TweakMyClient.minecraftClient.player;
            assert player != null;
            if (eventId == 1023) { //SoundEvents.ENTITY_WITHER_SPAWN
                player.sendMessage(new LiteralText(StringUtils.translate("tweakmyclient.message.globalEventListener.witherSpawn", pos.getX(), pos.getY(), pos.getZ())), false);
            } else if (eventId == 1038) { //SoundEvents.BLOCK_END_PORTAL_SPAWN
                player.sendMessage(new LiteralText(StringUtils.translate("tweakmyclient.message.globalEventListener.endPortalSpawn", pos.getX(), pos.getY(), pos.getZ())), false);
            } else if (eventId == 1028) { //SoundEvents.ENTITY_ENDER_DRAGON_DEATH
                player.sendMessage(new LiteralText(StringUtils.translate("tweakmyclient.message.globalEventListener.enderDragonDeath", pos.getX(), pos.getY(), pos.getZ())), false);
            }
        }
    }
}
