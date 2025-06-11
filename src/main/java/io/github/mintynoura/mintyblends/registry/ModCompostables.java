package io.github.mintynoura.mintyblends.registry;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.item.Item;

public class ModCompostables {
    public static void registerCompostableItems() {
        compostSixtyFive(ModBlocks.MINT.asItem());
        compostSixtyFive(ModBlocks.CATNIP.asItem());
        compostSixtyFive(ModBlocks.MEDICINAL_HERB.asItem());
        compostSixtyFive(ModBlocks.CULINARY_HERB.asItem());

        compostThirty(ModItems.MINT_LEAVES);
        compostThirty(ModItems.CATNIP_LEAVES);
        compostThirty(ModItems.MEDICINAL_LEAVES);
        compostThirty(ModItems.CULINARY_LEAVES);
    }

    public static void compostSixtyFive(Item item) {
        CompostingChanceRegistry.INSTANCE.add(item, 0.65f);
    }

    public static void compostThirty(Item item) {
        CompostingChanceRegistry.INSTANCE.add(item, 0.3f);
    }
}
