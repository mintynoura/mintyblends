package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.block.entity.KettleBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.entity.BlockEntityType;

@SuppressWarnings("deprecation")
public class ModBlockEntities {
    public static final BlockEntityType<KettleBlockEntity> KETTLE_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "kettle_block_entity"),
            FabricBlockEntityTypeBuilder.create(KettleBlockEntity::new, ModBlocks.KETTLE).build(null));
    public static final Identifier INTERACT_WITH_KETTLE = makeCustomStat("interact_with_kettle", StatFormatter.DEFAULT);

    public static Identifier makeCustomStat(final String id, final StatFormatter formatter) {
        Identifier location = Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, id);
        Registry.register(BuiltInRegistries.CUSTOM_STAT, id, location);
        Stats.CUSTOM.get(location, formatter);
        return location;
    }

    public static void registerBlockEntities() {
    }
}
