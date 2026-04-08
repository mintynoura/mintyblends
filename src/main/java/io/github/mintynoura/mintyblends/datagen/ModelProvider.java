//package io.github.mintynoura.mintyblends.datagen;
//
//import io.github.mintynoura.mintyblends.compat.farmersdelight.FarmersDelightCompat;
//import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
//import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
//import net.minecraft.client.data.models.BlockModelGenerators;
//import net.minecraft.client.data.models.ItemModelGenerators;
//import net.minecraft.client.data.models.model.ModelTemplates;
//
//public class ModelProvider extends FabricModelProvider {
//    public ModelProvider(FabricDataOutput output) {
//        super(output);
//    }
//
//    @Override
//    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
//
//    }
//
//    @Override
//    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
//        itemModelGenerator.generateFlatItem(FarmersDelightCompat.FRIED_GREENS, ModelTemplates.FLAT_ITEM);
//        itemModelGenerator.generateFlatItem(FarmersDelightCompat.ONION_SOUP, ModelTemplates.FLAT_ITEM);
//        itemModelGenerator.generateFlatItem(FarmersDelightCompat.TOMATO_SOUP, ModelTemplates.FLAT_ITEM);
//        itemModelGenerator.generateFlatItem(FarmersDelightCompat.VEGETABLE_PORRIDGE, ModelTemplates.FLAT_ITEM);
//    }
//}
