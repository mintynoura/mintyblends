package io.github.mintynoura.mintyblends.datagen;

import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KettleBrewingRecipeBuilder implements RecipeBuilder {
    private final HolderGetter<Item> items;
    private final List<Ingredient> ingredients = new ArrayList<>(4);
    private final ItemStackTemplate result;
    private ItemStackTemplate container;
    private final int brewingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public KettleBrewingRecipeBuilder(HolderGetter<Item> items, ItemStackTemplate result, ItemStackTemplate container, int brewingTime) {
        this.items = items;
        this.result = result;
        this.container = container;
        this.brewingTime = brewingTime;
    }

    public static KettleBrewingRecipeBuilder kettleBrewing(HolderGetter<Item> items, ItemStackTemplate result, ItemStackTemplate container, int brewingTime) {
        return new KettleBrewingRecipeBuilder(items, result, container, brewingTime);
    }

    public static KettleBrewingRecipeBuilder kettleBrewing(HolderGetter<Item> items, ItemStackTemplate result) {
        return new KettleBrewingRecipeBuilder(items, result, KettleBrewingRecipe.defaultContainer, KettleBrewingRecipe.defaultBrewingTime);
    }

    public static KettleBrewingRecipeBuilder kettleBrewing(HolderGetter<Item> items, ItemStack result) {
        return kettleBrewing(items, ItemStackTemplate.fromNonEmptyStack(result), KettleBrewingRecipe.defaultContainer, KettleBrewingRecipe.defaultBrewingTime);
    }

    public static KettleBrewingRecipeBuilder kettleBrewing(HolderGetter<Item> items, Item result) {
        return kettleBrewing(items, result, 1);
    }

    public static KettleBrewingRecipeBuilder kettleBrewing(HolderGetter<Item> items, Item result, int count) {
        return kettleBrewing(items, result, count, KettleBrewingRecipe.defaultBrewingTime);
    }

    public static KettleBrewingRecipeBuilder kettleBrewing(HolderGetter<Item> items, Item result, int count, int brewingTime) {
        return kettleBrewing(items, new ItemStackTemplate(result, count), KettleBrewingRecipe.defaultContainer, brewingTime);
    }

    public KettleBrewingRecipeBuilder requires(final TagKey<Item> tag) {
        return this.requires(Ingredient.of(this.items.getOrThrow(tag)));
    }

    public KettleBrewingRecipeBuilder requires(final ItemLike item) {
        return this.requires(Ingredient.of(item), 1);
    }


    public KettleBrewingRecipeBuilder requires(final Ingredient ingredient) {
        return this.requires(ingredient, 1);
    }

    public KettleBrewingRecipeBuilder requires(final Ingredient ingredient, final int count) {
        for (int i = 0; i < count; i++) {
            this.ingredients.add(ingredient);
        }

        return this;
    }

    public KettleBrewingRecipeBuilder container(Item item) {
        this.container = new ItemStackTemplate(item);
        return this;
    }

    public KettleBrewingRecipeBuilder container(ItemStack item) {
        this.container = ItemStackTemplate.fromNonEmptyStack(item);
        return this;
    }

    public KettleBrewingRecipeBuilder container(ItemStackTemplate item) {
        this.container = item;
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> location) {
        Advancement.Builder advancement = Advancement.Builder.recipeAdvancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(location)).rewards(new AdvancementRewards.Builder().addRecipe(location)).requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        KettleBrewingRecipe recipe = new KettleBrewingRecipe(this.ingredients, this.result, this.container, this.brewingTime);
        output.accept(location, recipe, advancement.build(location.identifier().withPrefix("recipes/")));
    }
}
