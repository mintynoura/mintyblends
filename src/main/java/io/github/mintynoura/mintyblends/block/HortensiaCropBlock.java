package io.github.mintynoura.mintyblends.block;

import io.github.mintynoura.mintyblends.registry.MintyBlendsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.PitcherCropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;
@NullMarked
public class HortensiaCropBlock extends PitcherCropBlock {
    public static final int MAX_AGE = 4;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_4;
    public static final EnumProperty<DoubleBlockHalf> HALF = DoublePlantBlock.HALF;
    private final Function<BlockState, VoxelShape> shapeFunction = this.makeShapes();


    public HortensiaCropBlock(Properties settings) {
        super(settings);
    }

    private Function<BlockState, VoxelShape> makeShapes() {
        int[] is = new int[]{5, 10, 17, 22, 22};
        return this.getShapeForEachState(state -> {
            int i = 6 + is[state.getValue(AGE)];
            return switch (state.getValue(HALF)) {
                case LOWER -> Block.column(10, -1.0, Math.min(16, -1 + i));
                case UPPER -> Block.column(10, 0.0, Math.max(0, -1 + i - 16));
            };
        });
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.shapeFunction.apply(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    private static boolean canGrowInto(LevelReader level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        return blockState.isAir() || blockState.is(MintyBlendsBlocks.HORTENSIA_CROP);
    }


    private void grow(ServerLevel level, BlockState state, BlockPos pos, int amount) {
        int i = Math.min(state.getValue(AGE) + amount, 4);
        if (this.canGrow(level, pos, state, i)) {
            BlockState blockState = state.setValue(AGE, i);
            level.setBlock(pos, blockState, Block.UPDATE_CLIENTS);
            if (isDouble(i)) {
                level.setBlock(pos.above(), blockState.setValue(HALF, DoubleBlockHalf.UPPER), Block.UPDATE_ALL);
            }
        }
    }

    private boolean canGrow(LevelReader level, BlockPos pos, BlockState state, int age) {
        return !this.isMaxAge(state) && sufficientLight(level, pos) && (!isDouble(age) || canGrowInto(level, pos.above()));
    }

    private static boolean sufficientLight(LevelReader level, BlockPos pos) {
        return level.getRawBrightness(pos, 0) >= 8;
    }

    private boolean isMaxAge(BlockState state) {
        return state.getValue(AGE) >= 4;
    }


    private static boolean isDouble(int age) {
        return age >= 2;
    }


    private static boolean isLower(BlockState state) {
        return state.is(MintyBlendsBlocks.HORTENSIA_CROP) && state.getValue(HALF) == DoubleBlockHalf.LOWER;
    }

    @Nullable
    private HortensiaCropBlock.LowerHalfContext getLowerHalfContext(LevelReader level, BlockPos pos, BlockState state) {
        if (isLower(state)) {
            return new HortensiaCropBlock.LowerHalfContext(pos, state);
        } else {
            BlockPos blockPos = pos.below();
            BlockState blockState = level.getBlockState(blockPos);
            return isLower(blockState) ? new HortensiaCropBlock.LowerHalfContext(blockPos, blockState) : null;
        }
    }

    record LowerHalfContext(BlockPos pos, BlockState state) {
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        LowerHalfContext lowerHalfContext = this.getLowerHalfContext(level, pos, state);
        return lowerHalfContext != null && this.canGrow(level, lowerHalfContext.pos, lowerHalfContext.state, lowerHalfContext.state.getValue(AGE) + 1);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        HortensiaCropBlock.LowerHalfContext lowerHalfContext = this.getLowerHalfContext(level, pos, state);
        if (lowerHalfContext != null) {
            this.grow(level, lowerHalfContext.state, lowerHalfContext.pos, 1);
        }
    }
}
