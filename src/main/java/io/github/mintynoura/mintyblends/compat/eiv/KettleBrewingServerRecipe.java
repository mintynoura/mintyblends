package io.github.mintynoura.mintyblends.compat.eiv;

import de.crafty.eiv.common.api.recipe.EivRecipeType;
import de.crafty.eiv.common.api.recipe.IEivServerRecipe;
import io.github.mintynoura.mintyblends.MintyBlends;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.List;

public class KettleBrewingServerRecipe implements IEivServerRecipe {


    private List<Ingredient> ingredients;
    private ItemStack container;
    private ItemStack result;
    private int brewingTime;


    public static final EivRecipeType<KettleBrewingServerRecipe> TYPE = EivRecipeType.register(Identifier.of(MintyBlends.MOD_ID, "kettle_brewing"),
            () -> new KettleBrewingServerRecipe(List.of(), ItemStack.EMPTY, ItemStack.EMPTY, 0));

    public KettleBrewingServerRecipe(List<Ingredient> ingredients, ItemStack container, ItemStack result, int brewingTime) {
        this.ingredients = ingredients;
        this.container = container;
        this.result = result;
        this.brewingTime = brewingTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public ItemStack getContainer() {
        return container;
    }

    public int getBrewingTime() {
        return brewingTime;
    }

    public ItemStack getResult() {
        return result;
    }

    @Override
    public void writeToTag(NbtCompound nbtCompound) {

    }

    @Override
    public void loadFromTag(NbtCompound nbtCompound) {

    }

    @Override
    public EivRecipeType<? extends IEivServerRecipe> getRecipeType() {
        return TYPE;
    }
}
