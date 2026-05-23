package io.github.mintynoura.mintyblends.block;

import io.github.mintynoura.mintyblends.util.MintyBlendsTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LilyPadBlock;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public class InfernalilyBlock extends LilyPadBlock implements SuspiciousEffectHolder, BonemealableBlock {
    public final SuspiciousStewEffects stewEffects;

    public InfernalilyBlock(SuspiciousStewEffects stewEffects, Properties properties) {
        super(properties);
        this.stewEffects = stewEffects;
    }

    public InfernalilyBlock(Holder<MobEffect> effect, float effectLengthInSeconds, Properties properties) {
        this(makeEffectList(effect, effectLengthInSeconds), properties);
    }

    protected static SuspiciousStewEffects makeEffectList(Holder<MobEffect> effect, float effectLengthInSeconds) {
        return new SuspiciousStewEffects(List.of(new SuspiciousStewEffects.Entry(effect, Mth.floor(effectLengthInSeconds * 20.0F))));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        FluidState fluidState = level.getFluidState(pos);
        FluidState fluidAbove = level.getFluidState(pos.above());
        return (fluidState.is(MintyBlendsTags.Fluids.SUPPORTS_INFERNALILY) || state.is(MintyBlendsTags.Blocks.SUPPORTS_INFERNALILY)) && fluidAbove.is(Fluids.EMPTY);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {

    }

    @Override
    public SuspiciousStewEffects getSuspiciousEffects() {
        return this.stewEffects;
    }
}
