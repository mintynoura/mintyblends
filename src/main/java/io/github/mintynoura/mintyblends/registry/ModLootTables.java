package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModLootTables {
    private static final RegistryKey<LootTable> SNIFFER_DIGGING_LOOT_TABLE_KEY = LootTables.SNIFFER_DIGGING_GAMEPLAY;
    private static final RegistryKey<LootTable> PIGLIN_BARTERING_LOOT_TABLE_KEY = LootTables.PIGLIN_BARTERING_GAMEPLAY;

    public static void modify() {
        LootTableEvents.MODIFY.register((id, tableBuilder, source, registries) -> {
            if (source.isBuiltin() && SNIFFER_DIGGING_LOOT_TABLE_KEY.equals(id)) {
                tableBuilder.modifyPools(poolBuilder -> poolBuilder.with(ItemEntry.builder(ModItems.HORTENSIA_SEEDS)));
            }
            if (source.isBuiltin() && PIGLIN_BARTERING_LOOT_TABLE_KEY.equals(id)) {
                tableBuilder.modifyPools(poolBuilder -> poolBuilder.with(ItemEntry.builder(ModBlocks.CUREFLOWER).weight(10).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3, 5)))));
            }
        }
        );
    }

    public static final RegistryKey<LootTable> OCELOT_GIFT = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MintyBlends.MOD_ID, "gameplay/ocelot_gift"));

    public static void registerLootTables() {
        modify();
    }
}
