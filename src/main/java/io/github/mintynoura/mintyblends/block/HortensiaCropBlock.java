package io.github.mintynoura.mintyblends.block;

import io.github.mintynoura.mintyblends.registry.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class HortensiaCropBlock extends PitcherCropBlock implements Fertilizable {
    public static final int MAX_AGE = 4;
    public static final IntProperty AGE = Properties.AGE_4;
    public static final EnumProperty<DoubleBlockHalf> HALF = TallPlantBlock.HALF;
    private final Function<BlockState, VoxelShape> shapeFunction = this.createShapeFunction();


    public HortensiaCropBlock(Settings settings) {
        super(settings);
    }

    private Function<BlockState, VoxelShape> createShapeFunction() {
        int[] is = new int[]{0, 9, 11, 22, 26};
        return this.createShapeFunction(state -> {
            int i = (state.get(AGE) == 0 ? 4 : 6) + is[state.get(AGE)];
            int j = state.get(AGE) == 0 ? 6 : 10;

            return switch (state.get(HALF)) {
                case LOWER -> Block.createColumnShape(j, -1.0, Math.min(16, -1 + i));
                case UPPER -> Block.createColumnShape(j, 0.0, Math.max(0, -1 + i - 16));
            };
        });
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.shapeFunction.apply(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }


    private static boolean canGrowAt(WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.isAir() || blockState.isOf(ModBlocks.HORTENSIA_CROP);
    }


    private void tryGrow(ServerWorld world, BlockState state, BlockPos pos, int amount) {
        int i = Math.min(state.get(AGE) + amount, 4);
        if (this.canGrow(world, pos, state, i)) {
            BlockState blockState = state.with(AGE, i);
            world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
            if (isDoubleTallAtAge(i)) {
                world.setBlockState(pos.up(), blockState.with(HALF, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL);
            }
        }
    }

    private boolean canGrow(WorldView world, BlockPos pos, BlockState state, int age) {
        return !this.isFullyGrown(state) && canPlaceAt(world, pos) && (!isDoubleTallAtAge(age) || canGrowAt(world, pos.up()));
    }

    private static boolean canPlaceAt(WorldView world, BlockPos pos) {
        return world.getBaseLightLevel(pos, 0) >= 8;
    }

    private boolean isFullyGrown(BlockState state) {
        return state.get(AGE) >= 4;
    }


    private static boolean isDoubleTallAtAge(int age) {
        return age >= 3;
    }


    private static boolean isLowerHalf(BlockState state) {
        return state.isOf(ModBlocks.HORTENSIA_CROP) && state.get(HALF) == DoubleBlockHalf.LOWER;
    }

    @Nullable
    private HortensiaCropBlock.LowerHalfContext getLowerHalfContext(WorldView world, BlockPos pos, BlockState state) {
        if (isLowerHalf(state)) {
            return new HortensiaCropBlock.LowerHalfContext(pos, state);
        } else {
            BlockPos blockPos = pos.down();
            BlockState blockState = world.getBlockState(blockPos);
            return isLowerHalf(blockState) ? new HortensiaCropBlock.LowerHalfContext(blockPos, blockState) : null;
        }
    }

    record LowerHalfContext(BlockPos pos, BlockState state) {
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        LowerHalfContext lowerHalfContext = this.getLowerHalfContext(world, pos, state);
        return lowerHalfContext != null && this.canGrow(world, lowerHalfContext.pos, lowerHalfContext.state, lowerHalfContext.state.get(AGE) + 1);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        HortensiaCropBlock.LowerHalfContext lowerHalfContext = this.getLowerHalfContext(world, pos, state);
        if (lowerHalfContext != null) {
            this.tryGrow(world, lowerHalfContext.state, lowerHalfContext.pos, 1);
        }
    }
}
