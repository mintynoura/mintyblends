package io.github.mintynoura.mintyblends.datagen;

import io.github.mintynoura.mintyblends.compat.farmersdelight.FarmersDelightCompat;
import io.github.mintynoura.mintyblends.registry.ModBlocks;
import io.github.mintynoura.mintyblends.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import vectorwing.farmersdelight.common.crafting.CookingPotBookCategory;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.concurrent.CompletableFuture;

public class CompatRecipeProvider extends FabricRecipeProvider {
    public CompatRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider wrapperLookup, RecipeOutput exporter) {
        return new RecipeProvider(wrapperLookup, exporter) {
            @Override
            public void buildRecipes() {
                HolderGetter<Item> holderGetter = wrapperLookup.lookupOrThrow(Registries.ITEM);
                CookingPotRecipeBuilder.cookingPotRecipe(holderGetter, FarmersDelightCompat.FRIED_GREENS.asItem(), 1, 200, 1.0f, Items.BOWL).addIngredient(ModTags.WILD_CROPS_ITEM).addIngredient(ItemTags.SMALL_FLOWERS).addIngredient(CommonTags.CROPS_CABBAGE).addIngredient(ModItems.CULINARY_LEAVES).unlockedByItems("has_culinary_leaves", ModItems.CULINARY_LEAVES).setRecipeBookCategory(CookingPotBookCategory.MEALS).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
                CookingPotRecipeBuilder.cookingPotRecipe(holderGetter, FarmersDelightCompat.ONION_SOUP.asItem(), 1, 200, 1.0f, Items.BOWL).addIngredient(CommonTags.CROPS_ONION).addIngredient(CommonTags.CROPS_ONION).addIngredient(CommonTags.FOODS_MILK).addIngredient(ModItems.CULINARY_LEAVES).unlockedByItems("has_culinary_leaves", ModItems.CULINARY_LEAVES).setRecipeBookCategory(CookingPotBookCategory.MEALS).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
                CookingPotRecipeBuilder.cookingPotRecipe(holderGetter, FarmersDelightCompat.TOMATO_SOUP.asItem(), 1, 200, 1.0f, Items.BOWL).addIngredient(CommonTags.CROPS_TOMATO).addIngredient(CommonTags.CROPS_TOMATO).addIngredient(CommonTags.CROPS_ONION).addIngredient(ModItems.CULINARY_LEAVES).addIngredient(Items.SUGAR).unlockedByItems("has_culinary_leaves", ModItems.CULINARY_LEAVES).setRecipeBookCategory(CookingPotBookCategory.MEALS).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
                CookingPotRecipeBuilder.cookingPotRecipe(holderGetter, FarmersDelightCompat.VEGETABLE_PORRIDGE.asItem(), 1, 200, 1.0f, Items.BOWL).addIngredient(CommonTags.CROPS_GRAIN).addIngredient(CommonTags.FOODS_MILK).addIngredient(ConventionalItemTags.VEGETABLE_FOODS).addIngredient(ModItems.CULINARY_LEAVES).unlockedByItems("has_culinary_leaves", ModItems.CULINARY_LEAVES).setRecipeBookCategory(CookingPotBookCategory.MEALS).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
                CookingPotRecipeBuilder.cookingPotRecipe(holderGetter, ModItems.PUMPKIN_STEW.asItem(), 1, 200, 1.0f, Items.BOWL).addIngredient(vectorwing.farmersdelight.common.registry.ModItems.PUMPKIN_SLICE.get()).addIngredient(Items.POTATO).addIngredient(Items.CARROT).addIngredient(ModItems.CULINARY_LEAVES).unlockedByItems("has_culinary_leaves", ModItems.CULINARY_LEAVES).setRecipeBookCategory(CookingPotBookCategory.MEALS).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));

                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModBlocks.MINT.asItem()), Ingredient.of(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), ModItems.MINT_LEAVES, 4).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModBlocks.CATNIP.asItem()), Ingredient.of(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), ModItems.CATNIP_LEAVES, 4).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModBlocks.MEDICINAL_HERB.asItem()), Ingredient.of(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), ModItems.MEDICINAL_LEAVES, 4).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModBlocks.CULINARY_HERB.asItem()), Ingredient.of(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), ModItems.CULINARY_LEAVES, 4).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModBlocks.SAGEBRUSH.asItem()), Ingredient.of(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), ModItems.SAGEBRUSH_LEAVES, 4).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));

                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.CUREFLOWER), Ingredient.of(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), Items.BLACK_DYE, 2).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.RENDFLOWER), Ingredient.of(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), Items.PURPLE_DYE, 2).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModBlocks.SILENT_FLOWER), Ingredient.of(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), Items.LIGHT_BLUE_DYE, 2).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
            }
        };
    }

    @Override
    public String getName() {
        return "Minty Blends Farmer's Delight Compat Recipes";
    }
}
