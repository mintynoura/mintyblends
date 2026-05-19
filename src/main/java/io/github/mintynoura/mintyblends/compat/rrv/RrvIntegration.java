package io.github.mintynoura.mintyblends.compat.rrv;

import cc.cassian.rrv.api.ReliableRecipeViewerPlugin;
import cc.cassian.rrv.api.recipe.ItemView;
import cc.cassian.rrv.common.recipe.ServerRecipeManager;
import io.github.mintynoura.mintyblends.registry.MintyBlendsRecipes;
// TODO: stack sensitive brews
public class RrvIntegration implements ReliableRecipeViewerPlugin {
    @Override
    public void onIntegrationInitialize() {
        ItemView.addServerRecipeProvider(list -> ServerRecipeManager.INSTANCE.getRecipesForType(MintyBlendsRecipes.KETTLE_BREWING_RECIPE_TYPE).forEach(recipe -> list.add(new KettleBrewingServerRecipe(recipe.getIngredients(), recipe.getContainer().create(), recipe.getResult().create(), recipe.getBrewingTime()))));
    }
}