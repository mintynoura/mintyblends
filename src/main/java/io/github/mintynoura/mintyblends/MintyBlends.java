package io.github.mintynoura.mintyblends;

import com.mojang.serialization.Codec;
import io.github.mintynoura.mintyblends.compat.farmersdelight.FarmersDelightCompat;
import io.github.mintynoura.mintyblends.registry.*;
import io.github.mintynoura.mintyblends.util.ModTags;
import io.github.mintynoura.mintyblends.worldgen.ModPlacedFeatures;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;

public class MintyBlends implements ModInitializer {
	public static final String MOD_ID = "mintyblends";

	public static final AttachmentType<Integer> CATNIP_COOLDOWN = AttachmentRegistry.createPersistent(Identifier.fromNamespaceAndPath(MOD_ID, "catnip_cooldown"), Codec.INT);

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final MintyBlendsConfig CONFIG = MintyBlendsConfig.createToml(Paths.get("config"), "", "mintyblends", MintyBlendsConfig.class);

	@Override
	public void onInitialize() {
		ModBlocks.registerBlocks();
		ModItems.registerItems();
		ModCompostables.registerCompostableItems();
		ModBlockEntities.registerBlockEntities();
		ModMenus.registerMenus();
		ModRecipes.registerRecipes();
		ModLootTables.registerLootTables();
		ModStatusEffects.registerStatusEffects();
		ModComponents.registerComponents();
		ModSoundEvents.registerSoundEffects();
		ModParticleTypes.registerParticleTypes();

		BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.HAS_CATNIP), GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.CATNIP_PLACED_FEATURE_KEY);
		BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.HAS_CULINARY_HERB), GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.CULINARY_HERB_PLACED_FEATURE_KEY);
		BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.CRIMSON_FOREST), GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.CUREFLOWER_PLACED_FEATURE_KEY);
		BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.HAS_MEDICINAL_HERB), GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.MEDICINAL_HERB_PLACED_FEATURE_KEY);
		BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.HAS_MINT), GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.MINT_PLACED_FEATURE_KEY);
		BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.HAS_SAGEBRUSH), GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.SAGEBRUSH_PLACED_FEATURE_KEY);
		BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.DARK_FOREST), GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.SILENT_FLOWER_PLACED_FEATURE_KEY);

		if (FabricLoader.getInstance().isModLoaded("farmersdelight")) {
			FarmersDelightCompat.registerItems();
		}
	}
}