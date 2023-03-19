package top.hendrixshen.tweakmyclient.util.render;

import com.mojang.blaze3d.systems.RenderSystem;
import fi.dy.masa.malilib.util.Color4f;
import lombok.Getter;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.lwjgl.opengl.GL11;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.mixin.accessor.MultiPlayerGameModeAccessor;
import top.hendrixshen.tweakmyclient.util.MiscUtil;

public class CustomBlockHitBoxRenderer implements IRenderer {
    @Getter(lazy = true)
    private static final CustomBlockHitBoxRenderer instance = new CustomBlockHitBoxRenderer();

    @Override
    public void render() {
        HitResult hitResult = TweakMyClient.getMinecraftClient().hitResult;

        if (!(hitResult instanceof BlockHitResult)) {
            return;
        }

        ClientLevel clientLevel = TweakMyClient.getMinecraftClient().level;
        Entity cameraEntity = TweakMyClient.getMinecraftClient().cameraEntity;
        MultiPlayerGameMode multiPlayerGameMode = TweakMyClient.getMinecraftClient().gameMode;

        if (clientLevel == null || cameraEntity == null || multiPlayerGameMode == null) {
            return;
        }

        BlockHitResult blockHitResult = (BlockHitResult) hitResult;
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState blockState = clientLevel.getBlockState(blockPos);
        VoxelShape voxelShape = blockState.getShape(clientLevel, blockHitResult.getBlockPos(), CollisionContext.of(cameraEntity));

        if (Configs.customBlockHitBoxOverlayLinkedAdapter) {
            voxelShape = MiscUtil.linkedBlockAdapter(clientLevel, blockState, blockPos, voxelShape);
        }

        // Adjust AABB for break animation.
        final float destroyProgress = ((MultiPlayerGameModeAccessor) multiPlayerGameMode).getDestroyProgress();

        switch (Configs.breakAnimationMode) {
            case DOWN:
                voxelShape = voxelShape.toAabbs().stream()
                        .map(box -> box.inflate(0, - box.getYsize() * destroyProgress / 2, 0)
                                .move(0, -box.getYsize() * destroyProgress / 2, 0))
                        .map(Shapes::create)
                        .reduce(Shapes::or)
                        .orElse(Shapes.empty()).optimize();
                break;
            case SHRINK:
                voxelShape = voxelShape.toAabbs().stream()
                        .map(box -> box.inflate(box.getXsize() * destroyProgress / 2,
                                box.getYsize() * destroyProgress / 2,
                                box.getZsize() * destroyProgress / 2))
                        .map(Shapes::create)
                        .reduce(Shapes::or)
                        .orElse(Shapes.empty()).optimize();
                break;
            case NONE:
            default:
                voxelShape = voxelShape.toAabbs().stream()
                        .map(Shapes::create)
                        .reduce(Shapes::or)
                        .orElse(Shapes.empty()).optimize();
        }

        Vec3 vec3 = TweakMyClient.getMinecraftClient().gameRenderer.getMainCamera().getPosition();

        if (Configs.featureCustomBlockHitBoxOverlayFill) {
            float k = System.currentTimeMillis() % (100 * (101 - Configs.customBlockHitBoxOverlayFillRainbowSpeed)) / (50F * (101 - Configs.customBlockHitBoxOverlayFillRainbowSpeed));

            if (Configs.customBlockHitBoxOverlayDisableDepthTest) {
                RenderSystem.disableDepthTest();
            }

            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(-1.0F, -1.0F);
            RenderUtil.renderShapeOverlay(voxelShape,
                    blockPos.getX() - vec3.x(), blockPos.getY() - vec3.y(), blockPos.getZ() - vec3.z(),
                    Configs.customBlockHitBoxOverlayFillRainbow ? new Color4f(
                            0.5F + 0.5F * (float) Math.sin(k * Math.PI),
                            0.5F + 0.5F * (float) Math.sin((k + 4F / 3F) * Math.PI),
                            0.5F + 0.5F * (float) Math.sin((k + 8F / 3F) * Math.PI),
                            Configs.colorBlockHitBoxOverlayFill.a
                    ) : Configs.colorBlockHitBoxOverlayFill);
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);

            if (Configs.customBlockHitBoxOverlayDisableDepthTest) {
                RenderSystem.enableDepthTest();
            }
        }

        if (Configs.featureCustomBlockHitBoxOverlayOutline) {
            float k = System.currentTimeMillis() % (100 * (101 - Configs.customBlockHitBoxOverlayOutlineRainbowSpeed)) / (50F * (101 - Configs.customBlockHitBoxOverlayOutlineRainbowSpeed));

            if (Configs.customBlockHitBoxOverlayDisableDepthTest) {
                RenderSystem.disableDepthTest();
            }

            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(-1.0F, -1.0F);
            RenderUtil.renderShapeOutline(voxelShape,
                    blockPos.getX() - vec3.x(), blockPos.getY() - vec3.y(), blockPos.getZ() - vec3.z(),
                    Configs.customBlockHitBoxOverlayOutlineRainbow ? new Color4f(
                            0.5F + 0.5F * (float) Math.sin(k * Math.PI),
                            0.5F + 0.5F * (float) Math.sin((k + 2F / 3F) * Math.PI),
                            0.5F + 0.5F * (float) Math.sin((k + 6F / 3F) * Math.PI),
                            Configs.colorBlockHitBoxOverlayOutline.a
                    ) : Configs.colorBlockHitBoxOverlayOutline);

            if (Configs.customBlockHitBoxOverlayDisableDepthTest) {
                RenderSystem.enableDepthTest();
            }
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        }
    }

    @Override
    public boolean shouldRender() {
        return (Configs.featureCustomBlockHitBoxOverlayFill || Configs.featureCustomBlockHitBoxOverlayOutline) &&
                TweakMyClient.getMinecraftClient().hitResult != null;
    }
}
