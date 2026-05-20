package io.github.mintynoura.mintyblends.worldgen;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.registry.MintyBlendsBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class MintyBlendsConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> CATNIP_CONFIGURED_KEY = createKey("catnip");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CULINARY_HERB_CONFIGURED_KEY = createKey("culinary_herb");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CUREFLOWER_CONFIGURED_KEY = createKey("cureflower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEDICINAL_HERB_CONFIGURED_KEY = createKey("medicinal_herb");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MINT_CONFIGURED_KEY = createKey("mint");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SAGEBRUSH_CONFIGURED_KEY = createKey("sagebrush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILENT_FLOWER_CONFIGURED_KEY = createKey("silent_flower");

    public static SimpleBlockConfiguration catnipFeature = new SimpleBlockConfiguration(BlockStateProvider.simple(MintyBlendsBlocks.CATNIP));
    public static SimpleBlockConfiguration culinaryHerbFeature = new SimpleBlockConfiguration(BlockStateProvider.simple(MintyBlendsBlocks.CULINARY_HERB));
    public static SimpleBlockConfiguration cureflowerFeature = new SimpleBlockConfiguration(BlockStateProvider.simple(MintyBlendsBlocks.CUREFLOWER));
    public static SimpleBlockConfiguration medicinalHerbFeature = new SimpleBlockConfiguration(BlockStateProvider.simple(MintyBlendsBlocks.MEDICINAL_HERB));
    public static SimpleBlockConfiguration mintFeature = new SimpleBlockConfiguration(BlockStateProvider.simple(MintyBlendsBlocks.MINT));
    public static SimpleBlockConfiguration sagebrushFeature = new SimpleBlockConfiguration(BlockStateProvider.simple(MintyBlendsBlocks.SAGEBRUSH));
    public static SimpleBlockConfiguration silentFlowerFeature = new SimpleBlockConfiguration(BlockStateProvider.simple(MintyBlendsBlocks.SILENT_FLOWER));

    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(MintyBlends.ID, name));
    }

    public static void register(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        context.register(CATNIP_CONFIGURED_KEY, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, catnipFeature));
        context.register(CULINARY_HERB_CONFIGURED_KEY, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, culinaryHerbFeature));
        context.register(CUREFLOWER_CONFIGURED_KEY, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, cureflowerFeature));
        context.register(MEDICINAL_HERB_CONFIGURED_KEY, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, medicinalHerbFeature));
        context.register(MINT_CONFIGURED_KEY, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, mintFeature));
        context.register(SAGEBRUSH_CONFIGURED_KEY, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, sagebrushFeature));
        context.register(SILENT_FLOWER_CONFIGURED_KEY, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, silentFlowerFeature));
    }
}
