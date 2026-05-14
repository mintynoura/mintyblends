//package io.github.mintynoura.mintyblends.datagen;
//
//import io.github.mintynoura.mintyblends.compat.farmersdelight.FarmersDelightCompat;
//import io.github.mintynoura.mintyblends.registry.MintyBlendsBlocks;
//import io.github.mintynoura.mintyblends.registry.MintyBlendsItems;
//import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
//import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
//import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
//import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
//import net.minecraft.core.HolderGetter;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.data.recipes.RecipeOutput;
//import net.minecraft.data.recipes.RecipeProvider;
//import net.minecraft.tags.ItemTags;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.item.crafting.Ingredient;
//import vectorwing.farmersdelight.common.crafting.CookingPotBookCategory;
//import vectorwing.farmersdelight.common.tag.CommonTags;
//import vectorwing.farmersdelight.common.tag.ModTags;
//import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;
//import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;
//
//import java.util.concurrent.CompletableFuture;
//
//public class CompatRecipeProvider extends FabricRecipeProvider {
//    public CompatRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
//        super(output, registriesFuture);
//    }
//
//    @Override
//    protected RecipeProvider createRecipeProvider(HolderLookup.Provider wrapperLookup, RecipeOutput exporter) {
//        return new RecipeProvider(wrapperLookup, exporter) {
//            @Override
//            public void buildRecipes() {
//                HolderGetter<Item> holderGetter = wrapperLookup.lookupOrThrow(Registries.ITEM);
//                CookingPotRecipeBuilder.cookingPotRecipe(holderGetter, FarmersDelightCompat.FRIED_GREENS.asItem(), 1, 200, 1.0f, Items.BOWL).addIngredient(ModTags.WILD_CROPS_ITEM).addIngredient(ItemTags.SMALL_FLOWERS).addIngredient(CommonTags.CROPS_CABBAGE).addIngredient(MintyBlendsItems.CULINARY_LEAVES).unlockedByItems("has_culinary_leaves", MintyBlendsItems.CULINARY_LEAVES).setRecipeBookCategory(CookingPotBookCategory.MEALS).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CookingPotRecipeBuilder.cookingPotRecipe(holderGetter, FarmersDelightCompat.ONION_SOUP.asItem(), 1, 200, 1.0f, Items.BOWL).addIngredient(CommonTags.CROPS_ONION).addIngredient(CommonTags.CROPS_ONION).addIngredient(CommonTags.FOODS_MILK).addIngredient(MintyBlendsItems.CULINARY_LEAVES).unlockedByItems("has_culinary_leaves", MintyBlendsItems.CULINARY_LEAVES).setRecipeBookCategory(CookingPotBookCategory.MEALS).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CookingPotRecipeBuilder.cookingPotRecipe(holderGetter, FarmersDelightCompat.TOMATO_SOUP.asItem(), 1, 200, 1.0f, Items.BOWL).addIngredient(CommonTags.CROPS_TOMATO).addIngredient(CommonTags.CROPS_TOMATO).addIngredient(CommonTags.CROPS_ONION).addIngredient(MintyBlendsItems.CULINARY_LEAVES).addIngredient(Items.SUGAR).unlockedByItems("has_culinary_leaves", MintyBlendsItems.CULINARY_LEAVES).setRecipeBookCategory(CookingPotBookCategory.MEALS).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CookingPotRecipeBuilder.cookingPotRecipe(holderGetter, FarmersDelightCompat.VEGETABLE_PORRIDGE.asItem(), 1, 200, 1.0f, Items.BOWL).addIngredient(CommonTags.CROPS_GRAIN).addIngredient(CommonTags.FOODS_MILK).addIngredient(ConventionalItemTags.VEGETABLE_FOODS).addIngredient(MintyBlendsItems.CULINARY_LEAVES).unlockedByItems("has_culinary_leaves", MintyBlendsItems.CULINARY_LEAVES).setRecipeBookCategory(CookingPotBookCategory.MEALS).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CookingPotRecipeBuilder.cookingPotRecipe(holderGetter, MintyBlendsItems.PUMPKIN_STEW.asItem(), 1, 200, 1.0f, Items.BOWL).addIngredient(vectorwing.farmersdelight.common.registry.MintyBlendsItems.PUMPKIN_SLICE.get()).addIngredient(Items.POTATO).addIngredient(Items.CARROT).addIngredient(MintyBlendsItems.CULINARY_LEAVES).unlockedByItems("has_culinary_leaves", MintyBlendsItems.CULINARY_LEAVES).setRecipeBookCategory(CookingPotBookCategory.MEALS).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
//
//                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(MintyBlendsBlocks.MINT.asItem()), Ingredient.of(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), MintyBlendsItems.MINT_LEAVES, 4).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(MintyBlendsBlocks.CATNIP.asItem()), Ingredient.of(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), MintyBlendsItems.CATNIP_LEAVES, 4).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(MintyBlendsBlocks.MEDICINAL_HERB.asItem()), Ingredient.of(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), MintyBlendsItems.MEDICINAL_LEAVES, 4).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(MintyBlendsBlocks.CULINARY_HERB.asItem()), Ingredient.of(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), MintyBlendsItems.CULINARY_LEAVES, 4).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(MintyBlendsBlocks.SAGEBRUSH.asItem()), Ingredient.of(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), MintyBlendsItems.SAGEBRUSH_LEAVES, 4).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
//
//                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(MintyBlendsItems.CUREFLOWER), Ingredient.of(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), Items.BLACK_DYE, 2).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(MintyBlendsItems.RENDFLOWER), Ingredient.of(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), Items.PURPLE_DYE, 2).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
//                CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(MintyBlendsBlocks.SILENT_FLOWER), Ingredient.of(holderGetter.getOrThrow(CommonTags.TOOLS_KNIFE)), Items.LIGHT_BLUE_DYE, 2).build(withConditions(output, ResourceConditions.anyModsLoaded("farmersdelight")));
//            }
//        };
//    }
//
//    @Override
//    public String getName() {
//        return "Minty Blends Farmer's Delight Compat Recipes";
//    }
//}
