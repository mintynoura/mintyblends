package io.github.mintynoura.mintyblends.block;

import io.github.mintynoura.mintyblends.util.MintyBlendsTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LilyPadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class LavaLotusBlock extends LilyPadBlock {

    public LavaLotusBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        FluidState fluidState = level.getFluidState(pos);
        FluidState fluidAbove = level.getFluidState(pos.above());
        return (fluidState.is(MintyBlendsTags.Fluids.SUPPORTS_LAVA_LOTUS) || state.is(MintyBlendsTags.Blocks.SUPPORTS_LAVA_LOTUS)) && fluidAbove.is(Fluids.EMPTY);
    }
}
