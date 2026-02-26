package io.github.mintynoura.mintyblends;

import com.mojang.serialization.Codec;
import io.github.mintynoura.mintyblends.compat.farmersdelight.FarmersDelightCompat;
import io.github.mintynoura.mintyblends.registry.*;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
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
		ModScreenHandlers.registerScreenHandlers();
		ModRecipes.registerRecipes();
		ModLootTables.registerLootTables();
		ModStatusEffects.registerStatusEffects();
		ModComponents.registerComponents();
		ModSoundEvents.registerSoundEffects();
		ModParticleTypes.registerParticleTypes();
		ModWorldgenFeatures.registerWorldgenFeatures();
		ModTrades.registerTrades();

		if (FabricLoader.getInstance().isModLoaded("farmersdelight")) {
			FarmersDelightCompat.registerItems();
		}
	}
}