package io.github.mintynoura.mintyblends.datagen;

import io.github.mintynoura.mintyblends.registry.ModTrades;
//import io.github.mintynoura.mintyblends.worldgen.ModConfiguredFeatures;
//import io.github.mintynoura.mintyblends.worldgen.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class MintyBlendsDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
//		registryBuilder.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::register);
//		registryBuilder.add(Registries.PLACED_FEATURE, ModPlacedFeatures::register);
		registryBuilder.add(Registries.VILLAGER_TRADE, ModTrades::register);
	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
	//	pack.addProvider(WorldgenProvider::new);
		pack.addProvider(TradeTagsProvider::new);
		pack.addProvider(TradeProvider::new);
	}
}
