package io.github.mintynoura.mintyblends.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;

public class KettleBrewingRecipeInput implements RecipeInput {

    private final DefaultedList<ItemStack> stacks;
    private final RecipeFinder matcher = new RecipeFinder();
    private final int stackCount;



    public KettleBrewingRecipeInput(DefaultedList<ItemStack> stacks) {
        this.stacks = stacks;

        int i = 0;

        for (ItemStack itemStack : stacks) {
            if (!itemStack.isEmpty()) {
                i++;
                this.matcher.addInput(itemStack, 1);
            }
        }

        this.stackCount = i;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.stacks.get(slot);
    }

    public List<ItemStack> getStacks() {
        return this.stacks;
    }

    public int getStackCount() {
        return this.stackCount;
    }

    public RecipeFinder getRecipeMatcher() {
        return this.matcher;
    }

    @Override
    public int size() {
        return this.stacks.size();
    }
}
