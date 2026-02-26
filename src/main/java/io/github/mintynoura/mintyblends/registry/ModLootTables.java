package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ModLootTables {
    private static final ResourceKey<LootTable> SNIFFER_DIGGING_LOOT_TABLE_KEY = BuiltInLootTables.SNIFFER_DIGGING;
    private static final ResourceKey<LootTable> PIGLIN_BARTERING_LOOT_TABLE_KEY = BuiltInLootTables.PIGLIN_BARTERING;

    public static void modify() {
        LootTableEvents.MODIFY.register((id, tableBuilder, source, registries) -> {
            if (source.isBuiltin() && SNIFFER_DIGGING_LOOT_TABLE_KEY.equals(id)) {
                tableBuilder.modifyPools(poolBuilder -> poolBuilder.add(LootItem.lootTableItem(ModItems.HORTENSIA_SEEDS)));
            }
            if (source.isBuiltin() && PIGLIN_BARTERING_LOOT_TABLE_KEY.equals(id)) {
                tableBuilder.modifyPools(poolBuilder -> poolBuilder.add(LootItem.lootTableItem(ModBlocks.CUREFLOWER).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 5)))));
            }
        }
        );
    }

    public static final ResourceKey<LootTable> OCELOT_GIFT = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "gameplay/ocelot_gift"));

    public static void registerLootTables() {
        modify();
    }
}
