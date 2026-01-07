package io.github.mintynoura.mintyblends.compat.rrv;

import cc.cassian.rrv.api.recipe.ItemView;

import java.util.Collections;

public class RrvClientIntegration {
    public static void onIntegrationInitialize() {
        ItemView.registerClientRecipeWrapper(KettleBrewingServerRecipe.TYPE, serverRecipe -> Collections.singletonList(new KettleBrewingClientRecipe(serverRecipe)));
    }
}