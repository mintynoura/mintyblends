package io.github.mintynoura.mintyblends;

import io.github.mintynoura.mintyblends.registry.ModBlocks;
import io.github.mintynoura.mintyblends.registry.ModScreenHandlers;
import io.github.mintynoura.mintyblends.screen.KettleScreen;
import io.github.mintynoura.mintyblends.util.ModTags;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

public class MintyBlendsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MINT, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CATNIP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDICINAL_HERB, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CULINARY_HERB, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SAGEBRUSH, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CUREFLOWER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RENDFLOWER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SILENT_FLOWER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HORTENSIA_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLE_HORTENSIA, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PINK_HORTENSIA, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUE_HORTENSIA, RenderLayer.getCutout());

		ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
				if (view != null && pos != null) {
					boolean coldTag = view.getBiomeFabric(pos).isIn(ModTags.Biomes.PRODUCES_COLD_HORTENSIAS);
					boolean temperateTag = view.getBiomeFabric(pos).isIn(ModTags.Biomes.PRODUCES_TEMPERATE_HORTENSIAS);
					boolean warmTag = view.getBiomeFabric(pos).isIn(ModTags.Biomes.PRODUCES_WARM_HORTENSIAS);
					if (temperateTag || (coldTag && warmTag) || (!coldTag && !warmTag)) {
						return 0x9945eb;
					} else if (coldTag) {
						return 0xff339c;
					} else {
						return 0x3495eb;
					}
				} else return 0x9945eb;
        }, ModBlocks.HORTENSIA_CROP);

		HandledScreens.register(ModScreenHandlers.KETTLE_SCREEN_HANDLER, KettleScreen::new);
	}
}