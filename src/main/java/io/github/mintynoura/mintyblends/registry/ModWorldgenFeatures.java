package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.util.ModTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModWorldgenFeatures {
    public static final RegistryKey<PlacedFeature> MINT_PLACED_FEATURE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MintyBlends.MOD_ID, "mint"));

    public static void registerWorldgenFeatures() {
        BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.HAS_MINT), GenerationStep.Feature.VEGETAL_DECORATION, MINT_PLACED_FEATURE);
    }
}
