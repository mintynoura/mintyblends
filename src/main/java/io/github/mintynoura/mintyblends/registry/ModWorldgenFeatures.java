package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.util.ModTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModWorldgenFeatures {
    public static final RegistryKey<PlacedFeature> MINT_PLACED_FEATURE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MintyBlends.MOD_ID, "mint"));
    public static final RegistryKey<PlacedFeature> CATNIP_PLACED_FEATURE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MintyBlends.MOD_ID, "catnip"));
    public static final RegistryKey<PlacedFeature> MEDICINAL_HERB_PLACED_FEATURE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MintyBlends.MOD_ID, "medicinal_herb"));
    public static final RegistryKey<PlacedFeature> CULINARY_HERB_PLACED_FEATURE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MintyBlends.MOD_ID, "culinary_herb"));
    public static final RegistryKey<PlacedFeature> SAGEBRUSH_PLACED_FEATURE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MintyBlends.MOD_ID, "sagebrush"));
    public static final RegistryKey<PlacedFeature> CUREFLOWER_PLACED_FEATURE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MintyBlends.MOD_ID, "cureflower"));
    public static final RegistryKey<PlacedFeature> SILENT_FLOWER_PLACED_FEATURE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MintyBlends.MOD_ID, "silent_flower"));

    public static void registerWorldgenFeatures() {
        BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.HAS_MINT), GenerationStep.Feature.VEGETAL_DECORATION, MINT_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.HAS_CATNIP), GenerationStep.Feature.VEGETAL_DECORATION, CATNIP_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.HAS_MEDICINAL_HERB), GenerationStep.Feature.VEGETAL_DECORATION, MEDICINAL_HERB_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.HAS_CULINARY_HERB), GenerationStep.Feature.VEGETAL_DECORATION, CULINARY_HERB_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.HAS_SAGEBRUSH), GenerationStep.Feature.VEGETAL_DECORATION, SAGEBRUSH_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.CRIMSON_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, CUREFLOWER_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, SILENT_FLOWER_PLACED_FEATURE);
    }
}
