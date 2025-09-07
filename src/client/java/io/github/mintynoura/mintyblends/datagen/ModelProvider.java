package io.github.mintynoura.mintyblends.datagen;

//import io.github.mintynoura.mintyblends.compat.farmersdelight.FarmersDelightCompat;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
//        itemModelGenerator.register(FarmersDelightCompat.FRIED_GREENS, Models.GENERATED);
//        itemModelGenerator.register(FarmersDelightCompat.ONION_SOUP, Models.GENERATED);
//        itemModelGenerator.register(FarmersDelightCompat.TOMATO_SOUP, Models.GENERATED);
//        itemModelGenerator.register(FarmersDelightCompat.VEGETABLE_PORRIDGE, Models.GENERATED);
    }
}
