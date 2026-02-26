package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.block.entity.KettleBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;

@SuppressWarnings("deprecation")
public class ModBlockEntities {
    public static final BlockEntityType<KettleBlockEntity> KETTLE_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "kettle_block_entity"),
            FabricBlockEntityTypeBuilder.create(KettleBlockEntity::new, ModBlocks.KETTLE).build(null));

    public static void registerBlockEntities() {

    }
}
