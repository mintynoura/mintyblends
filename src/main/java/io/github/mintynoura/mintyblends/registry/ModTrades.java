package io.github.mintynoura.mintyblends.registry;


import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.block.Block;
import net.minecraft.village.TradeOffers;

public class ModTrades {

    public static void registerTrades() {
        registerHerbTrade(ModBlocks.MINT);
        registerHerbTrade(ModBlocks.CATNIP);
        registerHerbTrade(ModBlocks.MEDICINAL_HERB);
        registerHerbTrade(ModBlocks.CULINARY_HERB);
        registerHerbTrade(ModBlocks.SAGEBRUSH);

        TradeOfferHelper.registerWanderingTraderOffers(factories ->
                factories.addOffersToPool(TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL, new TradeOffers.SellItemFactory(ModBlocks.SILENT_FLOWER.asItem(), 1, 1, 7, 1)));
    }

    private static void registerHerbTrade(Block block) {
        TradeOfferHelper.registerWanderingTraderOffers(factories ->
                factories.addOffersToPool(TradeOfferHelper.WanderingTraderOffersBuilder.SELL_COMMON_ITEMS_POOL, new TradeOffers.SellItemFactory(block.asItem(), 3, 2, 6, 1)));
    }
}
