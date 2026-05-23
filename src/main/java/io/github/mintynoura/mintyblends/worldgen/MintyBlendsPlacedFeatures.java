package io.github.mintynoura.mintyblends.worldgen;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.registry.MintyBlendsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

@SuppressWarnings("deprecation")
public class MintyBlendsPlacedFeatures {
    public static final ResourceKey<PlacedFeature> CATNIP_PLACED_FEATURE_KEY = createKey("catnip");
    public static final ResourceKey<PlacedFeature> CULINARY_HERB_PLACED_FEATURE_KEY = createKey("culinary_herb");
    public static final ResourceKey<PlacedFeature> CUREFLOWER_PLACED_FEATURE_KEY = createKey("cureflower");
    public static final ResourceKey<PlacedFeature> MEDICINAL_HERB_PLACED_FEATURE_KEY = createKey("medicinal_herb");
    public static final ResourceKey<PlacedFeature> MINT_PLACED_FEATURE_KEY = createKey("mint");
    public static final ResourceKey<PlacedFeature> SAGEBRUSH_PLACED_FEATURE_KEY = createKey("sagebrush");
    public static final ResourceKey<PlacedFeature> SILENT_FLOWER_PLACED_FEATURE_KEY = createKey("silent_flower");

    public static List<PlacementModifier> catnipPlacementModifiers = List.of(
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
            BiomeFilter.biome(),
            CountPlacement.of(64),
            RandomOffsetPlacement.ofTriangle(4, 2),
            BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
    );
    public static List<PlacementModifier> culinaryHerbPlacementModifiers = List.of(
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
            BiomeFilter.biome(),
            CountPlacement.of(96),
            RandomOffsetPlacement.ofTriangle(7, 3),
            BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
    );
    public static List<PlacementModifier> cureflowerPlacementModifiers = List.of(
            CountOnEveryLayerPlacement.of(3),
            RarityFilter.onAverageOnceEvery(16),
            InSquarePlacement.spread(),
            HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
            BiomeFilter.biome(),
            CountPlacement.of(16),
            RandomOffsetPlacement.ofTriangle(2, 1),
            BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(MintyBlendsBlocks.CUREFLOWER.defaultBlockState(), BlockPos.ZERO))
    );
    public static List<PlacementModifier> medicinalHerbPlacementModifiers = List.of(
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
            BiomeFilter.biome(),
            CountPlacement.of(32),
            RandomOffsetPlacement.ofTriangle(1, 2),
            BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
    );
    public static List<PlacementModifier> mintPlacementModifiers = List.of(
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
            BiomeFilter.biome(),
            CountPlacement.of(64),
            RandomOffsetPlacement.ofTriangle(4, 2),
            BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
    );
    public static List<PlacementModifier> sagebrushPlacementModifiers = List.of(
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
            BiomeFilter.biome(),
            CountPlacement.of(64),
            RandomOffsetPlacement.ofTriangle(4, 2),
            BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
    );
    public static List<PlacementModifier> silentFlowerPlacementModifiers = List.of(
            RarityFilter.onAverageOnceEvery(24),
            InSquarePlacement.spread(),
            HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
            BiomeFilter.biome(),
            CountPlacement.of(8),
            BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
    );

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(MintyBlends.ID, name));
    }

    public static void register(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(CATNIP_PLACED_FEATURE_KEY, new PlacedFeature(configuredFeatures.getOrThrow(MintyBlendsConfiguredFeatures.CATNIP_CONFIGURED_KEY), catnipPlacementModifiers));
        context.register(CULINARY_HERB_PLACED_FEATURE_KEY, new PlacedFeature(configuredFeatures.getOrThrow(MintyBlendsConfiguredFeatures.CULINARY_HERB_CONFIGURED_KEY), culinaryHerbPlacementModifiers));
        context.register(CUREFLOWER_PLACED_FEATURE_KEY, new PlacedFeature(configuredFeatures.getOrThrow(MintyBlendsConfiguredFeatures.CUREFLOWER_CONFIGURED_KEY), cureflowerPlacementModifiers));
        context.register(MEDICINAL_HERB_PLACED_FEATURE_KEY, new PlacedFeature(configuredFeatures.getOrThrow(MintyBlendsConfiguredFeatures.MEDICINAL_HERB_CONFIGURED_KEY), medicinalHerbPlacementModifiers));
        context.register(MINT_PLACED_FEATURE_KEY, new PlacedFeature(configuredFeatures.getOrThrow(MintyBlendsConfiguredFeatures.MINT_CONFIGURED_KEY), mintPlacementModifiers));
        context.register(SAGEBRUSH_PLACED_FEATURE_KEY, new PlacedFeature(configuredFeatures.getOrThrow(MintyBlendsConfiguredFeatures.SAGEBRUSH_CONFIGURED_KEY), sagebrushPlacementModifiers));
        context.register(SILENT_FLOWER_PLACED_FEATURE_KEY, new PlacedFeature(configuredFeatures.getOrThrow(MintyBlendsConfiguredFeatures.SILENT_FLOWER_CONFIGURED_KEY), silentFlowerPlacementModifiers));
    }
}
