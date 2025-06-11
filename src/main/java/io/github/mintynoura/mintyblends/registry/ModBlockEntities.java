package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.block.entity.KettleBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<KettleBlockEntity> KETTLE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(MintyBlends.MOD_ID, "kettle_block_entity"),
            FabricBlockEntityTypeBuilder.create(KettleBlockEntity::new, ModBlocks.KETTLE).build(null));

    public static void registerBlockEntities() {

    }
}
