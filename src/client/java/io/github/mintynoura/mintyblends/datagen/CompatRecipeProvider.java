package io.github.mintynoura.mintyblends.datagen;

//import io.github.mintynoura.mintyblends.compat.farmersdelight.FarmersDelightCompat;
import io.github.mintynoura.mintyblends.registry.ModBlocks;
import io.github.mintynoura.mintyblends.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
//import vectorwing.farmersdelight.common.crafting.CookingPotBookCategory;
//import vectorwing.farmersdelight.common.tag.CommonTags;
//import vectorwing.farmersdelight.common.tag.ModTags;
//import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;
//import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.concurrent.CompletableFuture;

public class CompatRecipeProvider extends FabricRecipeProvider {
    public CompatRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter exporter) {
        return new RecipeGenerator(wrapperLookup, exporter) {
            @Override
            public void generate() {
                RegistryEntryLookup<Item> holderGetter = wrapperLookup.getOrThrow(RegistryKeys.ITEM);
//                CookingPotRecipeBuilder.cookingPotRecipe(holderGetter, FarmersDelightCompat.FRIED_GREENS.asItem(), 1, 200, 1.0f, Items.BOWL).addIngredient(ModTags.WILD_CROPS_ITEM).addIngredient(ItemTags.SMALL_FLOWERS).addIngredient(CommonTags.CROPS_CABBAGE).addIngredient(ModItems.CULINARY_LEAVES).unlockedByItems("has_culinary_leaves", ModItems.CULINARY_LEAVES).setRecipeBookCategory(CookingPotBookCategory.MEALS).build(withConditions(exporter, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CookingPotRecipeBuilder.cookingPotRecipe(holderGetter, FarmersDelightCompat.ONION_SOUP.asItem(), 1, 200, 1.0f, Items.BOWL).addIngredient(CommonTags.CROPS_ONION).addIngredient(CommonTags.CROPS_ONION).addIngredient(CommonTags.FOODS_MILK).addIngredient(ModItems.CULINARY_LEAVES).unlockedByItems("has_culinary_leaves", ModItems.CULINARY_LEAVES).setRecipeBookCategory(CookingPotBookCategory.MEALS).build(withConditions(exporter, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CookingPotRecipeBuilder.cookingPotRecipe(holderGetter, FarmersDelightCompat.TOMATO_SOUP.asItem(), 1, 200, 1.0f, Items.BOWL).addIngredient(CommonTags.CROPS_TOMATO).addIngredient(CommonTags.CROPS_TOMATO).addIngredient(CommonTags.CROPS_ONION).addIngredient(ModItems.CULINARY_LEAVES).addIngredient(Items.SUGAR).unlockedByItems("has_culinary_leaves", ModItems.CULINARY_LEAVES).setRecipeBookCategory(CookingPotBookCategory.MEALS).build(withConditions(exporter, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CookingPotRecipeBuilder.cookingPotRecipe(holderGetter, FarmersDelightCompat.VEGETABLE_PORRIDGE.asItem(), 1, 200, 1.0f, Items.BOWL).addIngredient(CommonTags.CROPS_GRAIN).addIngredient(CommonTags.FOODS_MILK).addIngredient(ConventionalItemTags.VEGETABLE_FOODS).addIngredient(ModItems.CULINARY_LEAVES).unlockedByItems("has_culinary_leaves", ModItems.CULINARY_LEAVES).setRecipeBookCategory(CookingPotBookCategory.MEALS).build(withConditions(exporter, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CookingPotRecipeBuilder.cookingPotRecipe(holderGetter, ModItems.PUMPKIN_STEW.asItem(), 1, 200, 1.0f, Items.BOWL).addIngredient(vectorwing.farmersdelight.common.registry.ModItems.PUMPKIN_SLICE.get()).addIngredient(Items.POTATO).addIngredient(Items.CARROT).addIngredient(ModItems.CULINARY_LEAVES).unlockedByItems("has_culinary_leaves", ModItems.CULINARY_LEAVES).setRecipeBookCategory(CookingPotBookCategory.MEALS).build(withConditions(exporter, ResourceConditions.anyModsLoaded("farmersdelight")));
//
//                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.ofItem(ModBlocks.MINT.asItem()), Ingredient.ofTag(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), ModItems.MINT_LEAVES, 4).build(withConditions(exporter, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.ofItem(ModBlocks.CATNIP.asItem()), Ingredient.ofTag(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), ModItems.CATNIP_LEAVES, 4).build(withConditions(exporter, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.ofItem(ModBlocks.MEDICINAL_HERB.asItem()), Ingredient.ofTag(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), ModItems.MEDICINAL_LEAVES, 4).build(withConditions(exporter, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.ofItem(ModBlocks.CULINARY_HERB.asItem()), Ingredient.ofTag(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), ModItems.CULINARY_LEAVES, 4).build(withConditions(exporter, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.ofItem(ModBlocks.SAGEBRUSH.asItem()), Ingredient.ofTag(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), ModItems.SAGEBRUSH_LEAVES, 4).build(withConditions(exporter, ResourceConditions.anyModsLoaded("farmersdelight")));
//
//                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.ofItem(ModItems.CUREFLOWER), Ingredient.ofTag(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), Items.BLACK_DYE, 2).build(withConditions(exporter, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.ofItem(ModItems.RENDFLOWER), Ingredient.ofTag(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), Items.PURPLE_DYE, 2).build(withConditions(exporter, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.ofItem(ModBlocks.SILENT_FLOWER), Ingredient.ofTag(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), Items.LIGHT_BLUE_DYE, 2).build(withConditions(exporter, ResourceConditions.anyModsLoaded("farmersdelight")));
            }
        };
    }

    @Override
    public String getName() {
        return "Minty Blends Farmer's Delight Compat Recipes";
    }
}
