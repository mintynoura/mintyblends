package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.block.*;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import java.util.function.Function;

import static net.minecraft.world.level.block.Blocks.litBlockEmission;

// TODO: placeable brews and censers
public class MintyBlendsBlocks {
    public static final Block MINT = registerBlock("mint", properties -> new HerbBlock(MobEffects.FIRE_RESISTANCE, 3f, properties), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.CHERRY_SAPLING)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY),
            true);
    public static final Block CATNIP = registerBlock("catnip", properties -> new HerbBlock(MobEffects.SPEED, 5f, properties), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.CHERRY_SAPLING)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY),
            true);
    public static final Block MEDICINAL_HERB = registerBlock("medicinal_herb", properties -> new HerbBlock(MobEffects.INSTANT_HEALTH, 0.05f, properties), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.CHERRY_SAPLING)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY),
            true);
    public static final Block CULINARY_HERB = registerBlock("culinary_herb", properties -> new HerbBlock(MobEffects.SATURATION, 0.35f, properties), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.CHERRY_SAPLING)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY),
            true);
    public static final Block SAGEBRUSH = registerBlock("sagebrush", properties -> new DesertHerbBlock(MobEffects.STRENGTH, 5f, properties), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.CHERRY_SAPLING)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY),
            true);

    public static final Block CUREFLOWER = registerBlock("cureflower", properties -> new NetherFlowerBlock(MobEffects.REGENERATION, 7f, properties), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.NETHER)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.NETHER_SPROUTS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY),
            false);
    public static final Block RENDFLOWER = registerBlock("rendflower", properties -> new NetherFlowerBlock(MintyBlendsStatusEffects.RENDING, 9f, properties), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.NETHER_SPROUTS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY),
            false);
    public static final Block SILENT_FLOWER = registerBlock("silent_flower", properties -> new FlowerBlock(MintyBlendsStatusEffects.STEALTH, 7f, properties), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)
                    .lightLevel(_ -> 3),
            true);
    public static final Block INFERNALILY = registerBlock("infernalily", properties -> new InfernalilyBlock(MobEffects.FIRE_RESISTANCE, 7f, properties), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.CRIMSON_HYPHAE)
                    .instabreak()
                    .sound(SoundType.LILY_PAD)
                    .noOcclusion()
                    .pushReaction(PushReaction.DESTROY),
            false);

    public static final Block POTTED_CUREFLOWER = registerBlock("potted_cureflower", properties -> new FlowerPotBlock(CUREFLOWER, properties), Blocks.flowerPotProperties(), false);
    public static final Block POTTED_RENDFLOWER = registerBlock("potted_rendflower", properties -> new FlowerPotBlock(RENDFLOWER, properties), Blocks.flowerPotProperties(), false);
    public static final Block POTTED_SILENT_FLOWER = registerBlock("potted_silent_flower", properties -> new FlowerPotBlock(SILENT_FLOWER, properties), Blocks.flowerPotProperties(), false);

    public static final Block HORTENSIA_CROP = registerBlock("hortensia_crop", HortensiaCropBlock::new, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY),
            false);
    public static final Block PURPLE_HORTENSIA = registerBlock("purple_hortensia", DoublePlantBlock::new, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY),
            true);
    public static final Block PINK_HORTENSIA = registerBlock("pink_hortensia", DoublePlantBlock::new, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY),
            true);
    public static final Block BLUE_HORTENSIA = registerBlock("blue_hortensia", DoublePlantBlock::new, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY),
            true);

    public static final Block KETTLE = registerBlock("kettle", KettleBlock::new, BlockBehaviour.Properties.of()
                    .strength(4f)
                    .sound(SoundType.LANTERN)
                    .lightLevel(litBlockEmission(7))
                    .noOcclusion(),
            true);

    public static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties, boolean shouldRegisterItem) {
        ResourceKey<Block> blockRegistryKey = keyOfBlock(name);
        Block block = factory.apply(properties.setId(blockRegistryKey));
        if (shouldRegisterItem) {
            registerBlockItem(name, block);
        }
        return Registry.register(BuiltInRegistries.BLOCK, blockRegistryKey, block);
    }

    public static void registerBlockItem(String name, Block block) {
        ResourceKey<Item> itemRegistryKey = keyOfItem(name);
        Registry.register(BuiltInRegistries.ITEM, itemRegistryKey, new BlockItem(block, new Item.Properties().setId(itemRegistryKey).useBlockDescriptionPrefix()));
    }

    private static ResourceKey<Block> keyOfBlock(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MintyBlends.ID, name));
    }

    private static ResourceKey<Item> keyOfItem(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MintyBlends.ID, name));
    }

    public static void initialize() {
        FlammableBlockRegistry.getDefaultInstance().add(MintyBlendsBlocks.MINT, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(MintyBlendsBlocks.CATNIP, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(MintyBlendsBlocks.MEDICINAL_HERB, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(MintyBlendsBlocks.CULINARY_HERB, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(MintyBlendsBlocks.SAGEBRUSH, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(MintyBlendsBlocks.SILENT_FLOWER, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(MintyBlendsBlocks.PURPLE_HORTENSIA, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(MintyBlendsBlocks.PINK_HORTENSIA, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(MintyBlendsBlocks.BLUE_HORTENSIA, 100, 60);
    }
}
