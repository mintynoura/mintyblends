package io.github.mintynoura.mintyblends.compat.rrv;

import cc.cassian.rrv.api.ReliableRecipeViewerClientPlugin;
import cc.cassian.rrv.api.recipe.ItemView;
import cc.cassian.rrv.client.recipe.ClientRecipeManager;
import io.github.mintynoura.mintyblends.registry.MintyBlendsRecipes;

public class RrvClientIntegration implements ReliableRecipeViewerClientPlugin {
    @Override
    public void onIntegrationInitialize() {
        ItemView.addClientRecipeProvider(recipeList ->
                ClientRecipeManager.INSTANCE.getRecipesForType(MintyBlendsRecipes.KETTLE_BREWING_RECIPE_TYPE).forEach(recipeHolder ->
                        recipeList.add(new KettleBrewingClientRecipe(recipeHolder))));
    }
}