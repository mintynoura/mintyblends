package io.github.mintynoura.mintyblends.compat.rrv;

import cc.cassian.rrv.api.TagUtil;
import cc.cassian.rrv.api.recipe.ReliableServerRecipe;
import cc.cassian.rrv.api.recipe.ReliableServerRecipeType;
import io.github.mintynoura.mintyblends.MintyBlends;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class KettleBrewingServerRecipe implements ReliableServerRecipe {

    private List<Ingredient> ingredients;
    private ItemStack container;
    private ItemStack result;
    private int brewingTime;


    public static final ReliableServerRecipeType<KettleBrewingServerRecipe> TYPE = ReliableServerRecipeType.register(Identifier.fromNamespaceAndPath(MintyBlends.ID, "kettle_brewing"),
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
    public void writeToTag(CompoundTag nbt) {
        nbt.put("ingredients", TagUtil.writeList(this.ingredients, ((ingredient, nbtCompound) -> TagUtil.writeIngredient(ingredient))));
        nbt.put("container", TagUtil.encodeItemStackOnServer(this.container));
        nbt.put("result", TagUtil.encodeItemStackOnServer(this.result));
        nbt.putInt("brewing_time", this.brewingTime);
    }

    @Override
    public void loadFromTag(CompoundTag nbt) {
        this.ingredients = TagUtil.readList(nbt, "ingredients", TagUtil::readIngredient);
        this.container = TagUtil.decodeItemStackOnClient(nbt.getCompound("container").orElseGet(CompoundTag::new));
        this.result = TagUtil.decodeItemStackOnClient(nbt.getCompound("result").orElseGet(CompoundTag::new));
        this.brewingTime = nbt.getIntOr("brewing_time", 0);
    }

    @Override
    public ReliableServerRecipeType<? extends ReliableServerRecipe> getRecipeType() {
        return TYPE;
    }
}