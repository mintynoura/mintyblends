package io.github.mintynoura.mintyblends;

import io.github.mintynoura.mintyblends.registry.ModBlocks;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MintyBlends implements ModInitializer {
	public static final String MOD_ID = "mintyblends";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
	}
}