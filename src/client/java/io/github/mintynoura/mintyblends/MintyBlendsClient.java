package io.github.mintynoura.mintyblends;

import io.github.mintynoura.mintyblends.block.HortensiaCropBlock;
import io.github.mintynoura.mintyblends.particle.KettleSteamParticle;
import io.github.mintynoura.mintyblends.registry.ModBlocks;
import io.github.mintynoura.mintyblends.registry.ModParticleTypes;
import io.github.mintynoura.mintyblends.registry.ModScreenHandlers;
import io.github.mintynoura.mintyblends.screen.KettleScreen;
import io.github.mintynoura.mintyblends.util.ModTags;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

public class MintyBlendsClient implements ClientModInitializer {

	private static final int TEMPERATE_CLIMATE_COLOR = 0x9951df;
	private static final int COLD_CLIMATE_COLOR = 0xf0429c;
	private static final int WARM_CLIMATE_COLOR = 0x4294dd;

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
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_CUREFLOWER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_RENDFLOWER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_SILENT_FLOWER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HORTENSIA_CROP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLE_HORTENSIA, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PINK_HORTENSIA, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUE_HORTENSIA, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.KETTLE, RenderLayer.getCutout());

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