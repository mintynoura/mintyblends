package io.github.mintynoura.mintyblends.compat.rei;

import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipe;
import io.github.mintynoura.mintyblends.registry.ModBlocks;
import io.github.mintynoura.mintyblends.registry.ModRecipes;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;

public class ClientPlugin implements me.shedaniel.rei.api.client.plugins.REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new KettleBrewingDisplayCategory());
        registry.addWorkstations(CommonPlugin.KETTLE_BREWING_CATEGORY_ID, EntryStacks.of(ModBlocks.KETTLE.asItem()));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
  //      registry.beginRecipeFiller(KettleBrewingRecipe.class).fill(KettleBrewingDisplay::new);
    }
}
