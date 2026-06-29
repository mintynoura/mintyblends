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
                        .saveMintyBlends(output, "bane_reversal_tea");
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsBlends.CONVERT_POSITIVE_TO_NEGATIVE_TEA)
                        .requires(MintyBlendsItems.RENDFLOWER)
                        .requires(Items.LILY_OF_THE_VALLEY)
                        .requires(Items.SPIDER_EYE)
                        .requires(Items.BROWN_MUSHROOM)
                        .unlockedBy(getHasName(MintyBlendsItems.RENDFLOWER), has(MintyBlendsItems.RENDFLOWER))
                        .saveMintyBlends(output, "boon_inversion_tea");
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsBlends.CLEAR_ALL_EFFECTS_TEA)
                        .requires(MintyBlendsBlocks.SILENT_FLOWER.asItem())
                        .requires(Items.BLUE_ORCHID)
                        .requires(Items.KELP)
                        .unlockedBy(getHasName(MintyBlendsBlocks.SILENT_FLOWER.asItem()), has(MintyBlendsBlocks.SILENT_FLOWER.asItem()))
                        .saveMintyBlends(output, "clearing_tea");
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsBlends.CLEAR_NEGATIVE_TEA)
                        .requires(MintyBlendsItems.SAGEBRUSH_LEAVES)
                        .requires(MintyBlendsItems.CUREFLOWER)
                        .requires(Items.HONEY_BOTTLE)
                        .requires(Items.KELP)
                        .unlockedBy(getHasName(MintyBlendsItems.SAGEBRUSH_LEAVES), has(MintyBlendsItems.SAGEBRUSH_LEAVES))
                        .saveMintyBlends(output, "curing_tea");
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsBlends.CLEAR_POSITIVE_TEA)
                        .requires(Items.FERMENTED_SPIDER_EYE)
                        .requires(Items.CLOSED_EYEBLOSSOM)
                        .requires(Items.GLOWSTONE_DUST)
                        .requires(Items.KELP)
                        .unlockedBy(getHasName(Items.FERMENTED_SPIDER_EYE), has(Items.FERMENTED_SPIDER_EYE))
                        .saveMintyBlends(output, "fatiguing_tea");
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsBlends.REACHING_TEA)
                        .requires(MintyBlendsItems.CATNIP_LEAVES)
                        .requires(Items.OXEYE_DAISY)
                        .requires(Items.SUGAR)
                        .unlockedBy(getHasName(MintyBlendsItems.CATNIP_LEAVES), has(MintyBlendsItems.CATNIP_LEAVES))
                        .saveMintyBlends(output, "reaching_tea");
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsBlends.STRONG_REACHING_TEA)
                        .requires(MintyBlendsItems.CATNIP_LEAVES)
                        .requires(Items.BLAZE_POWDER)
                        .container(MintyBlendsBlends.REACHING_TEA)
                        .unlockedBy(getHasName(MintyBlendsItems.CATNIP_LEAVES), has(MintyBlendsItems.CATNIP_LEAVES))
                        .saveMintyBlends(output, "strong_reaching_tea");
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsBlends.RENDING_TEA)
                        .requires(MintyBlendsItems.RENDFLOWER)
                        .requires(MintyBlendsItems.RENDFLOWER)
                        .requires(Items.CHORUS_FLOWER)
                        .unlockedBy(getHasName(MintyBlendsItems.RENDFLOWER), has(MintyBlendsItems.RENDFLOWER))
                        .saveMintyBlends(output, "rending_tea");
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsBlends.STRONG_RENDING_TEA)
                        .requires(MintyBlendsItems.RENDFLOWER)
                        .requires(Items.BLAZE_POWDER)
                        .container(MintyBlendsBlends.RENDING_TEA)
                        .unlockedBy(getHasName(MintyBlendsItems.RENDFLOWER), has(MintyBlendsItems.RENDFLOWER))
                        .saveMintyBlends(output, "strong_rending_tea");
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsBlends.STEALTH_TEA)
                        .requires(MintyBlendsBlocks.SILENT_FLOWER)
                        .requires(MintyBlendsBlocks.SILENT_FLOWER)
                        .requires(Items.AZURE_BLUET)
                        .unlockedBy(getHasName(MintyBlendsBlocks.SILENT_FLOWER), has(MintyBlendsBlocks.SILENT_FLOWER))
                        .saveMintyBlends(output, "stealth_tea");
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsBlends.STRONG_STEALTH_TEA)
                        .requires(MintyBlendsBlocks.SILENT_FLOWER)
                        .requires(Items.BLAZE_POWDER)
                        .container(MintyBlendsBlends.STEALTH_TEA)
                        .unlockedBy(getHasName(MintyBlendsBlocks.SILENT_FLOWER), has(MintyBlendsBlocks.SILENT_FLOWER))
                        .saveMintyBlends(output, "strong_stealth_tea");

                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsItems.MINT_TEA)
                        .requires(MintyBlendsItems.MINT_LEAVES)
                        .requires(MintyBlendsItems.MINT_LEAVES)
                        .requires(Items.SUGAR)
                        .unlockedBy(getHasName(MintyBlendsItems.MINT_LEAVES), has(MintyBlendsItems.MINT_LEAVES))
                        .saveWithPrefix(output);
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsItems.SWEET_BERRY_TEA)
                        .requires(Items.SWEET_BERRIES)
                        .requires(Items.SWEET_BERRIES)
                        .unlockedBy(getHasName(Items.SWEET_BERRIES), has(Items.SWEET_BERRIES))
                        .saveWithPrefix(output);
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsItems.GLOW_BERRY_TEA)
                        .requires(Items.GLOW_BERRIES)
                        .requires(Items.GLOW_BERRIES)
                        .requires(Items.SUGAR)
                        .unlockedBy(getHasName(Items.GLOW_BERRIES), has(Items.GLOW_BERRIES))
                        .saveWithPrefix(output);
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsItems.TORCHFLOWER_TEA)
                        .requires(Items.TORCHFLOWER)
                        .requires(Items.TORCHFLOWER)
                        .requires(Items.HONEY_BOTTLE)
                        .unlockedBy(getHasName(Items.TORCHFLOWER), has(Items.TORCHFLOWER))
                        .saveWithPrefix(output);
                KettleBrewingRecipeBuilder.kettleBrewing(itemLookup, MintyBlendsItems.WILDFLOWER_TEA)
                        .requires(Items.WILDFLOWERS)
                        .requires(Items.WILDFLOWERS)
                        .requires(Items.HONEY_BOTTLE)
                        .unlockedBy(getHasName(Items.WILDFLOWERS), has(Items.WILDFLOWERS))
                        .saveWithPrefix(output);
            }
        };
    }

    @Override
    public String getName() {
        return "MintyBlendsRecipeProvider";
    }
}