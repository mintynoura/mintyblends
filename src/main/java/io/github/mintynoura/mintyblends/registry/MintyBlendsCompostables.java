package io.github.mintynoura.mintyblends.registry;

import net.fabricmc.fabric.api.registry.CompostableRegistry;
import net.minecraft.world.item.Item;
import java.util.ArrayList;
import java.util.List;

public class MintyBlendsCompostables {
    public static List<Item> compost85 = new ArrayList<>();
    public static List<Item> compost65 = new ArrayList<>();
    public static List<Item> compost30 = new ArrayList<>();

    public static void addCompostables() {
        compost85.add(MintyBlendsBlocks.PURPLE_HORTENSIA.asItem());
        compost85.add(MintyBlendsBlocks.PINK_HORTENSIA.asItem());
        compost85.add(MintyBlendsBlocks.BLUE_HORTENSIA.asItem());

        compost65.add(MintyBlendsBlocks.MINT.asItem());
        compost65.add(MintyBlendsBlocks.CATNIP.asItem());
        compost65.add(MintyBlendsBlocks.MEDICINAL_HERB.asItem());
        compost65.add(MintyBlendsBlocks.CULINARY_HERB.asItem());
        compost65.add(MintyBlendsBlocks.SAGEBRUSH.asItem());
        compost65.add(MintyBlendsItems.CUREFLOWER.asItem());
        compost65.add(MintyBlendsItems.RENDFLOWER.asItem());
        compost65.add(MintyBlendsBlocks.SILENT_FLOWER.asItem());

        compost30.add(MintyBlendsItems.MINT_LEAVES);
        compost30.add(MintyBlendsItems.CATNIP_LEAVES);
        compost30.add(MintyBlendsItems.MEDICINAL_LEAVES);
        compost30.add(MintyBlendsItems.CULINARY_LEAVES);
        compost30.add(MintyBlendsItems.SAGEBRUSH_LEAVES);
        compost30.add(MintyBlendsItems.HORTENSIA_SEEDS);
    }

    public static void compostItems() {
        for (Item item : compost85) {
            CompostableRegistry.INSTANCE.add(item, 0.85f);
        }
        for (Item item : compost65) {
            CompostableRegistry.INSTANCE.add(item, 0.65f);
        }
        for (Item item : compost30) {
            CompostableRegistry.INSTANCE.add(item, 0.30f);
        }
    }

    public static void registerCompostableItems() {
        addCompostables();
        compostItems();
    }
}
