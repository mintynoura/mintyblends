package io.github.mintynoura.mintyblends.compat.eiv;

import de.crafty.eiv.common.api.IExtendedItemViewIntegration;
import de.crafty.eiv.common.api.recipe.ItemView;
import de.crafty.eiv.common.recipe.ServerRecipeManager;
import io.github.mintynoura.mintyblends.registry.ModRecipes;

public class EivIntegration implements IExtendedItemViewIntegration {
    @Override
    public void onIntegrationInitialize() {
        ItemView.addRecipeProvider(list -> ServerRecipeManager.INSTANCE.getRecipesForType(ModRecipes.KETTLE_BREWING_RECIPE_TYPE).forEach(recipe -> list.add(new KettleBrewingServerRecipe(recipe.getIngredients(), recipe.getContainer(), recipe.getResult(), recipe.getBrewingTime()))));
    }
}
