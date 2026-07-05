package top.hendrixshen.tweakmyclient.impl.feature.customBlockHitBoxOverlay;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.lwjgl.opengl.GL11;
import top.hendrixshen.magiclib.api.compat.minecraft.client.MinecraftCompat;
import top.hendrixshen.magiclib.api.event.minecraft.render.RenderLevelListener;
import top.hendrixshen.magiclib.api.render.context.LevelRenderContext;
import top.hendrixshen.tweakmyclient.game.Configs;
import top.hendrixshen.tweakmyclient.mixin.accessor.MultiPlayerGameModeAccessor;
import top.hendrixshen.tweakmyclient.util.RenderUtil;

// CHECKSTYLE.OFF: ImportOrder
//#if MC >= 12105
//$$ import fi.dy.masa.malilib.util.data.Color4f;
//#else
import fi.dy.masa.malilib.util.Color4f;
//#endif
// CHECKSTYLE.ON: ImportOrder

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

// CHECKSTYLE.OFF: ImportOrder
//#if MC > 11404
import net.minecraft.client.multiplayer.ClientLevel;
//#else
//$$ import net.minecraft.client.multiplayer.MultiPlayerLevel;
//#endif
// CHECKSTYLE.ON: ImportOrder

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CustomBlockHitBoxRenderer implements RenderLevelListener {
    @Getter(lazy = true)
    private static final CustomBlockHitBoxRenderer instance = new CustomBlockHitBoxRenderer();

    @Override
    public void preRenderLevel(ClientLevel level, LevelRenderContext renderContext) {
        // NO-OP
    }

    @Override
    public void postRenderLevel(ClientLevel level, LevelRenderContext renderContext) {
        boolean shouldRenderFill = Configs.customBlockHitBoxOverlay.getBooleanValue();
        boolean shouldRenderOutline = Configs.customBlockHitBoxOutline.getBooleanValue();

        if (!shouldRenderFill && !shouldRenderOutline) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();

        if (!(mc.hitResult instanceof BlockHitResult)) {
            return;
        }

        BlockHitResult hitResult = (BlockHitResult) mc.hitResult;
        //#if MC >= 12110
        //$$ Entity cameraEntity = mc.getCameraEntity();
        //#else
        Entity cameraEntity = mc.cameraEntity;
        //#endif
        MultiPlayerGameMode multiPlayerGameMode = mc.gameMode;

        if (level == null || cameraEntity == null || multiPlayerGameMode == null) {
            return;
        }

        BlockPos blockPos = hitResult.getBlockPos();
        BlockState blockState = level.getBlockState(blockPos);
        VoxelShape voxelShape = blockState.getShape(level, hitResult.getBlockPos(), CollisionContext.of(cameraEntity));

        if (Configs.customBlockHitBoxLinkedAdapter.getBooleanValue()) {
            voxelShape = CustomBlockHitBoxRenderer.linkedBlockAdapter(level, blockState, blockPos, voxelShape);
        }

        // Adjust AABB for break animation.
        final float destroyProgress = ((MultiPlayerGameModeAccessor) multiPlayerGameMode).tmc$getDestroyProgress();
        BreakAnimationMode breakAnimationMode = (BreakAnimationMode) Configs.customBlockHitBoxBreakAnimation.getOptionListValue();

        switch (breakAnimationMode) {
            case DOWN:
                voxelShape = voxelShape.toAabbs().stream()
                        .map(box -> box.inflate(0, -box.getYsize() * destroyProgress / 2, 0)
                                .move(0, -box.getYsize() * destroyProgress / 2, 0))
                        .map(Shapes::create)
                        .reduce(Shapes::or)
                        .orElse(Shapes.empty()).optimize();
                break;
            case SHRINK:
                voxelShape = voxelShape.toAabbs().stream()
                        .map(box -> box.inflate(
                                -box.getXsize() * destroyProgress / 2,
                                -box.getYsize() * destroyProgress / 2,
                                -box.getZsize() * destroyProgress / 2))
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

        Vec3 vec3 = MinecraftCompat.getInstance().getMainCamera().getPosition();

        if (Configs.customBlockHitBoxOverlay.getBooleanValue()) {
            float k = System.currentTimeMillis()
                    % (100 * (101 - Configs.customBlockHitBoxOverlayRainbowSpeed.getIntegerValue()))
                    / (50F * (101 - Configs.customBlockHitBoxOverlayRainbowSpeed.getIntegerValue()));

            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(-1.0F, -1.0F);
            RenderUtil.renderShapeOverlay(voxelShape,
                    blockPos.getX() - vec3.x(), blockPos.getY() - vec3.y(), blockPos.getZ() - vec3.z(),
                    Configs.customBlockHitBoxOverlayRainbow.getBooleanValue() ? new Color4f(
                            0.5F + 0.5F * (float) Math.sin(k * Math.PI),
                            0.5F + 0.5F * (float) Math.sin((k + 4F / 3F) * Math.PI),
                            0.5F + 0.5F * (float) Math.sin((k + 8F / 3F) * Math.PI),
                            Configs.customBlockHitBoxOverlayColor.getColor().a
                    ) : Configs.customBlockHitBoxOverlayColor.getColor(),
                    !(breakAnimationMode.isForceDisableDepthTest() && Configs.customBlockHitBoxDepthTest.getBooleanValue())
            );
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        }

        if (Configs.customBlockHitBoxOutline.getBooleanValue()) {
            float k = System.currentTimeMillis()
                    % (100 * (101 - Configs.customBlockHitBoxOutlineRainbowSpeed.getIntegerValue()))
                    / (50F * (101 - Configs.customBlockHitBoxOutlineRainbowSpeed.getIntegerValue()));

            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(-1.0F, -1.0F);
            RenderUtil.renderShapeOutline(voxelShape,
                    blockPos.getX() - vec3.x(), blockPos.getY() - vec3.y(), blockPos.getZ() - vec3.z(),
                    Configs.customBlockHitBoxOutlineRainbow.getBooleanValue() ? new Color4f(
                            0.5F + 0.5F * (float) Math.sin(k * Math.PI),
                            0.5F + 0.5F * (float) Math.sin((k + 2F / 3F) * Math.PI),
                            0.5F + 0.5F * (float) Math.sin((k + 6F / 3F) * Math.PI),
                            Configs.customBlockHitBoxOutlineColor.getColor().a
                    ) : Configs.customBlockHitBoxOutlineColor.getColor(),
                    !(breakAnimationMode.isForceDisableDepthTest() && Configs.customBlockHitBoxDepthTest.getBooleanValue())
            );

            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        }
    }

    public static VoxelShape linkedBlockAdapter(ClientLevel clientLevel, BlockState blockState, BlockPos blockPos, VoxelShape shape) {
        try {
            if (blockState.getBlock() instanceof ChestBlock) {
                Block block = blockState.getBlock();
                Direction direction = ChestBlock.getConnectedDirection(blockState);
                BlockState connectBlock = clientLevel.getBlockState(blockPos.relative(direction, 1));

                if (connectBlock.getBlock() == block && blockPos.relative(direction, 1).relative(ChestBlock.getConnectedDirection(connectBlock)).equals(blockPos)) {
                    return Shapes.or(shape, connectBlock.getShape(clientLevel, blockPos).move(direction.getStepX(), direction.getStepY(), direction.getStepZ()));
                }
            } else if (blockState.getBlock() instanceof DoorBlock) {
                Block block = blockState.getBlock();

                if (clientLevel.getBlockState(blockPos.above(1)).getBlock() == block) {
                    BlockState connectBlock = clientLevel.getBlockState(blockPos.above(1));

                    if (connectBlock.getValue(DoorBlock.POWERED).equals(blockState.getValue(DoorBlock.POWERED))
                            && connectBlock.getValue(DoorBlock.FACING).equals(blockState.getValue(DoorBlock.FACING))
                            && connectBlock.getValue(DoorBlock.HINGE).equals(connectBlock.getValue(DoorBlock.HINGE))) {
                        return Shapes.or(shape, connectBlock.getShape(clientLevel, blockPos).move(0, 1, 0));
                    }
                } else if (clientLevel.getBlockState(blockPos.below(1)).getBlock() == block) {
                    BlockState connectBlock = clientLevel.getBlockState(blockPos.below(1));

                    if (connectBlock.getValue(DoorBlock.POWERED).equals(blockState.getValue(DoorBlock.POWERED))
                            && connectBlock.getValue(DoorBlock.FACING).equals(blockState.getValue(DoorBlock.FACING))
                            && connectBlock.getValue(DoorBlock.HINGE).equals(connectBlock.getValue(DoorBlock.HINGE))) {
                        return Shapes.or(shape, connectBlock.getShape(clientLevel, blockPos).move(0, -1, 0));
                    }
                }
            } else if (blockState.getBlock() instanceof BedBlock) {
                Block block = blockState.getBlock();
                Direction direction = blockState.getValue(HorizontalDirectionalBlock.FACING);
                BlockState connectBlock = clientLevel.getBlockState(blockPos.relative(direction));

                if (blockState.getValue(BedBlock.PART).equals(BedPart.FOOT)
                        && connectBlock.getBlock() == block
                        && connectBlock.getValue(BedBlock.PART).equals(BedPart.HEAD)) {
                    return Shapes.or(shape, connectBlock.getShape(clientLevel, blockPos).move(direction.getStepX(), direction.getStepY(), direction.getStepZ()));
                }

                connectBlock = clientLevel.getBlockState(blockPos.relative(direction.getOpposite()));
                direction = direction.getOpposite();

                if (blockState.getValue(BedBlock.PART).equals(BedPart.HEAD)
                        && connectBlock.getBlock() == block
                        && connectBlock.getValue(BedBlock.PART).equals(BedPart.FOOT)) {
                    return Shapes.or(shape, connectBlock.getShape(clientLevel, blockPos).move(direction.getStepX(), direction.getStepY(), direction.getStepZ()));
                }
            } else if (blockState.getBlock() instanceof PistonBaseBlock && blockState.getValue(PistonBaseBlock.EXTENDED)) {
                Block block = blockState.getBlock();
                Direction direction = blockState.getValue(DirectionalBlock.FACING);
                BlockState connectBlock = clientLevel.getBlockState(blockPos.relative(direction));

                if (connectBlock.getValue(PistonHeadBlock.TYPE).equals(block == Blocks.PISTON ? PistonType.DEFAULT : PistonType.STICKY)
                        && direction.equals(connectBlock.getValue(DirectionalBlock.FACING))) {
                    return Shapes.or(shape, connectBlock.getShape(clientLevel, blockPos).move(direction.getStepX(), direction.getStepY(), direction.getStepZ()));
                }
            } else if (blockState.getBlock() instanceof PistonHeadBlock) {
                Direction direction = blockState.getValue(DirectionalBlock.FACING);
                BlockState connectBlock = clientLevel.getBlockState(blockPos.relative(direction.getOpposite()));

                if (connectBlock.getBlock() instanceof PistonBaseBlock && direction == connectBlock.getValue(DirectionalBlock.FACING) && connectBlock.getValue(PistonBaseBlock.EXTENDED)) {
                    return Shapes.or(shape, connectBlock.getShape(clientLevel, blockPos.relative(direction.getOpposite())).move(direction.getOpposite().getStepX(), direction.getOpposite().getStepY(), direction.getOpposite().getStepZ()));
                }
            }
        } catch (Exception ignore) {
            // NO-OP
        }

        return shape;
    }
}
