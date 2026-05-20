package io.github.mintynoura.mintyblends.registry;


import io.github.mintynoura.mintyblends.MintyBlends;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.item.trading.VillagerTrades;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Optional;

public class MintyBlendsTrades {
    public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_CATNIP = createKey("wandering_trader/emerald_catnip");
    public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_CULINARY_HERB = createKey("wandering_trader/emerald_culinary_herb");
    public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_MEDICINAL_HERB = createKey("wandering_trader/emerald_medicinal_herb");
    public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_MINT = createKey("wandering_trader/emerald_mint");
    public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_SAGEBRUSH = createKey("wandering_trader/emerald_sagebrush");
    public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_SILENT_FLOWER = createKey("wandering_trader/emerald_silent_flower");

   private static VillagerTrade registerHerbTrade(Block block) {
        return new VillagerTrade(new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(block.asItem(), 2), 6, 1, 0.05F, Optional.empty(), List.of());
    }
    private static ResourceKey<VillagerTrade> createKey(String name) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(MintyBlends.ID, name));
    }

    public static void register(BootstrapContext<VillagerTrade> context) {
        VillagerTrades.register(context, WANDERING_TRADER_EMERALD_CATNIP, registerHerbTrade(MintyBlendsBlocks.CATNIP));
        VillagerTrades.register(context, WANDERING_TRADER_EMERALD_CULINARY_HERB, registerHerbTrade(MintyBlendsBlocks.CULINARY_HERB));
        VillagerTrades.register(context, WANDERING_TRADER_EMERALD_MEDICINAL_HERB, registerHerbTrade(MintyBlendsBlocks.MEDICINAL_HERB));
        VillagerTrades.register(context, WANDERING_TRADER_EMERALD_MINT, registerHerbTrade(MintyBlendsBlocks.MINT));
        VillagerTrades.register(context, WANDERING_TRADER_EMERALD_SAGEBRUSH, registerHerbTrade(MintyBlendsBlocks.SAGEBRUSH));
        VillagerTrades.register(context, WANDERING_TRADER_EMERALD_SILENT_FLOWER,
                new VillagerTrade(new TradeCost(Items.EMERALD, 1), new ItemStackTemplate(MintyBlendsBlocks.SILENT_FLOWER.asItem()), 7, 1, 0.05F, Optional.empty(), List.of())
        );
    }
}
