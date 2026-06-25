package io.github.mintynoura.mintyblends.datagen;

import io.github.mintynoura.mintyblends.registry.MintyBlendsBlocks;
import io.github.mintynoura.mintyblends.registry.MintyBlendsItems;
import io.github.mintynoura.mintyblends.util.MintyBlendsBlends;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class MintyBlendsRecipeProvider extends FabricRecipeProvider {
    public MintyBlendsRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    // TODO: datagen stack sensitive brews
    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsBlends.CONVERT_NEGATIVE_TO_POSITIVE_TEA)
                        .requires(MintyBlendsItems.CUREFLOWER)
                        .requires(Items.GLOW_BERRIES)
                        .requires(Items.SLIME_BALL)
                        .requires(Items.RED_MUSHROOM)
                        .unlockedBy(getHasName(MintyBlendsItems.CUREFLOWER), has(MintyBlendsItems.CUREFLOWER))
                        .save(output, "kettle_brewing/bane_reversal_tea");
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsBlends.CONVERT_POSITIVE_TO_NEGATIVE_TEA)
                        .requires(MintyBlendsItems.RENDFLOWER)
                        .requires(Items.LILY_OF_THE_VALLEY)
                        .requires(Items.SPIDER_EYE)
                        .requires(Items.BROWN_MUSHROOM)
                        .unlockedBy(getHasName(MintyBlendsItems.RENDFLOWER), has(MintyBlendsItems.RENDFLOWER))
                        .save(output, "kettle_brewing/boon_inversion_tea");
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsBlends.CLEAR_ALL_EFFECTS_TEA)
                        .requires(MintyBlendsBlocks.SILENT_FLOWER.asItem())
                        .requires(Items.BLUE_ORCHID)
                        .requires(Items.KELP)
                        .unlockedBy(getHasName(MintyBlendsBlocks.SILENT_FLOWER.asItem()), has(MintyBlendsBlocks.SILENT_FLOWER.asItem()))
                        .save(output, "kettle_brewing/clearing_tea");
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsBlends.CLEAR_NEGATIVE_TEA)
                        .requires(MintyBlendsItems.SAGEBRUSH_LEAVES)
                        .requires(MintyBlendsItems.CUREFLOWER)
                        .requires(Items.HONEY_BOTTLE)
                        .requires(Items.KELP)
                        .unlockedBy(getHasName(MintyBlendsItems.SAGEBRUSH_LEAVES), has(MintyBlendsItems.SAGEBRUSH_LEAVES))
                        .save(output, "kettle_brewing/curing_tea");
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsBlends.CLEAR_POSITIVE_TEA)
                        .requires(Items.FERMENTED_SPIDER_EYE)
                        .requires(Items.CLOSED_EYEBLOSSOM)
                        .requires(Items.GLOWSTONE_DUST)
                        .requires(Items.KELP)
                        .unlockedBy(getHasName(Items.FERMENTED_SPIDER_EYE), has(Items.FERMENTED_SPIDER_EYE))
                        .save(output, "kettle_brewing/fatiguing_tea");
            }
        };
    }

    @Override
    public String getName() {
        return "MintyBlendsRecipeProvider";
    }
}