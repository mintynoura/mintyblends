package io.github.mintynoura.mintyblends.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class HerbBlock extends VegetationBlock implements SuspiciousEffectHolder, BonemealableBlock {

    protected static final MapCodec<SuspiciousStewEffects> EFFECTS_FIELD = SuspiciousStewEffects.CODEC.fieldOf("suspicious_stew_effects");
    public static final MapCodec<HerbBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(EFFECTS_FIELD.forGetter(HerbBlock::getSuspiciousEffects), propertiesCodec()).apply(instance, HerbBlock::new)
    );
    private final SuspiciousStewEffects stewEffects;
    private static final VoxelShape SHAPE = Block.column(10.0, 0.0, 16.0);

    public HerbBlock(Holder<MobEffect> stewEffect, float effectLengthInSeconds, BlockBehaviour.Properties settings) {
        this(createStewEffectList(stewEffect, effectLengthInSeconds), settings);
    }

    protected HerbBlock(SuspiciousStewEffects stewEffects, BlockBehaviour.Properties properties) {
        super(properties);
        this.stewEffects = stewEffects;
    }

    protected static SuspiciousStewEffects createStewEffectList(Holder<MobEffect> effect, float effectLengthInSeconds) {
        return new SuspiciousStewEffects(List.of(new SuspiciousStewEffects.Entry(effect, Mth.floor(effectLengthInSeconds * 20.0F))));
    }

    @Override
    protected MapCodec<? extends VegetationBlock> codec() {
        return CODEC;
    }

    @Override
    public SuspiciousStewEffects getSuspiciousEffects() {
        return this.stewEffects;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.move(state.getOffset(pos));
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        BonemealableBlock.findSpreadableNeighbourPos(world, pos, state).ifPresent(posx -> world.setBlockAndUpdate(posx, this.defaultBlockState()));
    }
}
