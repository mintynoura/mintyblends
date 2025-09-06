package io.github.mintynoura.mintyblends.block.entity;

import io.github.mintynoura.mintyblends.block.KettleBlock;
import io.github.mintynoura.mintyblends.item.component.HerbalBrewComponent;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipe;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipeInput;
import io.github.mintynoura.mintyblends.registry.*;
import io.github.mintynoura.mintyblends.screen.KettleScreenHandler;
import io.github.mintynoura.mintyblends.util.ModTags;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.SuspiciousStewIngredient;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.item.consume.UseAction;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class KettleBlockEntity extends BlockEntity implements ImplementedInventory, Nameable, ExtendedScreenHandlerFactory<BlockPos> {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
    private int progress = 0;
    private int brewTime;
    private int litUses = 0;
    public static final int maxLitUses = 4;
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
    protected void readData(ReadView view) {
        super.readData(view);
        Inventories.readData(view, inventory);
        progress = view.getInt("kettle.progress", 0);
        brewTime = view.getInt("kettle.brew_time", 0);
        litUses = view.getInt("kettle.lit_uses", 0);
        customName = tryParseCustomName(view, "CustomName");
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        Inventories.writeData(view, inventory);
        view.putInt("kettle.progress", progress);
        view.putInt("kettle.brew_time", brewTime);
        view.putInt("kettle.lit_uses", litUses);
        view.putNullable("CustomName", TextCodecs.CODEC, this.customName);
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
        } else return false;
    }

    private boolean canBlend(KettleBrewingRecipeInput recipeInput, DefaultedList<ItemStack> inventory) {
        ItemStack container = inventory.get(OUTPUT_SLOT);
        ItemStack itemStack;
        boolean hasContainer = false;
        boolean hasIngredients = false;
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
                        hasIngredients = true;
                    }
                }
            }
            return hasContainer && hasIngredients;
        }
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        boolean changed = state.get(KettleBlock.LIT) ^ this.isLit();
        if (changed) {
            state = state.with(KettleBlock.LIT, this.isLit());
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
        }
        if (this.isLit()) {
            if (hasRecipe() && canCraftRecipe(world.getRegistryManager(), getRecipe().get(), new KettleBrewingRecipeInput(getIngredients()), inventory)) {
                this.progress++;
                this.brewTime = this.getBrewingTime(getRecipe().get());
                markDirty(world, pos, state);
                if (this.progress == this.brewTime) {
                    craftRecipe(world.getRegistryManager(), getRecipe().get(), new KettleBrewingRecipeInput(getIngredients()));
                    postCraft(world, state);
                }
            } else if (!hasRecipe() && canBlend(new KettleBrewingRecipeInput(getIngredients()), inventory)) {
                this.progress++;
                this.brewTime = KettleBrewingRecipe.defaultBrewingTime;
                markDirty(world, pos, state);
                if (this.progress == this.brewTime) {
                    blend(new KettleBrewingRecipeInput(getIngredients()));
                   postCraft(world, state);
                }
            } else this.progress = 0;
        } else this.progress = 0;
    }

    private void craftRecipe(
            DynamicRegistryManager dynamicRegistryManager,
            RecipeEntry<KettleBrewingRecipe> recipe,
            KettleBrewingRecipeInput recipeInput
    ) {
        List<ItemStack> recipeRemainders = new ArrayList<>();
        for (int i = 0; i < recipeInput.size(); i++) {
            Item item = recipeInput.getStackInSlot(i).getItem();
            recipeRemainders.add(item.getRecipeRemainder());
        }
        for (int i = 0; i <= OUTPUT_SLOT; i++) {
            KettleBlockEntity.this.removeStack(i, 1);
        }
        ItemStack result = recipe.value().craft(recipeInput, dynamicRegistryManager);
        this.setStack(OUTPUT_SLOT, result);
        for (ItemStack remainder : recipeRemainders) {
            ItemDispenserBehavior.spawnItem(world, remainder, 6, Direction.UP, Vec3d.ofCenter(pos));
        }
    }

    private void blend(KettleBrewingRecipeInput recipeInput) {
        ItemStack itemStack;
        Set<StatusEffectInstance> statusEffectSet = new HashSet<>();
        Set<Identifier> herbalEffectSet = new HashSet<>();
        Set<String> ingredientSet = new HashSet<>();
        List<ItemStack> recipeRemainders = new ArrayList<>();
        List<ConsumeEffect> consumeEffects = new ArrayList<>();
        for (int i = 0; i < recipeInput.size(); i++) {
            Item item = recipeInput.getStackInSlot(i).getItem();
            recipeRemainders.add(item.getRecipeRemainder());
        }
        for (int i = 0; i < recipeInput.size(); i++) {
            itemStack = recipeInput.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                if (itemStack.isIn(ModTags.Items.BLENDING_INGREDIENTS)) {
                    if (itemStack.getItem() != Items.AIR) {
                        ingredientSet.add(itemStack.getItem().getTranslationKey());
                    }
                    if (itemStack.isIn(ItemTags.SMALL_FLOWERS) || itemStack.isIn(ModTags.Items.HERBS)) {
                        SuspiciousStewIngredient suspiciousStewIngredient = SuspiciousStewIngredient.of(itemStack.getItem());
                        if (suspiciousStewIngredient != null) {
                            StatusEffectInstance statusEffect = new StatusEffectInstance(suspiciousStewIngredient.getStewEffects().effects().getFirst().createStatusEffectInstance());
                            statusEffectSet.add(statusEffect);
                        }
                    }
                    if (itemStack.contains(ModComponents.HERB_COMPONENT)) {
                        Identifier herbalEffect = itemStack.get(ModComponents.HERB_COMPONENT).herbalEffect();
                        herbalEffectSet.add(herbalEffect);
                    }
                    if (itemStack.contains(DataComponentTypes.CONSUMABLE)) {
                        consumeEffects.addAll(itemStack.get(DataComponentTypes.CONSUMABLE).onConsumeEffects());
                    }
                }
            }
        }
        HerbalBrewComponent herbalBrewComponent = new HerbalBrewComponent(List.copyOf(herbalEffectSet), List.copyOf(statusEffectSet), List.copyOf(ingredientSet));
        ConsumableComponent consumableComponent = new ConsumableComponent(1.6f, UseAction.DRINK, SoundEvents.ENTITY_GENERIC_DRINK, false, consumeEffects);
        ItemStack herbalBrew = new ItemStack(ModItems.HERBAL_BREW);
        herbalBrew.set(ModComponents.HERBAL_BREW_COMPONENT, herbalBrewComponent);
        herbalBrew.set(DataComponentTypes.CONSUMABLE, consumableComponent);
        for (int i = 0; i < OUTPUT_SLOT; i++) {
            KettleBlockEntity.this.removeStack(i, 1);
        }
        this.setStack(OUTPUT_SLOT, herbalBrew);
        for (ItemStack remainder : recipeRemainders) {
            ItemDispenserBehavior.spawnItem(world, remainder, 6, Direction.UP, Vec3d.ofCenter(pos));
        }
    }

    private void postCraft(World world, BlockState state) {
        Random random = world.getRandom();
        Direction direction = state.get(HorizontalFacingBlock.FACING).rotateYClockwise();
        double h = random.nextDouble() * 0.6 - 0.3;
        double i = direction.getAxis() == Direction.Axis.X ? direction.getOffsetX() * 0.52 : h;
        double k = direction.getAxis() == Direction.Axis.Z ? direction.getOffsetZ() * 0.52 : h;
        for (int count = 0; count < 3; count++) {
            ((ServerWorld) world).spawnParticles(ModParticleTypes.KETTLE_STEAM, pos.getX() + 0.5 + i, pos.getY() + 1, pos.getZ() + 0.5 + k, 1, 0, 0.1, 0, 0);
        }
        if (this.litUses == 1) {
            world.playSound(
                    null,
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    ModSoundEvents.BLOCK_KETTLE_EXTINGUISH,
                    SoundCategory.BLOCKS,
                    0.7F + random.nextFloat(),
                    random.nextFloat() * 0.7F + 0.5f
            );
        }
        this.litUses -= 1;
        this.progress = 0;
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
