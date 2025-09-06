package io.github.mintynoura.mintyblends;

import io.github.mintynoura.mintyblends.block.HortensiaCropBlock;
import io.github.mintynoura.mintyblends.particle.KettleSteamParticle;
import io.github.mintynoura.mintyblends.registry.ModBlocks;
import io.github.mintynoura.mintyblends.registry.ModParticleTypes;
import io.github.mintynoura.mintyblends.registry.ModScreenHandlers;
import io.github.mintynoura.mintyblends.screen.KettleScreen;
import io.github.mintynoura.mintyblends.util.ModTags;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.BlockRenderLayer;

public class MintyBlendsClient implements ClientModInitializer {

	private static final int TEMPERATE_CLIMATE_COLOR = 0x9951df;
	private static final int COLD_CLIMATE_COLOR = 0xf0429c;
	private static final int WARM_CLIMATE_COLOR = 0x4294dd;

	@Override
	public void onInitializeClient() {

		BlockRenderLayerMap.putBlock(ModBlocks.MINT, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.CATNIP, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.MEDICINAL_HERB, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.CULINARY_HERB, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.SAGEBRUSH, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.CUREFLOWER, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.RENDFLOWER, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.SILENT_FLOWER, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.POTTED_CUREFLOWER, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.POTTED_RENDFLOWER, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.POTTED_SILENT_FLOWER, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.HORTENSIA_CROP, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.PURPLE_HORTENSIA, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.PINK_HORTENSIA, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.BLUE_HORTENSIA, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.KETTLE, BlockRenderLayer.CUTOUT);

		ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
				if (view != null && pos != null) {
					if (state.get(HortensiaCropBlock.HALF) == DoubleBlockHalf.UPPER) {
						pos = pos.down();
					}
					boolean coldTag = view.getBiomeFabric(pos).isIn(ModTags.Biomes.PRODUCES_PINK_HORTENSIAS);
					boolean temperateTag = view.getBiomeFabric(pos).isIn(ModTags.Biomes.PRODUCES_PURPLE_HORTENSIAS);
					boolean warmTag = view.getBiomeFabric(pos).isIn(ModTags.Biomes.PRODUCES_BLUE_HORTENSIAS);

					if (!temperateTag && coldTag && !warmTag) return COLD_CLIMATE_COLOR;
					if (!temperateTag && !coldTag && warmTag) return WARM_CLIMATE_COLOR;
				}
				return TEMPERATE_CLIMATE_COLOR;

        }, ModBlocks.HORTENSIA_CROP);

		HandledScreens.register(ModScreenHandlers.KETTLE_SCREEN_HANDLER, KettleScreen::new);

		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.KETTLE_STEAM, KettleSteamParticle.Factory::new);
	}
}