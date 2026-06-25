package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.recipe.CenserBlendRecipe;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipe;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipeDisplay;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

public class MintyBlendsRecipes {
    public static final RecipeSerializer<KettleBrewingRecipe> KETTLE_BREWING_RECIPE_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(MintyBlends.ID, "kettle_brewing"), KettleBrewingRecipe.SERIALIZER);
    public static final RecipeSerializer<CenserBlendRecipe> CENSER_BLEND_RECIPE_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(MintyBlends.ID, "crafting_special_censer_blend"), CenserBlendRecipe.SERIALIZER);

    public static final RecipeType<KettleBrewingRecipe> KETTLE_BREWING_RECIPE_TYPE = Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(MintyBlends.ID, "kettle_brewing"), new RecipeType<KettleBrewingRecipe>() {
        @Override
        public String toString() {
            return "kettle_brewing";
        }
    });

    public static final RecipeBookCategory KETTLE_BREWING_RECIPE_CATEGORY = Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath(MintyBlends.ID, "kettle_brewing"), new RecipeBookCategory());
    public static final RecipeDisplay.Type<KettleBrewingRecipeDisplay> KETTLE_BREWING_RECIPE_DISPLAY = Registry.register(BuiltInRegistries.RECIPE_DISPLAY, Identifier.fromNamespaceAndPath(MintyBlends.ID, "kettle_brewing"), KettleBrewingRecipeDisplay.TYPE);

    public static void initialize() {}
}
