package io.github.mintynoura.mintyblends;

import io.github.mintynoura.mintyblends.block.HortensiaCropBlock;
import io.github.mintynoura.mintyblends.compat.eiv.EivClientIntegration;
import io.github.mintynoura.mintyblends.compat.rrv.RrvClientIntegration;
import io.github.mintynoura.mintyblends.particle.KettleSteamParticle;
import io.github.mintynoura.mintyblends.registry.ModBlocks;
import io.github.mintynoura.mintyblends.registry.ModParticleTypes;
import io.github.mintynoura.mintyblends.registry.ModMenus;
import io.github.mintynoura.mintyblends.screen.KettleScreen;
import io.github.mintynoura.mintyblends.util.ModTags;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import java.util.List;

public class MintyBlendsClient implements ClientModInitializer {

	private static final int TEMPERATE_CLIMATE_COLOR = 0xff9951df;
	private static final int COLD_CLIMATE_COLOR = 0xfff0429c;
	private static final int WARM_CLIMATE_COLOR = 0xff4294dd;

	@Override
	public void onInitializeClient() {
		BlockColorRegistry.register(
				List.of(new BlockTintSource() {
					@Override
					public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
							if (state.getValue(HortensiaCropBlock.HALF) == DoubleBlockHalf.UPPER) {
								pos = pos.below();
							}
							boolean coldTag = level.getBiomeFabric(pos).is(ModTags.Biomes.PRODUCES_PINK_HORTENSIAS);
							boolean temperateTag = level.getBiomeFabric(pos).is(ModTags.Biomes.PRODUCES_PURPLE_HORTENSIAS);
							boolean warmTag = level.getBiomeFabric(pos).is(ModTags.Biomes.PRODUCES_BLUE_HORTENSIAS);

							if (!temperateTag && coldTag && !warmTag) return COLD_CLIMATE_COLOR;
							if (!temperateTag && !coldTag && warmTag) return WARM_CLIMATE_COLOR;
                        return TEMPERATE_CLIMATE_COLOR;
                    }
					@Override
					public int color(BlockState state) {
						return TEMPERATE_CLIMATE_COLOR;
					}

					@Override
					public int colorAsTerrainParticle(BlockState state, BlockAndTintGetter level, BlockPos pos) {
						return 0xffffffff;
					}
				}), ModBlocks.HORTENSIA_CROP);
//		BlockColorRegistry.register((state, view, pos, tintIndex) -> {
//				if (view != null && pos != null) {
//					if (state.getValue(HortensiaCropBlock.HALF) == DoubleBlockHalf.UPPER) {
//						pos = pos.below();
//					}
//					boolean coldTag = view.getBiomeFabric(pos).is(ModTags.Biomes.PRODUCES_PINK_HORTENSIAS);
//					boolean temperateTag = view.getBiomeFabric(pos).is(ModTags.Biomes.PRODUCES_PURPLE_HORTENSIAS);
//					boolean warmTag = view.getBiomeFabric(pos).is(ModTags.Biomes.PRODUCES_BLUE_HORTENSIAS);
//
//					if (!temperateTag && coldTag && !warmTag) return COLD_CLIMATE_COLOR;
//					if (!temperateTag && !coldTag && warmTag) return WARM_CLIMATE_COLOR;
//				}
//				return TEMPERATE_CLIMATE_COLOR;
//
//        }, ModBlocks.HORTENSIA_CROP);

		MenuScreens.register(ModMenus.KETTLE_MENU, KettleScreen::new);

		ParticleProviderRegistry.getInstance().register(ModParticleTypes.KETTLE_STEAM, KettleSteamParticle.Factory::new);

		if (FabricLoader.getInstance().isModLoaded("eiv")) {
			EivClientIntegration.onIntegrationInitialize();
		}
		if (FabricLoader.getInstance().isModLoaded("rrv")) {
			RrvClientIntegration.onIntegrationInitialize();
		}
	}
}