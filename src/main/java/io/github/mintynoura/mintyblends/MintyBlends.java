package io.github.mintynoura.mintyblends;

import com.mojang.serialization.Codec;
import io.github.mintynoura.mintyblends.registry.ModBlocks;
import io.github.mintynoura.mintyblends.registry.ModCompostables;
import io.github.mintynoura.mintyblends.registry.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MintyBlends implements ModInitializer {
	public static final String MOD_ID = "mintyblends";

	public static final AttachmentType<Integer> CATNIP_COOLDOWN = AttachmentRegistry.createPersistent(Identifier.of(MOD_ID, "catnip_cooldown"), Codec.INT);

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.addModBlocks();
		ModItems.addModItems();
		ModCompostables.registerCompostableItems();
	}
}