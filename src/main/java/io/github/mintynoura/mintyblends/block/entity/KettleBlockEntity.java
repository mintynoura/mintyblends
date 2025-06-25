package io.github.mintynoura.mintyblends.block.entity;

import io.github.mintynoura.mintyblends.block.KettleBlock;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipe;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipeInput;
import io.github.mintynoura.mintyblends.registry.ModBlockEntities;
import io.github.mintynoura.mintyblends.registry.ModRecipes;
import io.github.mintynoura.mintyblends.screen.KettleScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class KettleBlockEntity extends BlockEntity implements ImplementedInventory, Nameable, SidedInventory, ExtendedScreenHandlerFactory<BlockPos> {

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(5, ItemStack.EMPTY);
    private int progress = 0;
    private int brewTime;
    private int litUses = 0;
    private final int maxLitUses = 3;
    private static final int OUTPUT_SLOT = 4;

    public boolean canBeLit = this.litUses != this.maxLitUses;

    protected final PropertyDelegate propertyDelegate;

    public KettleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.KETTLE_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> KettleBlockEntity.this.progress;
                    case 1 -> KettleBlockEntity.this.brewTime;
                    case 2 -> KettleBlockEntity.this.litUses;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        KettleBlockEntity.this.progress = value;
                        break;
                    case 1:
                        KettleBlockEntity.this.brewTime = value;
                        break;
                    case 2:
                        KettleBlockEntity.this.litUses = value;
                        break;
                }
            }

            @Override
            public int size() {
                return 3;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        Inventories.readNbt(nbt, items, registries);
        progress = nbt.getInt("kettle.progress", 0);
        brewTime = nbt.getInt("kettle.brew_time", 0);
        litUses = nbt.getInt("kettle.lit_uses", 0);
    }

    @Override
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, items, registries);
        nbt.putInt("kettle.progress", progress);
        nbt.putInt("kettle.brew_time", brewTime);
        nbt.putInt("kettle.lit_uses", litUses);
    }

    @Override
    public void onBlockReplaced(BlockPos pos, BlockState oldState) {
        ItemScatterer.spawn(world, pos, this);
        super.onBlockReplaced(pos, oldState);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public Text getName() {
        return this.getCustomName() != null ? this.getCustomName() : Text.translatableWithFallback("mintyblends.container.kettle", "Kettle");
    }

    @Override
    public Text getDisplayName() {
        return Text.translatableWithFallback("mintyblends.container.kettle", "Kettle");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new KettleScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void light() {
        this.litUses = maxLitUses;
    }

    public boolean isLit() {
        return this.litUses > 0;
    }

    private boolean canCraftRecipe() {
        Optional<RecipeEntry<KettleBrewingRecipe>> recipeEntry = getRecipe();
        return recipeEntry.isPresent() && this.isLit();
    }

    private Optional<RecipeEntry<KettleBrewingRecipe>> getRecipe() {
        if (world != null) {
            return world.getServer().getRecipeManager().getFirstMatch(ModRecipes.KETTLE_BREWING_RECIPE_TYPE, new KettleBrewingRecipeInput(items), world);
        } else return Optional.empty();
    }

    private int getBrewingTime(RecipeEntry<KettleBrewingRecipe> recipeEntry) {
        return recipeEntry.value().getBrewingTime();
    }

    private boolean canAcceptRecipeOutput(
            RegistryWrapper.WrapperLookup registries,
            RecipeEntry<KettleBrewingRecipe> recipe,
            KettleBrewingRecipeInput recipeInput,
            DefaultedList<ItemStack> inventory,
            int maxCount
    ) {
        if (recipe != null) {
            ItemStack itemStack = recipe.value().craft(recipeInput, registries);
            if (itemStack.isEmpty()) {
                return false;
            } else {
                ItemStack itemStack2 = inventory.get(OUTPUT_SLOT);
                if (itemStack2.isEmpty()) {
                    return true;
                } else if (!ItemStack.areItemsAndComponentsEqual(itemStack2, itemStack)) {
                    return false;
                } else {
                    return itemStack2.getCount() < maxCount && itemStack2.getCount() < itemStack2.getMaxCount() || itemStack2.getCount() < itemStack.getMaxCount();
                }
            }
        } else {
            return false;
        }
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        boolean changed = state.get(KettleBlock.LIT) ^ this.isLit();
        if (changed) {
            state = state.with(KettleBlock.LIT, this.isLit());
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
        }
        if (canCraftRecipe() && canAcceptRecipeOutput(world.getRegistryManager(), getRecipe().get(), new KettleBrewingRecipeInput(items), items, getMaxCountPerStack())) {
            this.progress++;
            this.brewTime = this.getBrewingTime(getRecipe().get());
            markDirty(world, pos, state);
            if (this.progress == this.brewTime) {
                craftRecipe(world.getRegistryManager(), getRecipe().get(), new KettleBrewingRecipeInput(items));
                this.litUses -= 1;
                this.progress = 0;
            }
        } else {
            KettleBlockEntity.this.progress = 0;
        }
    }

    private void craftRecipe(
            DynamicRegistryManager dynamicRegistryManager,
            RecipeEntry<KettleBrewingRecipe> recipe,
            KettleBrewingRecipeInput recipeInput
    ) {
            for (int i = 0; i < OUTPUT_SLOT; i++) {
                KettleBlockEntity.this.removeStack(i, 1);
            }
            ItemStack result = recipe.value().craft(recipeInput, dynamicRegistryManager);
            this.setStack(OUTPUT_SLOT, new ItemStack(result.getItem(), KettleBlockEntity.this.getStack(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }
}
