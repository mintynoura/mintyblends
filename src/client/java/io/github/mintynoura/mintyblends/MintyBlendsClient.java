package io.github.mintynoura.mintyblends;

import io.github.mintynoura.mintyblends.block.HortensiaCropBlock;
import io.github.mintynoura.mintyblends.compat.eiv.EivClientIntegration;
import io.github.mintynoura.mintyblends.compat.rrv.RrvClientIntegration;
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
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class MintyBlendsClient implements ClientModInitializer {

	private static final int TEMPERATE_CLIMATE_COLOR = 0x9951df;
	private static final int COLD_CLIMATE_COLOR = 0xf0429c;
	private static final int WARM_CLIMATE_COLOR = 0x4294dd;

	@Override
	public void onInitializeClient() {

		BlockRenderLayerMap.putBlock(ModBlocks.MINT, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.CATNIP, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.MEDICINAL_HERB, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.CULINARY_HERB, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.SAGEBRUSH, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.CUREFLOWER, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.RENDFLOWER, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.SILENT_FLOWER, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.POTTED_CUREFLOWER, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.POTTED_RENDFLOWER, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.POTTED_SILENT_FLOWER, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.HORTENSIA_CROP, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.PURPLE_HORTENSIA, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.PINK_HORTENSIA, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.BLUE_HORTENSIA, ChunkSectionLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.KETTLE, ChunkSectionLayer.CUTOUT);

		ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
				if (view != null && pos != null) {
					if (state.getValue(HortensiaCropBlock.HALF) == DoubleBlockHalf.UPPER) {
						pos = pos.below();
					}
					boolean coldTag = view.getBiomeFabric(pos).is(ModTags.Biomes.PRODUCES_PINK_HORTENSIAS);
					boolean temperateTag = view.getBiomeFabric(pos).is(ModTags.Biomes.PRODUCES_PURPLE_HORTENSIAS);
					boolean warmTag = view.getBiomeFabric(pos).is(ModTags.Biomes.PRODUCES_BLUE_HORTENSIAS);

					if (!temperateTag && coldTag && !warmTag) return COLD_CLIMATE_COLOR;
					if (!temperateTag && !coldTag && warmTag) return WARM_CLIMATE_COLOR;
				}
				return TEMPERATE_CLIMATE_COLOR;

        }, ModBlocks.HORTENSIA_CROP);

		HandledScreen.register(ModScreenHandlers.KETTLE_SCREEN_HANDLER, KettleScreen::new);

		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.KETTLE_STEAM, KettleSteamParticle.Factory::new);

		if (FabricLoader.getInstance().isModLoaded("eiv")) {
			EivClientIntegration.onIntegrationInitialize();
		}
		if (FabricLoader.getInstance().isModLoaded("rrv")) {
			RrvClientIntegration.onIntegrationInitialize();
		}
	}
}