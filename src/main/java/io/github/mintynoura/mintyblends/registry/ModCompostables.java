package io.github.mintynoura.mintyblends.registry;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModCompostables {
    public static List<Item> compost85 = new ArrayList<>();
    public static List<Item> compost65 = new ArrayList<>();
    public static List<Item> compost30 = new ArrayList<>();

    public static void addCompostables() {
        compost85.add(ModBlocks.PURPLE_HORTENSIA.asItem());
        compost85.add(ModBlocks.PINK_HORTENSIA.asItem());
        compost85.add(ModBlocks.BLUE_HORTENSIA.asItem());

        compost65.add(ModBlocks.MINT.asItem());
        compost65.add(ModBlocks.CATNIP.asItem());
        compost65.add(ModBlocks.MEDICINAL_HERB.asItem());
        compost65.add(ModBlocks.CULINARY_HERB.asItem());
        compost65.add(ModBlocks.SAGEBRUSH.asItem());
        compost65.add(ModBlocks.CUREFLOWER.asItem());
        compost65.add(ModBlocks.RENDFLOWER.asItem());
        compost65.add(ModBlocks.SILENT_FLOWER.asItem());

        compost30.add(ModItems.MINT_LEAVES);
        compost30.add(ModItems.CATNIP_LEAVES);
        compost30.add(ModItems.MEDICINAL_LEAVES);
        compost30.add(ModItems.CULINARY_LEAVES);
        compost30.add(ModItems.SAGEBRUSH_LEAVES);
        compost30.add(ModItems.HORTENSIA_SEEDS);
    }

    public static void compostItems() {
        for (Item item : compost85) {
            CompostingChanceRegistry.INSTANCE.add(item, 0.85f);
        }
        for (Item item : compost65) {
            CompostingChanceRegistry.INSTANCE.add(item, 0.65f);
        }
        for (Item item : compost30) {
            CompostingChanceRegistry.INSTANCE.add(item, 0.30f);
        }
    }

    public static void registerCompostableItems() {
        addCompostables();
        compostItems();
    }
}
