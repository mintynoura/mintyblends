package io.github.mintynoura.mintyblends;

import io.github.mintynoura.mintyblends.datagen.BlockTagProvider;
import io.github.mintynoura.mintyblends.datagen.ModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MintyBlendsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(BlockTagProvider::new);
		pack.addProvider(ModelProvider::new);
	}
}
