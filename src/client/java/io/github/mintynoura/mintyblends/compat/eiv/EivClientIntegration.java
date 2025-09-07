package io.github.mintynoura.mintyblends.compat.eiv;

import de.crafty.eiv.common.api.IExtendedItemViewIntegration;
import de.crafty.eiv.common.api.recipe.ItemView;

import java.util.Collections;

public class EivClientIntegration implements IExtendedItemViewIntegration {
    @Override
    public void onIntegrationInitialize() {
        ItemView.registerRecipeWrapper(KettleBrewingServerRecipe.TYPE, serverRecipe -> Collections.singletonList(new KettleBrewingViewRecipe(serverRecipe)));
    }
}