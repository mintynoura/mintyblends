package io.github.mintynoura.mintyblends.block;

import io.github.mintynoura.mintyblends.util.MintyBlendsTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LilyPadBlock;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class InfernalilyBlock extends LilyPadBlock implements SuspiciousEffectHolder {
    public final SuspiciousStewEffects stewEffects;

    public InfernalilyBlock(SuspiciousStewEffects stewEffects, Properties properties) {
        super(properties);
        this.stewEffects = stewEffects;
    }

    public InfernalilyBlock(Holder<MobEffect> effect, float effectLengthInSeconds, Properties properties) {
        this(makeEffectList(effect, effectLengthInSeconds), properties);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (itemStack.is(MintyBlendsTags.Items.INFERNALILY_FERTILIZERS) && canSpread(level, pos, state)) {
            if (level instanceof ServerLevel serverLevel) {
                spread(serverLevel, pos, state);
                itemStack.causeUseVibration(player, GameEvent.ITEM_INTERACT_FINISH);
                // TODO: new sound
                level.levelEvent(1505, pos, 15);
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 12, 0.25, 0.25, 0.25, 0);
                return InteractionResult.SUCCESS_SERVER;
            }
        }
        return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
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

    public static boolean dispenserSpread(ItemStack itemStack, Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof InfernalilyBlock block && block.canSpread(level, pos, state)) {
            if (level instanceof ServerLevel serverLevel) {
                block.spread(serverLevel, pos, state);
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 12, 0.25, 0.25, 0.25, 0);
                itemStack.shrink(1);
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean canSpread(LevelReader level, BlockPos pos, BlockState state) {
        return BonemealableBlock.hasSpreadableNeighbourPos(level, pos, state);
    }

    public void spread(ServerLevel level, BlockPos pos, BlockState state) {
        BonemealableBlock.findSpreadableNeighbourPos(level, pos, state).ifPresent(blockPos -> level.setBlockAndUpdate(blockPos, this.defaultBlockState()));
    }

    @Override
    public SuspiciousStewEffects getSuspiciousEffects() {
        return this.stewEffects;
    }
}
