package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.util.ModTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModWorldgenFeatures {
    public static final ResourceKey<PlacedFeature> MINT_PLACED_FEATURE = ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "mint"));
    public static final ResourceKey<PlacedFeature> CATNIP_PLACED_FEATURE = ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "catnip"));
    public static final ResourceKey<PlacedFeature> MEDICINAL_HERB_PLACED_FEATURE = ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "medicinal_herb"));
    public static final ResourceKey<PlacedFeature> CULINARY_HERB_PLACED_FEATURE = ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "culinary_herb"));
    public static final ResourceKey<PlacedFeature> SAGEBRUSH_PLACED_FEATURE = ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "sagebrush"));
    public static final ResourceKey<PlacedFeature> CUREFLOWER_PLACED_FEATURE = ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "cureflower"));
    public static final ResourceKey<PlacedFeature> SILENT_FLOWER_PLACED_FEATURE = ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "silent_flower"));

    public static void registerWorldgenFeatures() {
        BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.HAS_MINT), GenerationStep.Decoration.VEGETAL_DECORATION, MINT_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.HAS_CATNIP), GenerationStep.Decoration.VEGETAL_DECORATION, CATNIP_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.HAS_MEDICINAL_HERB), GenerationStep.Decoration.VEGETAL_DECORATION, MEDICINAL_HERB_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.HAS_CULINARY_HERB), GenerationStep.Decoration.VEGETAL_DECORATION, CULINARY_HERB_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.HAS_SAGEBRUSH), GenerationStep.Decoration.VEGETAL_DECORATION, SAGEBRUSH_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.CRIMSON_FOREST), GenerationStep.Decoration.VEGETAL_DECORATION, CUREFLOWER_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.DARK_FOREST), GenerationStep.Decoration.VEGETAL_DECORATION, SILENT_FLOWER_PLACED_FEATURE);
    }
}
