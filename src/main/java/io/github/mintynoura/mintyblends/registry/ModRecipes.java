package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.recipe.CenserBlendRecipe;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipes {
    public static final RecipeSerializer<KettleBrewingRecipe> KETTLE_BREWING_RECIPE_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "kettle_brewing"), new KettleBrewingRecipe.Serializer());
    public static final RecipeSerializer<CenserBlendRecipe> CENSER_BLEND_RECIPE_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "crafting_special_censer_blend"), new CustomRecipe.Serializer<>(CenserBlendRecipe::new));

    public static final RecipeType<KettleBrewingRecipe> KETTLE_BREWING_RECIPE_TYPE = Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "kettle_brewing"), new RecipeType<KettleBrewingRecipe>() {
        @Override
        public String toString() {
            return "kettle_brewing";
        }
    });

    public static void registerRecipes() {}
}
