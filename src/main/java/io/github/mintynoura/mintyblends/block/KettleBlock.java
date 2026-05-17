package io.github.mintynoura.mintyblends.block;

import com.mojang.serialization.MapCodec;
import io.github.mintynoura.mintyblends.block.entity.KettleBlockEntity;
import io.github.mintynoura.mintyblends.registry.MintyBlendsBlockEntities;
import io.github.mintynoura.mintyblends.registry.MintyBlendsParticleTypes;
import io.github.mintynoura.mintyblends.registry.MintyBlendsSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.Map;

@NullMarked
public class KettleBlock extends BaseEntityBlock {

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    private static final VoxelShape BASE_SHAPE = Shapes.or(
            Block.column(10.0, 0.0, 6.0),
            Block.column(8.0, 6.05, 14.0),
            Block.column(4.0, 14.0, 15.0));
    private static final Map<Direction, VoxelShape> OUTLINE_SHAPES_BY_DIRECTION = Shapes.rotateHorizontal(Shapes.or(
            Shapes.box(0.125, 0.5, 0.4375, 0.25, 0.8125, 0.5625),
            Shapes.box(0.8125, 0.5, 0.4375, 0.9375, 0.8125, 0.5625),
            Shapes.box(0.75, 0.5, 0.4375, 0.8125, 0.625, 0.5625),
            Shapes.box(0.9375, 0.6875, 0.4375, 1, 0.8125, 0.5625),
            BASE_SHAPE));

    public static final MapCodec<KettleBlock> CODEC = KettleBlock.simpleCodec(KettleBlock::new);

    public KettleBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    public static boolean canBeLit(BlockState state, Level level, BlockPos pos) {
        return tryLight(state, level, pos);
    }

    private static boolean tryLight(BlockState state, Level level, BlockPos pos) {
        KettleBlockEntity blockEntity = (KettleBlockEntity) level.getBlockEntity(pos);
        if (!state.getValue(LIT)) {
            blockEntity.light();
            return true;
        } else return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return OUTLINE_SHAPES_BY_DIRECTION.get(state.getValue(FACING));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BASE_SHAPE;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new KettleBlockEntity(pos, state);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                          Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldStack = player.getItemInHand(hand);
        boolean isFireCharge = heldStack.getItem() instanceof FireChargeItem;
        boolean isFlintAndSteel = heldStack.getItem() instanceof FlintAndSteelItem;
        KettleBlockEntity blockEntity = (KettleBlockEntity) level.getBlockEntity(pos);
        if ((isFireCharge || isFlintAndSteel) && !state.getValue(LIT)) {
            blockEntity.light();
            if (isFireCharge) {
                level.playSound(player, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F + 1.0F);
                player.getItemInHand(hand).shrink(1);
            } else {
                level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                player.getItemInHand(hand).hurtWithoutBreaking(1, player);
            }
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            return InteractionResult.SUCCESS;
        }
        if (!level.isClientSide()) {
            player.openMenu(state.getMenuProvider(level, pos));
            player.awardStat(MintyBlendsBlockEntities.INTERACT_WITH_KETTLE);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            if (random.nextFloat() < 0.11f) {
                level.playLocalSound(
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        MintyBlendsSoundEvents.BLOCK_KETTLE_AMBIENT,
                        SoundSource.BLOCKS,
                        0.5F + random.nextFloat(),
                        random.nextFloat() * 0.7F + 0.5f,
                        false
                );
            }
           if (random.nextFloat() < 0.05f) {
               Direction direction = state.getValue(FACING).getClockWise();
               double h = random.nextDouble() * 0.6 - 0.3;
               double i = direction.getAxis() == Direction.Axis.X ? direction.getStepX() * 0.52 : h;
               double k = direction.getAxis() == Direction.Axis.Z ? direction.getStepZ() * 0.52 : h;
               level.addParticle(MintyBlendsParticleTypes.KETTLE_STEAM, pos.getX() + 0.5 + i, pos.getY() + 1, pos.getZ() + 0.5 + k, 0.0, 0.07, 0.0);
           }
        }
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }


    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }
        return createTickerHelper(type, MintyBlendsBlockEntities.KETTLE_BLOCK_ENTITY, (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }
}
