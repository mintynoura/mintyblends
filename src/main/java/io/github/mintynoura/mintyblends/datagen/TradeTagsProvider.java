package io.github.mintynoura.mintyblends.datagen;

import io.github.mintynoura.mintyblends.registry.ModTrades;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.VillagerTradeTags;
import net.minecraft.world.item.trading.VillagerTrade;

import java.util.concurrent.CompletableFuture;

public class TradeTagsProvider extends FabricTagsProvider<VillagerTrade> {

    public TradeTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, Registries.VILLAGER_TRADE, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        this.builder(VillagerTradeTags.WANDERING_TRADER_COMMON)
                .add(ModTrades.WANDERING_TRADER_EMERALD_CATNIP)
                .add(ModTrades.WANDERING_TRADER_EMERALD_CULINARY_HERB)
                .add(ModTrades.WANDERING_TRADER_EMERALD_MEDICINAL_HERB)
                .add(ModTrades.WANDERING_TRADER_EMERALD_MINT)
                .add(ModTrades.WANDERING_TRADER_EMERALD_SAGEBRUSH)
                .add(ModTrades.WANDERING_TRADER_EMERALD_SILENT_FLOWER);
    }
}
