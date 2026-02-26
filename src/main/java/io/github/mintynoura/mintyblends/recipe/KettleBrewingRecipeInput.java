package io.github.mintynoura.mintyblends.recipe;

import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public class KettleBrewingRecipeInput implements RecipeInput {

    private final NonNullList<ItemStack> stacks;
    private final StackedItemContents matcher = new StackedItemContents();
    private final int stackCount;



    public KettleBrewingRecipeInput(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;

        int i = 0;

        for (ItemStack itemStack : stacks) {
            if (!itemStack.isEmpty()) {
                i++;
                this.matcher.accountStack(itemStack, 1);
            }
        }

        this.stackCount = i;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.stacks.get(slot);
    }

    public List<ItemStack> getStacks() {
        return this.stacks;
    }

    public int getStackCount() {
        return this.stackCount;
    }

    public StackedItemContents getRecipeMatcher() {
        return this.matcher;
    }

    @Override
    public int size() {
        return this.stacks.size();
    }
}
