package io.github.mintynoura.mintyblends.block;

import com.mojang.serialization.MapCodec;
import io.github.mintynoura.mintyblends.block.entity.KettleBlockEntity;
import io.github.mintynoura.mintyblends.registry.ModBlockEntities;
import io.github.mintynoura.mintyblends.registry.ModParticleTypes;
import io.github.mintynoura.mintyblends.registry.ModSoundEvents;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class KettleBlock extends BlockWithEntity {

    public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty LIT = Properties.LIT;

    private static final VoxelShape BASE_SHAPE = VoxelShapes.union(
            Block.createColumnShape(10.0, 0.0, 6.0),
            Block.createColumnShape(8.0, 6.05, 14.0),
            Block.createColumnShape(4.0, 14.0, 15.0));
    private static final Map<Direction, VoxelShape> OUTLINE_SHAPES_BY_DIRECTION = VoxelShapes.createHorizontalFacingShapeMap(VoxelShapes.union(
            VoxelShapes.cuboid(0.125, 0.5, 0.4375, 0.25, 0.8125, 0.5625),
            VoxelShapes.cuboid(0.8125, 0.5, 0.4375, 0.9375, 0.8125, 0.5625),
            VoxelShapes.cuboid(0.75, 0.5, 0.4375, 0.8125, 0.625, 0.5625),
            VoxelShapes.cuboid(0.9375, 0.6875, 0.4375, 1, 0.8125, 0.5625),
            BASE_SHAPE));

    public static final MapCodec<KettleBlock> CODEC = KettleBlock.createCodec(KettleBlock::new);

    public KettleBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(LIT, false));
    }

    public static boolean canBeLit(BlockState state, World world, BlockPos pos) {
        return tryLight(state, world, pos);
    }

    private static boolean tryLight(BlockState state, World world, BlockPos pos) {
        KettleBlockEntity blockEntity = (KettleBlockEntity) world.getBlockEntity(pos);
        if (!state.get(LIT)) {
            blockEntity.light();
            return true;
        } else return false;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPES_BY_DIRECTION.get(state.get(FACING));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BASE_SHAPE;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new KettleBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
                                         PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack heldStack = player.getStackInHand(hand);
        boolean isFireCharge = heldStack.getItem() instanceof FireChargeItem;
        boolean isFlintAndSteel = heldStack.getItem() instanceof FlintAndSteelItem;
        KettleBlockEntity blockEntity = (KettleBlockEntity) world.getBlockEntity(pos);
        if ((isFireCharge || isFlintAndSteel) && !state.get(LIT)) {
            blockEntity.light();
            if (isFireCharge) {
                world.playSound(player, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 1.0F);
                player.getStackInHand(hand).decrement(1);
            } else {
                world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                player.getStackInHand(hand).damage(1, player);
            }
            world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            return ActionResult.SUCCESS;
        }
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = blockEntity;
            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            if (random.nextFloat() < 0.11f) {
                world.playSoundClient(
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        ModSoundEvents.BLOCK_KETTLE_AMBIENT,
                        SoundCategory.BLOCKS,
                        0.5F + random.nextFloat(),
                        random.nextFloat() * 0.7F + 0.5f,
                        false
                );
            }
           if (random.nextFloat() < 0.05f) {
               Direction direction = state.get(FACING).rotateYClockwise();
               double h = random.nextDouble() * 0.6 - 0.3;
               double i = direction.getAxis() == Direction.Axis.X ? direction.getOffsetX() * 0.52 : h;
               double k = direction.getAxis() == Direction.Axis.Z ? direction.getOffsetZ() * 0.52 : h;
               world.addParticleClient(ModParticleTypes.KETTLE_STEAM, pos.getX() + 0.5 + i, pos.getY() + 1, pos.getZ() + 0.5 + k, 0.0, 0.07, 0.0);
           }
        }
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient) {
            return null;
        }
        return validateTicker(type, ModBlockEntities.KETTLE_BLOCK_ENTITY, (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }
}
