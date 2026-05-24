package io.github.mintynoura.mintyblends.datagen;

import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KettleBrewingRecipeBuilder implements RecipeBuilder {
    private final List<Ingredient> ingredients;
    private final ItemStackTemplate result;
    private final ItemStackTemplate container;
    private final int brewingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public KettleBrewingRecipeBuilder(List<Ingredient> ingredients, ItemStackTemplate result, ItemStackTemplate container, int brewingTime) {
        this.ingredients = ingredients;
        this.result = result;
        this.container = container;
        this.brewingTime = brewingTime;
    }

    public static KettleBrewingRecipeBuilder kettleBrewingRecipe(List<Ingredient> ingredients, ItemStackTemplate result, ItemStackTemplate container, int brewingTime) {
        return new KettleBrewingRecipeBuilder(ingredients, result, container, brewingTime);
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
