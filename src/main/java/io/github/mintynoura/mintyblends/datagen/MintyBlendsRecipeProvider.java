package io.github.mintynoura.mintyblends.datagen;

import io.github.mintynoura.mintyblends.registry.MintyBlendsItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MintyBlendsRecipeProvider extends FabricRecipeProvider {
    public MintyBlendsRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            public void buildRecipes() {
                KettleBrewingRecipeBuilder.kettleBrewingRecipe(List.of(Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE)),new ItemStackTemplate(MintyBlendsItems.GLOW_BERRY_TEA), new ItemStackTemplate(Items.POTION), 20)
                        .unlockedBy(getHasName(Items.ENCHANTED_GOLDEN_APPLE), has(Items.ENCHANTED_GOLDEN_APPLE))
                        .save(output);
            }
        };
    }

    @Override
    public String getName() {
        return "MintyBlendsRecipeProvider";
    }
}
