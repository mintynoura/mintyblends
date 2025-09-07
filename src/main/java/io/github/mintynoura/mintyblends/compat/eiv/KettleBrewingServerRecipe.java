package io.github.mintynoura.mintyblends.compat.eiv;

import de.crafty.eiv.common.api.recipe.EivRecipeType;
import de.crafty.eiv.common.api.recipe.IEivServerRecipe;
import de.crafty.eiv.common.recipe.util.EivTagUtil;
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
    public void writeToTag(NbtCompound nbt) {
        nbt.put("ingredients", EivTagUtil.writeList(this.ingredients, ((ingredient, nbtCompound) -> EivTagUtil.writeIngredient(ingredient))));
        nbt.put("container", EivTagUtil.encodeItemStackOnServer(this.container));
        nbt.put("result", EivTagUtil.encodeItemStackOnServer(this.result));
        nbt.putInt("brewing_time", this.brewingTime);
    }

    @Override
    public void loadFromTag(NbtCompound nbt) {
        this.ingredients = EivTagUtil.readList(nbt, "ingredients", EivTagUtil::readIngredient);
        this.container = EivTagUtil.decodeItemStackOnServer(nbt.getCompound("container").orElseGet(NbtCompound::new));
        this.result = EivTagUtil.decodeItemStackOnServer(nbt.getCompound("result").orElseGet(NbtCompound::new));
        this.brewingTime = nbt.getInt("brewing_time", 0);
    }

    @Override
    public EivRecipeType<? extends IEivServerRecipe> getRecipeType() {
        return TYPE;
    }
}