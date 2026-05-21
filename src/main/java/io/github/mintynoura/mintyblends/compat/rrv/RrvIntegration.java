package io.github.mintynoura.mintyblends.compat.rrv;

import cc.cassian.rrv.api.ReliableRecipeViewerPlugin;
import cc.cassian.rrv.common.recipe.ServerRecipeManager;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipe;
import io.github.mintynoura.mintyblends.registry.MintyBlendsRecipes;

// TODO: stack sensitive brews
public class RrvIntegration implements ReliableRecipeViewerPlugin {
    @Override
    public void onIntegrationInitialize() {
        ServerRecipeManager.INSTANCE.synchronizeRecipeType(KettleBrewingRecipe.SERIALIZER, MintyBlendsRecipes.KETTLE_BREWING_RECIPE_TYPE);
    }
}