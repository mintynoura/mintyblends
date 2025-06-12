package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModLootTables {
    public static final RegistryKey<LootTable> OCELOT_GIFT = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MintyBlends.MOD_ID, "gameplay/ocelot_gift"));
}
