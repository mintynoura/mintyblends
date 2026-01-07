package io.github.mintynoura.mintyblends.compat.rrv;

import cc.cassian.rrv.api.TagUtil;
import cc.cassian.rrv.api.recipe.ReliableServerRecipe;
import cc.cassian.rrv.api.recipe.ReliableServerRecipeType;
import io.github.mintynoura.mintyblends.MintyBlends;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.List;

public class KettleBrewingServerRecipe implements ReliableServerRecipe {

    private List<Ingredient> ingredients;
    private ItemStack container;
    private ItemStack result;
    private int brewingTime;


    public static final ReliableServerRecipeType<KettleBrewingServerRecipe> TYPE = ReliableServerRecipeType.register(Identifier.of(MintyBlends.MOD_ID, "kettle_brewing"),
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
        nbt.put("ingredients", TagUtil.writeList(this.ingredients, ((ingredient, nbtCompound) -> TagUtil.writeIngredient(ingredient))));
        nbt.put("container", TagUtil.encodeItemStackOnServer(this.container));
        nbt.put("result", TagUtil.encodeItemStackOnServer(this.result));
        nbt.putInt("brewing_time", this.brewingTime);
    }

    @Override
    public void loadFromTag(NbtCompound nbt) {
        this.ingredients = TagUtil.readList(nbt, "ingredients", TagUtil::readIngredient);
        this.container = TagUtil.decodeItemStackOnClient(nbt.getCompound("container").orElseGet(NbtCompound::new));
        this.result = TagUtil.decodeItemStackOnClient(nbt.getCompound("result").orElseGet(NbtCompound::new));
        this.brewingTime = nbt.getInt("brewing_time", 0);
    }

    @Override
    public ReliableServerRecipeType<? extends ReliableServerRecipe> getRecipeType() {
        return TYPE;
    }
}