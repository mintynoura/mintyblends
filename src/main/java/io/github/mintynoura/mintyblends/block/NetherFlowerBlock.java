package io.github.mintynoura.mintyblends.block;

import io.github.mintynoura.mintyblends.registry.ModBlocks;
import io.github.mintynoura.mintyblends.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(ModTags.Blocks.CUREFLOWER_GROWN_ON) || floor.is(ModTags.Blocks.RENDFLOWER_GROWN_ON) || super.mayPlaceOn(floor, world, pos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return BonemealableBlock.hasSpreadableNeighbourPos(world, pos, state);
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos newPos;
        for (int i = 0; i < 16; i++) {
            newPos = pos.offset(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
            if (world.getBlockState(newPos).is(Blocks.AIR) && world.getBlockState(newPos.below()).is(ModTags.Blocks.RENDFLOWER_GROWN_ON) && random.nextInt(6) == 0) {
                world.setBlock(newPos, ModBlocks.RENDFLOWER.defaultBlockState(), Block.UPDATE_ALL);
            } else if (world.getBlockState(newPos).is(Blocks.AIR) && world.getBlockState(newPos.below()).is(ModTags.Blocks.CUREFLOWER_GROWN_ON) && random.nextInt(6) == 0) {
                world.setBlock(newPos, ModBlocks.CUREFLOWER.defaultBlockState(), Block.UPDATE_ALL);
            }
        }
    }
}
