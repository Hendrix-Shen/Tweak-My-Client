package top.hendrixshen.tweakmyclient.util.render;

import com.mojang.blaze3d.systems.RenderSystem;
import fi.dy.masa.malilib.util.Color4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
//#if MC >= 11600
import net.minecraft.client.player.LocalPlayer;
//#endif
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
//#if MC >= 11600
import net.minecraft.world.entity.projectile.FishingHook;
//#endif
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.lwjgl.opengl.GL11;
import top.hendrixshen.tweakmyclient.config.Configs;
//#if MC >= 11600
import top.hendrixshen.tweakmyclient.fakeInterface.IFishingHookEntity;
//#endif
import top.hendrixshen.tweakmyclient.mixin.accessor.MultiPlayerGameModeAccessor;
import top.hendrixshen.tweakmyclient.util.MiscUtil;

public class OverlayRenderer {
    private static final OverlayRenderer INSTANCE = new OverlayRenderer();

    public static OverlayRenderer getInstance() {
        return INSTANCE;
    }

    //#if MC >= 11600
    public void renderOpenWater(Minecraft minecraft) {
        LocalPlayer localPlayer = minecraft.player;
        if (localPlayer != null) {
            FishingHook fishHook = localPlayer.fishing;
            if (fishHook != null) {
                BlockPos fishHookPos = fishHook.blockPosition();
                Color4f color = isInOpenWater(fishHook) ? Configs.colorWaterOpen : Configs.colorWaterShallow;
                BlockPos pos1 = new BlockPos(fishHookPos.getX() - 2, fishHookPos.getY() - 3, fishHookPos.getZ() - 2);
                BlockPos pos2 = new BlockPos(fishHookPos.getX() + 2, fishHookPos.getY(), fishHookPos.getZ() + 2);
                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                RenderUtil.renderAreaOutline(pos1, pos2, 3.0f, color, minecraft);
                GL11.glDisable(GL11.GL_LINE_SMOOTH);
            }
        }
    }

    private boolean isInOpenWater(FishingHook fishHook) {
        return ((IFishingHookEntity) fishHook).checkOpenWaterAround(fishHook.blockPosition());
    }
    //#endif

    public void renderBlockOverlay(Minecraft minecraft) {
        HitResult hitResult = minecraft.hitResult;
        Vec3 vec3 = minecraft.gameRenderer.getMainCamera().getPosition();
        if (hitResult instanceof BlockHitResult) {
            ClientLevel clientLevel = minecraft.level;
            Entity cameraEntity = minecraft.cameraEntity;
            MultiPlayerGameMode multiPlayerGameMode = minecraft.gameMode;
            if (clientLevel != null && cameraEntity != null && multiPlayerGameMode != null) {
                BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                BlockPos blockPos = blockHitResult.getBlockPos();
                BlockState blockState = clientLevel.getBlockState(blockPos);
                VoxelShape voxelShape = blockState.getShape(clientLevel, blockHitResult.getBlockPos(), CollisionContext.of(cameraEntity));

                if (Configs.customBlockHitBoxOverlayLinkedAdapter) {
                    voxelShape = MiscUtil.linkedBlockAdapter(clientLevel, blockState, blockPos, voxelShape);
                }

                final float destroyProgress = ((MultiPlayerGameModeAccessor) multiPlayerGameMode).getDestroyProgress();

                // Adjust AABB for break animation.
                switch (Configs.breakAnimationMode) {
                    case DOWN:
                        voxelShape = voxelShape.toAabbs().stream()
                                .map(box -> box.inflate(0.003, 0.003 - box.getYsize() * destroyProgress / 2, 0.003)
                                        .move(0, -box.getYsize() * destroyProgress / 2, 0))
                                .map(Shapes::create)
                                .reduce(Shapes::or)
                                .orElse(Shapes.empty()).optimize();
                        break;
                    case SHRINK:
                        voxelShape = voxelShape.toAabbs().stream()
                                .map(box -> box.inflate(0.003 - box.getXsize() * destroyProgress / 2,
                                        0.003 - box.getYsize() * destroyProgress / 2,
                                        0.003 - box.getZsize() * destroyProgress / 2))
                                .map(Shapes::create)
                                .reduce(Shapes::or)
                                .orElse(Shapes.empty()).optimize();
                        break;
                    case NONE:
                    default:
                        voxelShape = voxelShape.toAabbs().stream()
                                .map(box -> box.inflate(0.003, 0.003, 0.003))
                                .map(Shapes::create)
                                .reduce(Shapes::or)
                                .orElse(Shapes.empty()).optimize();
                }

                if (Configs.featureCustomBlockHitBoxOverlayFill) {
                    float k = System.currentTimeMillis() % (100 * (101 - Configs.customBlockHitBoxOverlayFillRainbowSpeed)) / (50F * (101 - Configs.customBlockHitBoxOverlayFillRainbowSpeed));

                    if (Configs.customBlockHitBoxOverlayDisableDepthTest) {
                        RenderSystem.disableDepthTest();
                    }

                    RenderUtil.renderShapeOverlay(voxelShape,
                            blockPos.getX() - vec3.x(), blockPos.getY() - vec3.y(), blockPos.getZ() - vec3.z(),
                            Configs.customBlockHitBoxOverlayFillRainbow ? new Color4f(
                                    0.5F + 0.5F * (float) Math.sin(k * Math.PI),
                                    0.5F + 0.5F * (float) Math.sin((k + 4F / 3F) * Math.PI),
                                    0.5F + 0.5F * (float) Math.sin((k + 8F / 3F) * Math.PI),
                                    Configs.colorBlockHitBoxOverlayFill.a
                            ) : Configs.colorBlockHitBoxOverlayFill);

                    if (Configs.customBlockHitBoxOverlayDisableDepthTest) {
                        RenderSystem.enableDepthTest();
                    }
                }

                if (Configs.featureCustomBlockHitBoxOverlayOutline) {
                    float k = System.currentTimeMillis() % (100 * (101 - Configs.customBlockHitBoxOverlayOutlineRainbowSpeed)) / (50F * (101 - Configs.customBlockHitBoxOverlayOutlineRainbowSpeed));

                    if (Configs.customBlockHitBoxOverlayDisableDepthTest) {
                        RenderSystem.disableDepthTest();
                    }

                    RenderUtil.renderShapeOutline(voxelShape,
                            1.0F,
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
                }
            }
        }
    }
}
