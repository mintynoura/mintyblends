package io.github.mintynoura.mintyblends.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

// TODO: kettle brewing datagen
public class MintyBlendsDataGenerator implements DataGeneratorEntrypoint {

//	@Override
//	public void buildRegistry(RegistrySetBuilder registryBuilder) {
//		registryBuilder.add(Registries.CONFIGURED_FEATURE, MintyBlendsConfiguredFeatures::register);
//		registryBuilder.add(Registries.PLACED_FEATURE, MintyBlendsPlacedFeatures::register);
//		registryBuilder.add(Registries.VILLAGER_TRADE, MintyBlendsTrades::register);
//	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(MintyBlendsRecipeProvider::new);
//		pack.addProvider(WorldgenProvider::new);
//		pack.addProvider(TradeTagsProvider::new);
//		pack.addProvider(TradeProvider::new);
	}
}
