package top.hendrixshen.TweakMyClient.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/*
 * This class file is from the RayTraceUtils file of litematica project, which is modified again
 *
 * */
public class RayTraceUtils {
    @Nullable
    public static BlockPos getTargetedPosition(World world, Entity player, double maxDistance, boolean sneakToOffset) {
        HitResult trace = getRayTraceFromEntity(world, player, false, maxDistance);
        if (trace.getType() != HitResult.Type.BLOCK) {
            return null;
        }
        BlockHitResult traceBlock = (BlockHitResult) trace;
        BlockPos pos = traceBlock.getBlockPos();
        // Sneaking puts the position adjacent the targeted block face, not sneaking puts it inside the targeted block
        if (sneakToOffset == player.isSneaking()) {
            pos = pos.offset(traceBlock.getSide());
        }
        return pos;
    }

    @Nonnull
    public static HitResult getRayTraceFromEntity(World world, Entity entity, boolean useLiquids, double range) {
        Vec3d eyesPos = entity.getCameraPosVec(1f);
        Vec3d rangedLookRot = entity.getRotationVec(1f).multiply(range);
        Vec3d lookEndPos = eyesPos.add(rangedLookRot);
        RaycastContext.FluidHandling fluidMode = useLiquids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE;
        HitResult result = rayTraceBlocks(world, eyesPos, lookEndPos, false, false, 1000);
        net.minecraft.util.math.Box bb = entity.getBoundingBox().expand(rangedLookRot.x, rangedLookRot.y, rangedLookRot.z).expand(1d, 1d, 1d);
        List<Entity> list = world.getOtherEntities(entity, bb);
        double closest = result != null && result.getType() == HitResult.Type.BLOCK ? eyesPos.distanceTo(result.getPos()) : Double.MAX_VALUE;
        Entity targetEntity = null;
        Optional<Vec3d> optional = Optional.empty();
        for (Entity entityTmp : list) {
            Optional<Vec3d> optionalTmp = entityTmp.getBoundingBox().raycast(eyesPos, lookEndPos);
            if (optionalTmp.isPresent()) {
                double distance = eyesPos.distanceTo(optionalTmp.get());

                if (distance <= closest) {
                    targetEntity = entityTmp;
                    optional = optionalTmp;
                    closest = distance;
                }
            }
        }
        if (targetEntity != null) {
            result = new EntityHitResult(targetEntity, optional.get());
        }
        if (result == null || eyesPos.distanceTo(result.getPos()) > range) {
            result = BlockHitResult.createMissed(Vec3d.ZERO, Direction.UP, BlockPos.ORIGIN);
        }
        return result;
    }

    /**
     * Mostly copy pasted from World#rayTraceBlocks() except for the added maxSteps argument and the layer range check
     */
    @Nullable
    public static BlockHitResult rayTraceBlocks(World world, Vec3d start, Vec3d end,
                                                boolean ignoreBlockWithoutBoundingBox,
                                                boolean returnLastUncollidableBlock, int maxSteps) {
        if (Double.isNaN(start.x) || Double.isNaN(start.y) || Double.isNaN(start.z) ||
                Double.isNaN(end.x) || Double.isNaN(end.y) || Double.isNaN(end.z)) {
            return null;
        }
        RayTraceCalcsData data = new RayTraceCalcsData(start, end);
        BlockState blockState = world.getBlockState(data.blockPos);
        BlockHitResult trace = traceFirstStep(data, world, blockState, ignoreBlockWithoutBoundingBox);
        if (trace != null) {
            return trace;
        }
        while (--maxSteps >= 0) {
            if (rayTraceCalcs(data, returnLastUncollidableBlock)) {
                return data.trace;
            }
            blockState = world.getBlockState(data.blockPos);
            if (traceLoopSteps(data, world, blockState, ignoreBlockWithoutBoundingBox)) {
                return data.trace;
            }
        }
        return returnLastUncollidableBlock ? data.trace : null;
    }

    @Nullable
    private static BlockHitResult traceFirstStep(RayTraceCalcsData data,
                                                 World world, BlockState blockState,
                                                 boolean ignoreBlockWithoutBoundingBox) {
        if (!ignoreBlockWithoutBoundingBox || !blockState.getCollisionShape(world, data.blockPos).isEmpty()) {
            VoxelShape blockShape = blockState.getOutlineShape(world, data.blockPos);
            boolean blockCollidable = !blockShape.isEmpty();
            if (blockCollidable) {
                return blockShape.raycast(data.start, data.end, data.blockPos);
            }
        }
        return null;
    }

    private static boolean traceLoopSteps(RayTraceCalcsData data,
                                          World world, BlockState blockState,
                                          boolean ignoreBlockWithoutBoundingBox) {
        if (!ignoreBlockWithoutBoundingBox || blockState.getMaterial() == Material.PORTAL ||
                !blockState.getCollisionShape(world, data.blockPos).isEmpty()) {
            VoxelShape blockShape = blockState.getOutlineShape(world, data.blockPos);
            boolean blockCollidable = !blockShape.isEmpty();
            BlockHitResult traceTmp = null;
            if (blockCollidable) {
                traceTmp = blockShape.raycast(data.start, data.end, data.blockPos);
            }
            if (traceTmp != null) {
                data.trace = traceTmp;
                return true;
            }
        }
        return false;
    }

    private static boolean rayTraceCalcs(RayTraceCalcsData data, boolean returnLastNonCollidableBlock) {
        boolean xDiffers = true;
        boolean yDiffers = true;
        boolean zDiffers = true;
        double nextX = 999.0D;
        double nextY = 999.0D;
        double nextZ = 999.0D;
        if (Double.isNaN(data.currentX) || Double.isNaN(data.currentY) || Double.isNaN(data.currentZ)) {
            data.trace = null;
            return true;
        }
        if (data.x == data.xEnd && data.y == data.yEnd && data.z == data.zEnd) {
            if (!returnLastNonCollidableBlock) {
                data.trace = null;
            }
            return true;
        }
        if (data.xEnd > data.x) {
            nextX = (double) data.x + 1.0D;
        } else if (data.xEnd < data.x) {
            nextX = (double) data.x + 0.0D;
        } else {
            xDiffers = false;
        }
        if (data.yEnd > data.y) {
            nextY = (double) data.y + 1.0D;
        } else if (data.yEnd < data.y) {
            nextY = (double) data.y + 0.0D;
        } else {
            yDiffers = false;
        }
        if (data.zEnd > data.z) {
            nextZ = (double) data.z + 1.0D;
        } else if (data.zEnd < data.z) {
            nextZ = (double) data.z + 0.0D;
        } else {
            zDiffers = false;
        }
        double relStepX = 999.0D;
        double relStepY = 999.0D;
        double relStepZ = 999.0D;
        double distToEndX = data.end.x - data.currentX;
        double distToEndY = data.end.y - data.currentY;
        double distToEndZ = data.end.z - data.currentZ;
        if (xDiffers) {
            relStepX = (nextX - data.currentX) / distToEndX;
        }
        if (yDiffers) {
            relStepY = (nextY - data.currentY) / distToEndY;
        }
        if (zDiffers) {
            relStepZ = (nextZ - data.currentZ) / distToEndZ;
        }
        if (relStepX == -0.0D) {
            relStepX = -1.0E-4D;
        }
        if (relStepY == -0.0D) {
            relStepY = -1.0E-4D;
        }
        if (relStepZ == -0.0D) {
            relStepZ = -1.0E-4D;
        }
        if (relStepX < relStepY && relStepX < relStepZ) {
            data.facing = data.xEnd > data.x ? Direction.WEST : Direction.EAST;
            data.currentX = nextX;
            data.currentY += distToEndY * relStepX;
            data.currentZ += distToEndZ * relStepX;
        } else if (relStepY < relStepZ) {
            data.facing = data.yEnd > data.y ? Direction.DOWN : Direction.UP;
            data.currentX += distToEndX * relStepY;
            data.currentY = nextY;
            data.currentZ += distToEndZ * relStepY;
        } else {
            data.facing = data.zEnd > data.z ? Direction.NORTH : Direction.SOUTH;
            data.currentX += distToEndX * relStepZ;
            data.currentY += distToEndY * relStepZ;
            data.currentZ = nextZ;
        }
        data.x = MathHelper.floor(data.currentX) - (data.facing == Direction.EAST ? 1 : 0);
        data.y = MathHelper.floor(data.currentY) - (data.facing == Direction.UP ? 1 : 0);
        data.z = MathHelper.floor(data.currentZ) - (data.facing == Direction.SOUTH ? 1 : 0);
        data.blockPos = new BlockPos(data.x, data.y, data.z);
        return false;
    }

    public static class RayTraceCalcsData {
        public final Vec3d start;
        public final Vec3d end;
        public final int xEnd;
        public final int yEnd;
        public final int zEnd;
        public int x;
        public int y;
        public int z;
        public double currentX;
        public double currentY;
        public double currentZ;
        public BlockPos blockPos;
        public Direction facing;
        public BlockHitResult trace;

        public RayTraceCalcsData(Vec3d start, Vec3d end) {
            this.start = start;
            this.end = end;
            this.currentX = start.x;
            this.currentY = start.y;
            this.currentZ = start.z;
            this.xEnd = MathHelper.floor(end.x);
            this.yEnd = MathHelper.floor(end.y);
            this.zEnd = MathHelper.floor(end.z);
            this.x = MathHelper.floor(start.x);
            this.y = MathHelper.floor(start.y);
            this.z = MathHelper.floor(start.z);
            this.blockPos = new BlockPos(x, y, z);
            this.trace = null;
        }
    }
}
