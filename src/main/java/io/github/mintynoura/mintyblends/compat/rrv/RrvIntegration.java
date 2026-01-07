package io.github.mintynoura.mintyblends.compat.rrv;

import cc.cassian.rrv.api.ReliableRecipeViewerPlugin;
import cc.cassian.rrv.api.recipe.ItemView;
import cc.cassian.rrv.common.recipe.ServerRecipeManager;
import io.github.mintynoura.mintyblends.registry.ModRecipes;

public class RrvIntegration implements ReliableRecipeViewerPlugin {
    @Override
    public void onIntegrationInitialize() {
        ItemView.addServerRecipeProvider(list -> ServerRecipeManager.INSTANCE.getRecipesForType(ModRecipes.KETTLE_BREWING_RECIPE_TYPE).forEach(recipe -> list.add(new KettleBrewingServerRecipe(recipe.getIngredients(), recipe.getContainer(), recipe.getResult(), recipe.getBrewingTime()))));
    }
}