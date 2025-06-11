package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.item.CatnipLeavesItem;
import io.github.mintynoura.mintyblends.item.MedicinalLeavesItem;
import io.github.mintynoura.mintyblends.item.MintLeavesItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
    public static final Item MINT_LEAVES = registerItem("mint_leaves", MintLeavesItem::new, new Item.Settings().food(new FoodComponent(0, 0, true)));
    public static final Item CATNIP_LEAVES = registerItem("catnip_leaves", CatnipLeavesItem::new, new Item.Settings());
    public static final Item MEDICINAL_LEAVES = registerItem("medicinal_leaves", MedicinalLeavesItem::new, new Item.Settings().food(new FoodComponent(0, 0, true)));
    public static final Item CULINARY_LEAVES = registerItem("culinary_leaves", Item::new, new Item.Settings().food(new FoodComponent(2, 0.2f, false)));

    public static final Item HORTENSIA_SEEDS = registerItem("hortensia_seeds", settings -> new BlockItem(ModBlocks.HORTENSIA_CROP, settings), new Item.Settings().useItemPrefixedTranslationKey());

    public static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        RegistryKey<Item> itemRegistryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MintyBlends.MOD_ID, name));
        Item item = factory.apply(settings.registryKey(itemRegistryKey));
        Registry.register(Registries.ITEM, itemRegistryKey, item);
        return item;
    }

    public static void addModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(ModItems.MINT_LEAVES);
            fabricItemGroupEntries.add(ModItems.CATNIP_LEAVES);
            fabricItemGroupEntries.add(ModItems.MEDICINAL_LEAVES);
            fabricItemGroupEntries.add(ModItems.CULINARY_LEAVES);
            fabricItemGroupEntries.add(ModItems.HORTENSIA_SEEDS);
        });
    }
}
