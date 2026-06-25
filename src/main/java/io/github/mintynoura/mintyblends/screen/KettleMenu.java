package io.github.mintynoura.mintyblends.screen;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.block.entity.KettleBlockEntity;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipe;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipeInput;
import io.github.mintynoura.mintyblends.registry.MintyBlendsMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class KettleMenu extends RecipeBookMenu {
    private final Container container;
    public final KettleBlockEntity kettleBlockEntity;
    private final ContainerData containerData;
    private static final Identifier EMPTY_CONTAINER_SLOT_TEXTURE = Identifier.fromNamespaceAndPath(MintyBlends.ID, "container/kettle/empty_container");


    public KettleMenu(int containerId, Inventory inventory, BlockPos pos) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(pos), new SimpleContainerData(3));
    }

    public KettleMenu(int containerId, Inventory inventory, BlockEntity kettleBlockEntity, ContainerData containerData) {
        super(MintyBlendsMenus.KETTLE_MENU, containerId);
        this.container = (Container) kettleBlockEntity;
        this.kettleBlockEntity = (KettleBlockEntity) kettleBlockEntity;
        this.containerData = containerData;

        this.addSlot(new Slot(this.container, 0, 31, 26));
        this.addSlot(new Slot(this.container, 1, 49, 26));
        this.addSlot(new Slot(this.container, 2, 31, 44));
        this.addSlot(new Slot(this.container, 3, 49, 44));
        this.addSlot(new ContainerSlot(this.container, 4, 80, 35));
        this.addSlot(new Slot(this.container, 5, 116, 35));

        int m;
        int l;
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(inventory, m, 8 + m * 18, 142));
        }

        addDataSlots(containerData);
    }

    public NonNullList<ItemStack> getIngredients() {
        NonNullList<ItemStack> ingredients = NonNullList.createWithCapacity(4);
        for (int i = 0; i < 4; i++) {
            ItemStack itemStack = this.container.getItem(i);
            ingredients.add(itemStack);
        }
        return ingredients;
    }


    public boolean isBrewing(){
        return containerData.get(0) > 0;
    }

    public boolean isLit() {
        return this.kettleBlockEntity.isLit();
    }

    public int getArrowProgress() {
        int progress = this.containerData.get(0);
        int brewTime = this.containerData.get(1);
        int arrowPixelSize = 44;

        return brewTime != 0 && progress != 0 ? progress * arrowPixelSize / brewTime : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            if (slotIndex < this.container.getContainerSize()) {
                if (!this.moveItemStackTo(originalStack, this.container.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, this.container.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return newStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PostPlaceAction handlePlacement(boolean useMaxItems, boolean allowDroppingItemsToClear, RecipeHolder<?> recipe, ServerLevel level, Inventory inventory) {
        return ServerPlaceRecipe.placeRecipe(new ServerPlaceRecipe.CraftingMenuAccess<>() {
            @Override
            public void fillCraftSlotsStackedContents(StackedItemContents stackedContents) {
                KettleMenu.this.fillCraftSlotsStackedContents(stackedContents);
            }

            @Override
            public void clearCraftingContent() {
                for (int i = 0; i < 4; i++) {
                    KettleMenu.this.getSlot(i).set(ItemStack.EMPTY);
                }
            }

            @Override
            public boolean recipeMatches(RecipeHolder<KettleBrewingRecipe> recipe) {
                return recipe.value().matches(new KettleBrewingRecipeInput(KettleMenu.this.kettleBlockEntity.getIngredients()), level);
            }
        }, 2, 2, getInputSlots(), getInputSlots(), inventory, ((RecipeHolder<KettleBrewingRecipe>) recipe), useMaxItems, allowDroppingItemsToClear);
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedItemContents stackedContents) {
        for (int i = 0; i < this.container.getContainerSize(); i++) {
            stackedContents.accountSimpleStack(this.container.getItem(i));
        }
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.MINTYBLENDS_KETTLE_BREWING;
    }

    public Slot getResultSlot() {
        return this.slots.get(5);
    }

    public List<Slot> getInputSlots() {
        return this.slots.subList(0, 4);
    }

    static class ContainerSlot extends Slot {
        public ContainerSlot(Container inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public @Nullable Identifier getNoItemIcon() {
            return EMPTY_CONTAINER_SLOT_TEXTURE;
        }
    }
}
