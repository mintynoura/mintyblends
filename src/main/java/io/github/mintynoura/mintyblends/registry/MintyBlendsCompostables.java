package io.github.mintynoura.mintyblends.registry;

import net.fabricmc.fabric.api.registry.CompostableRegistry;
import net.minecraft.world.item.Item;

import java.util.List;

public class MintyBlendsCompostables {
    public static List<Item> compost85 = List.of(
            MintyBlendsBlocks.PURPLE_HORTENSIA.asItem(),
            MintyBlendsBlocks.PINK_HORTENSIA.asItem(),
            MintyBlendsBlocks.BLUE_HORTENSIA.asItem()
    );
    public static List<Item> compost65 = List.of(
            MintyBlendsBlocks.MINT.asItem(),
            MintyBlendsBlocks.CATNIP.asItem(),
            MintyBlendsBlocks.MEDICINAL_HERB.asItem(),
            MintyBlendsBlocks.CULINARY_HERB.asItem(),
            MintyBlendsBlocks.SAGEBRUSH.asItem(),
            MintyBlendsItems.CUREFLOWER.asItem(),
            MintyBlendsItems.RENDFLOWER.asItem(),
            MintyBlendsBlocks.SILENT_FLOWER.asItem()
    );
    public static List<Item> compost30 = List.of(
            MintyBlendsItems.MINT_LEAVES,
            MintyBlendsItems.CATNIP_LEAVES,
            MintyBlendsItems.MEDICINAL_LEAVES,
            MintyBlendsItems.CULINARY_LEAVES,
            MintyBlendsItems.SAGEBRUSH_LEAVES,
            MintyBlendsItems.HORTENSIA_SEEDS
    );

    public static void registerCompostables() {
        compost85.forEach(item -> CompostableRegistry.INSTANCE.add(item, 0.85f));
        compost65.forEach(item -> CompostableRegistry.INSTANCE.add(item, 0.65f));
        compost30.forEach(item -> CompostableRegistry.INSTANCE.add(item, 0.30f));
    }

    public static void initialize() {
        registerCompostables();
    }
}
