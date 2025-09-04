package io.github.mintynoura.mintyblends;

import io.github.mintynoura.mintyblends.datagen.CompatRecipeProvider;
import io.github.mintynoura.mintyblends.datagen.ModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MintyBlendsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(CompatRecipeProvider::new);
		pack.addProvider(ModelProvider::new);
	}
}
