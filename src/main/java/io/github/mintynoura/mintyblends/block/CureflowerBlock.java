package io.github.mintynoura.mintyblends.block;

import io.github.mintynoura.mintyblends.registry.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class CureflowerBlock extends FlowerBlock implements Fertilizable {
    public CureflowerBlock(RegistryEntry<StatusEffect> stewEffect, float effectLengthInSeconds, Settings settings) {
        super(stewEffect, effectLengthInSeconds, settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.NYLIUM) || floor.isOf(Blocks.SOUL_SOIL) || super.canPlantOnTop(floor, world, pos);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return Fertilizable.canSpread(world, pos, state);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos newPos;
        for (int i = 0; i < 16; i++) {
            newPos = pos.add(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
            if (world.getBlockState(newPos).isOf(Blocks.AIR) && world.getBlockState(newPos.down()).isOf(Blocks.WARPED_NYLIUM) && random.nextInt(6) == 0) {
                world.setBlockState(newPos, ModBlocks.RENDFLOWER.getDefaultState(), Block.NOTIFY_ALL);
            } else if (world.getBlockState(newPos).isOf(Blocks.AIR) && world.getBlockState(newPos.down()).isOf(Blocks.CRIMSON_NYLIUM) && random.nextInt(6) == 0) {
                world.setBlockState(newPos, ModBlocks.CUREFLOWER.getDefaultState(), Block.NOTIFY_ALL);
            }
        }
    }
}
