package io.github.mintynoura.mintyblends.block.entity;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.block.KettleBlock;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipe;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipeInput;
import io.github.mintynoura.mintyblends.registry.*;
import io.github.mintynoura.mintyblends.screen.KettleMenu;
import io.github.mintynoura.mintyblends.util.BlendUtils;
import io.github.mintynoura.mintyblends.util.MintyBlendsTags;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.*;
@NullMarked
public class KettleBlockEntity extends BlockEntity implements ImplementedInventory, Nameable, ExtendedMenuProvider<BlockPos> {

    private final NonNullList<ItemStack> inventory = NonNullList.withSize(6, ItemStack.EMPTY);
    private int progress = 0;
    private int brewTime;
    private static final int[] INGREDIENT_SLOTS = new int[]{0, 1, 2, 3};
    public static final int CONTAINER_SLOT = 4;
    public static final int OUTPUT_SLOT = 5;

    @Nullable
    private Component customName;
    protected final ContainerData containerData;

    public KettleBlockEntity(BlockPos pos, BlockState state) {
        super(MintyBlendsBlockEntities.KETTLE_BLOCK_ENTITY, pos, state);
        this.containerData = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> KettleBlockEntity.this.progress;
                    case 1 -> KettleBlockEntity.this.brewTime;
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
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    public NonNullList<ItemStack> getIngredients() {
        NonNullList<ItemStack> ingredients = NonNullList.createWithCapacity(4);
        for (int i = 0; i < 4; i++) {
            ItemStack itemStack = inventory.get(i);
            ingredients.add(itemStack);
        }
        return ingredients;
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        ContainerHelper.loadAllItems(view, inventory);
        progress = view.getIntOr("kettle.progress", 0);
        brewTime = view.getIntOr("kettle.brew_time", 0);
        customName = parseCustomNameSafe(view, "CustomName");
    }

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        ContainerHelper.saveAllItems(view, inventory);
        view.putInt("kettle.progress", progress);
        view.putInt("kettle.brew_time", brewTime);
        view.storeNullable("CustomName", ComponentSerialization.CODEC, this.customName);
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState oldState) {
        Containers.dropContents(level, pos, this);
        super.preRemoveSideEffects(pos, oldState);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayer serverPlayerEntity) {
        return this.worldPosition;
    }

    @Override
    public Component getName() {
        return this.customName != null ? this.customName : Component.translatableWithFallback("mintyblends.container.kettle", "Kettle");
    }

    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    @Override
    public @Nullable Component getCustomName() {
        return this.customName;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new KettleMenu(syncId, playerInventory, this, this.containerData);
    }


    public boolean isLit() {
        if (this.level == null) {
            return false;
        }
        return this.level.getBlockState(this.worldPosition).getValue(KettleBlock.LIT);
    }

    private void resetProgress() {
        this.progress = 0;
    }

    public boolean hasRecipe() {
        Optional<RecipeHolder<KettleBrewingRecipe>> recipeEntry = getRecipe();
        return recipeEntry.isPresent();
    }

    public Optional<RecipeHolder<KettleBrewingRecipe>> getRecipe() {
        if (level != null) {
            return level.getServer().getRecipeManager().getRecipeFor(MintyBlendsRecipes.KETTLE_BREWING_RECIPE_TYPE, new KettleBrewingRecipeInput(getIngredients()), level);
        } else return Optional.empty();
    }

    public int getBrewingTime(RecipeHolder<KettleBrewingRecipe> recipeEntry) {
        return recipeEntry.value().getBrewingTime();
    }

    public boolean canCraftRecipe(
            RecipeHolder<KettleBrewingRecipe> recipe,
            KettleBrewingRecipeInput recipeInput,
            NonNullList<ItemStack> inventory) {
        if (recipe != null) {
            ItemStack result = recipe.value().assemble(recipeInput);
            if (result.isEmpty() || (!ItemStack.isSameItemSameComponents(result, inventory.get(OUTPUT_SLOT)) && !inventory.get(OUTPUT_SLOT).isEmpty())) {
                return false;
            }
            ItemStack container = inventory.get(CONTAINER_SLOT);
            boolean hasContainer = ItemStack.isSameItemSameComponents(container, recipe.value().getContainer().create());
            int resultCount = inventory.get(OUTPUT_SLOT).getCount() + result.getCount();
            return hasContainer && resultCount <= result.getMaxStackSize();
        } else return false;
    }

    public boolean canBlend(KettleBrewingRecipeInput recipeInput, NonNullList<ItemStack> inventory, Level level) {
        ItemStack container = inventory.get(CONTAINER_SLOT);
        ItemStack itemStack;
        boolean hasContainer = false;
        boolean hasIngredients = false;
        if (!inventory.get(OUTPUT_SLOT).isEmpty() && !ItemStack.isSameItemSameComponents(BlendUtils.blendBrew(recipeInput, level.getRandom()), inventory.get(OUTPUT_SLOT))) {
            return false;
        }
        if (ItemStack.isSameItemSameComponents(container, KettleBrewingRecipe.defaultContainer.create())) {
            hasContainer = true;
        }
        if (recipeInput.getStackCount() < 1) {
            return false;
        } else {
            for (int i = 0; i < recipeInput.size(); i++) {
                itemStack = recipeInput.getItem(i);
                if (!itemStack.isEmpty()) {
                    if (!itemStack.is(MintyBlendsTags.Items.BLENDING_INGREDIENTS)) {
                        return false;
                    } else {
                        hasIngredients = true;
                    }
                }
            }
            return hasContainer && hasIngredients;
        }
    }

    public void tick(Level level, BlockPos pos, BlockState blockState, KettleBlockEntity entity) {
        boolean changed = blockState.getValue(KettleBlock.LIT) ^ entity.isLit();
        if (changed) {
            blockState = blockState.setValue(KettleBlock.LIT, entity.isLit());
            level.setBlock(pos, blockState, Block.UPDATE_ALL);
        }
        if (entity.isLit()) {
            if (hasRecipe() && canCraftRecipe(getRecipe().get(), new KettleBrewingRecipeInput(getIngredients()), inventory)) {
                entity.progress++;
                entity.brewTime = entity.getBrewingTime(getRecipe().get());
                setChanged(level, pos, blockState);
                if (entity.progress == entity.brewTime) {
                    craftRecipe(level, getRecipe().get(), new KettleBrewingRecipeInput(getIngredients()));
                    postCraft(level, blockState, entity);
                }
            } else if (!hasRecipe() && canBlend(new KettleBrewingRecipeInput(getIngredients()), inventory, level)) {
                entity.progress++;
                entity.brewTime = MintyBlends.CONFIG.kettleBlendBrewingTime.value();
                setChanged(level, pos, blockState);
                if (entity.progress == entity.brewTime) {
                    blend(new KettleBrewingRecipeInput(getIngredients()), level);
                    postCraft(level, blockState, entity);
                }
            } else entity.resetProgress();
        } else entity.resetProgress();
        if (entity.progress > 0) {
            entity.progress = Mth.clamp(entity.progress, 0, entity.brewTime);
        }
    }

    public void craftRecipe(
            Level level,
            RecipeHolder<KettleBrewingRecipe> recipe,
            KettleBrewingRecipeInput recipeInput
    ) {
        ItemStack result = recipe.value().assemble(recipeInput);
        if (level instanceof ServerLevel serverLevel) {
            for (ItemStack remainder : BlendUtils.recipeRemainders(recipeInput)) {
                DefaultDispenseItemBehavior.spawnItem(serverLevel, remainder, 6, Direction.UP, Vec3.atCenterOf(worldPosition));
            }
        }
        for (int i = 0; i < OUTPUT_SLOT; i++) {
            KettleBlockEntity.this.removeItem(i, 1);
        }
        if (this.getItem(OUTPUT_SLOT).isEmpty()) {
            this.setItem(OUTPUT_SLOT, result);
        } else {
            this.getItem(OUTPUT_SLOT).grow(result.getCount());
        }
    }

    public void blend(KettleBrewingRecipeInput recipeInput, Level level) {
        ItemStack herbalBrew = BlendUtils.blendBrew(recipeInput, level.getRandom());
        if (level instanceof ServerLevel serverLevel) {
            for (ItemStack remainder : BlendUtils.recipeRemainders(recipeInput)) {
                DefaultDispenseItemBehavior.spawnItem(serverLevel, remainder, 6, Direction.UP, Vec3.atCenterOf(worldPosition));
            }
        }
        for (int i = 0; i < OUTPUT_SLOT; i++) {
            KettleBlockEntity.this.removeItem(i, 1);
        }
        if (this.getItem(OUTPUT_SLOT).isEmpty()) {
            this.setItem(OUTPUT_SLOT, herbalBrew);
        } else {
            this.getItem(OUTPUT_SLOT).grow(1);
        }
    }

    public void postCraft(Level level, BlockState state, KettleBlockEntity entity) {
        RandomSource random = level.getRandom();
        Direction direction = state.getValue(HorizontalDirectionalBlock.FACING).getClockWise();
        double h = random.nextDouble() * 0.6 - 0.3;
        double i = direction.getAxis() == Direction.Axis.X ? direction.getStepX() * 0.52 : h;
        double k = direction.getAxis() == Direction.Axis.Z ? direction.getStepZ() * 0.52 : h;
        for (int count = 0; count < 3; count++) {
            ((ServerLevel) level).sendParticles(MintyBlendsParticleTypes.KETTLE_STEAM, worldPosition.getX() + 0.5 + i, worldPosition.getY() + 1, worldPosition.getZ() + 0.5 + k, 1, 0, 0.1, 0, 0);
        }
        entity.progress = 0;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return side == Direction.UP ? INGREDIENT_SLOTS : new int[]{OUTPUT_SLOT};
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter components) {
        super.applyImplicitComponents(components);
        this.customName = components.get(DataComponents.CUSTOM_NAME);
        components.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(this.getItems());
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(DataComponents.CUSTOM_NAME, this.customName);
        builder.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(this.getItems()));
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
}
