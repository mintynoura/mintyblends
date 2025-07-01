package io.github.mintynoura.mintyblends.block.entity;

import io.github.mintynoura.mintyblends.block.KettleBlock;
import io.github.mintynoura.mintyblends.item.component.HerbalBrewComponent;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipe;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipeInput;
import io.github.mintynoura.mintyblends.registry.ModBlockEntities;
import io.github.mintynoura.mintyblends.registry.ModComponents;
import io.github.mintynoura.mintyblends.registry.ModItems;
import io.github.mintynoura.mintyblends.registry.ModRecipes;
import io.github.mintynoura.mintyblends.screen.KettleScreenHandler;
import io.github.mintynoura.mintyblends.util.ModTags;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SuspiciousStewIngredient;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class KettleBlockEntity extends BlockEntity implements ImplementedInventory, Nameable, ExtendedScreenHandlerFactory<BlockPos> {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
    private int progress = 0;
    private int brewTime;
    private int litUses = 0;
    public static final int maxLitUses = 3;
    private static final int[] INGREDIENT_SLOTS = new int[]{0, 1, 2, 3};
    public static final int OUTPUT_SLOT = 4;

    @Nullable
    private Text customName;
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
        return inventory;
    }

    public DefaultedList<ItemStack> getIngredients() {
        DefaultedList<ItemStack> ingredients = DefaultedList.ofSize(4);
        for (int i = 0; i < 4; i++) {
            ItemStack itemStack = inventory.get(i);
            ingredients.add(itemStack);
        }
        return ingredients;
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        Inventories.readNbt(nbt, inventory, registries);
        progress = nbt.getInt("kettle.progress", 0);
        brewTime = nbt.getInt("kettle.brew_time", 0);
        litUses = nbt.getInt("kettle.lit_uses", 0);
        customName = tryParseCustomName(nbt.get("CustomName"), registries);
    }

    @Override
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, inventory, registries);
        nbt.putInt("kettle.progress", progress);
        nbt.putInt("kettle.brew_time", brewTime);
        nbt.putInt("kettle.lit_uses", litUses);
        if (customName != null) {
            nbt.put("CustomName", TextCodecs.CODEC.encodeStart(registries.getOps(NbtOps.INSTANCE), customName).getOrThrow());
        }
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
        return this.customName != null ? this.customName : Text.translatableWithFallback("mintyblends.container.kettle", "Kettle");
    }

    @Override
    public Text getDisplayName() {
        return this.getName();
    }

    @Override
    public @Nullable Text getCustomName() {
        return this.customName;
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

    private boolean hasRecipe() {
        Optional<RecipeEntry<KettleBrewingRecipe>> recipeEntry = getRecipe();
        return recipeEntry.isPresent();
    }

    private Optional<RecipeEntry<KettleBrewingRecipe>> getRecipe() {
        if (world != null) {
            return world.getServer().getRecipeManager().getFirstMatch(ModRecipes.KETTLE_BREWING_RECIPE_TYPE, new KettleBrewingRecipeInput(getIngredients()), world);
        } else return Optional.empty();
    }

    private int getBrewingTime(RecipeEntry<KettleBrewingRecipe> recipeEntry) {
        return recipeEntry.value().getBrewingTime();
    }

    private boolean canCraftRecipe(
            RegistryWrapper.WrapperLookup registries,
            RecipeEntry<KettleBrewingRecipe> recipe,
            KettleBrewingRecipeInput recipeInput,
            DefaultedList<ItemStack> inventory) {
        if (recipe != null) {
            ItemStack itemStack = recipe.value().craft(recipeInput, registries);
            if (itemStack.isEmpty()) {
                return false;
            } else {
                ItemStack container = inventory.get(OUTPUT_SLOT);
                return ItemStack.areItemsAndComponentsEqual(container, recipe.value().getContainer());
            }
        } else {
            return false;
        }
    }

    private boolean canBlend(KettleBrewingRecipeInput recipeInput, DefaultedList<ItemStack> inventory) {
        ItemStack container = inventory.get(OUTPUT_SLOT);
        ItemStack itemStack;
        boolean hasContainer = false;
        boolean hasHerbs = false;
        if (ItemStack.areItemsAndComponentsEqual(container, KettleBrewingRecipe.defaultContainer)) {
            hasContainer = true;
        }
        if (recipeInput.getStackCount() < 1) {
            return false;
        } else {
            for (int i = 0; i < recipeInput.size(); i++) {
                itemStack = recipeInput.getStackInSlot(i);
                if (!itemStack.isEmpty()) {
                    if (!itemStack.isIn(ModTags.Items.BLENDING_INGREDIENTS)) {
                        return false;
                    } else {
                        hasHerbs = true;
                    }
                }
            }
            return hasContainer && hasHerbs;
        }
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        boolean changed = state.get(KettleBlock.LIT) ^ this.isLit();
        if (changed) {
            state = state.with(KettleBlock.LIT, this.isLit());
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
        }
        if (this.isLit()) {
            if (!hasRecipe() && canBlend(new KettleBrewingRecipeInput(getIngredients()), inventory)) {
                this.progress++;
                this.brewTime = KettleBrewingRecipe.defaultBrewingTime;
                markDirty(world, pos, state);
                if (this.progress == this.brewTime) {
                    blend(new KettleBrewingRecipeInput(getIngredients()));
                    this.litUses -= 1;
                    this.progress = 0;
                }
            }
            if (hasRecipe() && canCraftRecipe(world.getRegistryManager(), getRecipe().get(), new KettleBrewingRecipeInput(getIngredients()), inventory)) {
                this.progress++;
                this.brewTime = this.getBrewingTime(getRecipe().get());
                markDirty(world, pos, state);
                if (this.progress == this.brewTime) {
                    craftRecipe(world.getRegistryManager(), getRecipe().get(), new KettleBrewingRecipeInput(getIngredients()));
                    this.litUses -= 1;
                    this.progress = 0;
                }
            }
        } else this.progress = 0;
        if (!hasRecipe() && !canBlend(new KettleBrewingRecipeInput(getIngredients()), inventory)) {
            this.progress = 0;
        }
    }

    private void craftRecipe(
            DynamicRegistryManager dynamicRegistryManager,
            RecipeEntry<KettleBrewingRecipe> recipe,
            KettleBrewingRecipeInput recipeInput
    ) {
            for (int i = 0; i <= OUTPUT_SLOT; i++) {
                KettleBlockEntity.this.removeStack(i, 1);
            }
            ItemStack result = recipe.value().craft(recipeInput, dynamicRegistryManager);
            this.setStack(OUTPUT_SLOT, new ItemStack(result.getItem(), result.getCount()));
    }

    private void blend(KettleBrewingRecipeInput recipeInput) {
        ItemStack itemStack;
        Set<StatusEffectInstance> statusEffectSet = new HashSet<>();
        Set<Identifier> herbalEffectSet = new HashSet<>();
        for (int i = 0; i < recipeInput.size(); i++) {
            itemStack = recipeInput.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                if (itemStack.isIn(ModTags.Items.BLENDING_INGREDIENTS)) {
                    if (itemStack.isIn(ItemTags.SMALL_FLOWERS) || itemStack.isIn(ModTags.Items.HERBS)) {
                        SuspiciousStewIngredient suspiciousStewIngredient = SuspiciousStewIngredient.of(itemStack.getItem());
                        if (suspiciousStewIngredient != null) {
                            StatusEffectInstance statusEffect = new StatusEffectInstance(suspiciousStewIngredient.getStewEffects().effects().getFirst().createStatusEffectInstance());
                            statusEffectSet.add(statusEffect);
                        }
                    } else if (itemStack.get(ModComponents.HERB_COMPONENT) != null) {
                        Identifier herbalEffect = itemStack.get(ModComponents.HERB_COMPONENT).herbalEffect();
                        herbalEffectSet.add(herbalEffect);
                    }
                }
            }
        }
        HerbalBrewComponent herbalBrewComponent = new HerbalBrewComponent(List.copyOf(herbalEffectSet), List.copyOf(statusEffectSet));
        ItemStack herbalBrew = new ItemStack(ModItems.HERBAL_BREW);
        herbalBrew.set(ModComponents.HERBAL_BREW_COMPONENT, herbalBrewComponent);
        for (int i = 0; i <= OUTPUT_SLOT; i++) {
            KettleBlockEntity.this.removeStack(i, 1);
        }
        this.setStack(OUTPUT_SLOT, herbalBrew);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return side == Direction.UP ? INGREDIENT_SLOTS : new int[]{OUTPUT_SLOT};
    }

    @Override
    protected void readComponents(ComponentsAccess components) {
        super.readComponents(components);
        this.customName = components.get(DataComponentTypes.CUSTOM_NAME);
        components.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).copyTo(this.getItems());
    }

    @Override
    protected void addComponents(ComponentMap.Builder builder) {
        super.addComponents(builder);
        builder.add(DataComponentTypes.CUSTOM_NAME, this.customName);
        builder.add(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(this.getItems()));
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
