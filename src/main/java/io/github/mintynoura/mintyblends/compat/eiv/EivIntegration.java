package io.github.mintynoura.mintyblends.compat.eiv;

import de.crafty.eiv.common.api.IExtendedItemViewIntegration;
import de.crafty.eiv.common.api.recipe.ItemView;
import de.crafty.eiv.common.recipe.ServerRecipeManager;
import io.github.mintynoura.mintyblends.registry.MintyBlendsRecipes;

// TODO: stack sensitive brews
public class EivIntegration implements IExtendedItemViewIntegration {
    @Override
    public void onIntegrationInitialize() {
        ItemView.addRecipeProvider(list -> ServerRecipeManager.INSTANCE.getRecipesForType(MintyBlendsRecipes.KETTLE_BREWING_RECIPE_TYPE).forEach(recipe -> list.add(new KettleBrewingServerRecipe(recipe.getIngredients(), recipe.getContainer().create(), recipe.getResult().create(), recipe.getBrewingTime()))));
    }
}