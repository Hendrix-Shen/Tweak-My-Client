package top.hendrixshen.tweakmyclient.util;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import top.hendrixshen.tweakmyclient.config.Configs;

public class MiscUtil extends top.hendrixshen.magiclib.util.MiscUtil {
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
            //#if MC >= 11700
            } else if (blockState.getBlock() instanceof PointedDripstoneBlock && Configs.expCustomBlockHitBoxOverlayLinkedAdapterSupportPointedDripstoneBlock) {
                Direction direction = blockState.getValue(PointedDripstoneBlock.TIP_DIRECTION);

                BlockPos connectBlockPos = blockPos.above();
                while (true) {
                    BlockState connectBlock = clientLevel.getBlockState(connectBlockPos);
                    if (connectBlock.getBlock() instanceof PointedDripstoneBlock && connectBlock.getValue(PointedDripstoneBlock.TIP_DIRECTION).equals(direction)) {
                        shape = Shapes.or(shape, connectBlock.getShape(clientLevel, connectBlockPos).move(0, connectBlockPos.getY() - blockPos.getY(), 0));
                        connectBlockPos = connectBlockPos.above();
                    } else {
                        break;
                    }
                }

                connectBlockPos = blockPos.below();
                while (true) {
                    BlockState connectBlock = clientLevel.getBlockState(connectBlockPos);
                    if (connectBlock.getBlock() instanceof PointedDripstoneBlock && connectBlock.getValue(PointedDripstoneBlock.TIP_DIRECTION).equals(direction)) {
                        shape = Shapes.or(shape, connectBlock.getShape(clientLevel, connectBlockPos).move(0, connectBlockPos.getY() - blockPos.getY(), 0));
                        connectBlockPos = connectBlockPos.below();
                    } else {
                        break;
                    }
                }

                return shape;
            //#endif
            }
        } catch (Exception ignore) {
        }
        return shape;
    }
}
