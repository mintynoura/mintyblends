package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static final RecipeSerializer<KettleBrewingRecipe> KETTLE_BREWING_RECIPE_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(MintyBlends.MOD_ID, "kettle_brewing"), new KettleBrewingRecipe.Serializer());

    public static final RecipeType<KettleBrewingRecipe> KETTLE_BREWING_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE, Identifier.of(MintyBlends.MOD_ID, "kettle_brewing"), new RecipeType<KettleBrewingRecipe>() {
        @Override
        public String toString() {
            return "kettle_brewing";
        }
    });

    public static void registerRecipes() {}
}
