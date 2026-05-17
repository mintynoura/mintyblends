package io.github.mintynoura.mintyblends.block;

import io.github.mintynoura.mintyblends.registry.MintyBlendsBlocks;
import io.github.mintynoura.mintyblends.util.MintyBlendsTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class NetherFlowerBlock extends FlowerBlock implements BonemealableBlock {
    public NetherFlowerBlock(Holder<MobEffect> stewEffect, float effectLengthInSeconds, Properties settings) {
        super(stewEffect, effectLengthInSeconds, settings);
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter level, BlockPos pos) {
        return floor.is(MintyBlendsTags.Blocks.CUREFLOWER_GROWN_ON) || floor.is(MintyBlendsTags.Blocks.RENDFLOWER_GROWN_ON) || super.mayPlaceOn(floor, level, pos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return BonemealableBlock.hasSpreadableNeighbourPos(level, pos, state);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos newPos;
        for (int i = 0; i < 16; i++) {
            newPos = pos.offset(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
            if (level.isEmptyBlock(newPos) && level.getBlockState(newPos.below()).is(MintyBlendsTags.Blocks.RENDFLOWER_GROWN_ON) && random.nextInt(6) == 0) {
                level.setBlockAndUpdate(newPos, MintyBlendsBlocks.RENDFLOWER.defaultBlockState());
            } else if (level.isEmptyBlock(newPos) && level.getBlockState(newPos.below()).is(MintyBlendsTags.Blocks.CUREFLOWER_GROWN_ON) && random.nextInt(6) == 0) {
                level.setBlockAndUpdate(newPos, MintyBlendsBlocks.CUREFLOWER.defaultBlockState());
            }
        }
    }
}
