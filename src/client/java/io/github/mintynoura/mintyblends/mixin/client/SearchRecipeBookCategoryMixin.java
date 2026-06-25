package io.github.mintynoura.mintyblends.mixin.client;

import io.github.mintynoura.mintyblends.registry.MintyBlendsRecipes;
import net.minecraft.client.gui.screens.recipebook.SearchRecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SearchRecipeBookCategory.class)
public enum SearchRecipeBookCategoryMixin {
    MINTYBLENDS_KETTLE_BREWING(MintyBlendsRecipes.KETTLE_BREWING_RECIPE_CATEGORY);

    @Shadow
    SearchRecipeBookCategoryMixin(final RecipeBookCategory... includedCategories) {
    }
}
