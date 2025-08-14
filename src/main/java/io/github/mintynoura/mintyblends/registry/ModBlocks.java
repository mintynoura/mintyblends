package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.block.*;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

import static net.minecraft.block.Blocks.createLightLevelFromLitBlockState;

public class ModBlocks {
    public static final Block MINT = registerBlock("mint", settings -> new HerbBlock(StatusEffects.FIRE_RESISTANCE, 3f, settings), AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.CHERRY_SAPLING)
                    .offset(AbstractBlock.OffsetType.XZ)
                    .burnable()
                    .pistonBehavior(PistonBehavior.DESTROY),
            true);
    public static final Block CATNIP = registerBlock("catnip", settings -> new HerbBlock(StatusEffects.SPEED, 5f, settings), AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.CHERRY_SAPLING)
                    .offset(AbstractBlock.OffsetType.XZ)
                    .burnable()
                    .pistonBehavior(PistonBehavior.DESTROY),
            true);
    public static final Block MEDICINAL_HERB = registerBlock("medicinal_herb", settings -> new HerbBlock(StatusEffects.INSTANT_HEALTH, 1f, settings), AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.CHERRY_SAPLING)
                    .offset(AbstractBlock.OffsetType.XZ)
                    .burnable()
                    .pistonBehavior(PistonBehavior.DESTROY),
            true);
    public static final Block CULINARY_HERB = registerBlock("culinary_herb", settings -> new HerbBlock(StatusEffects.SATURATION, 0.35f, settings), AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.CHERRY_SAPLING)
                    .offset(AbstractBlock.OffsetType.XZ)
                    .burnable()
                    .pistonBehavior(PistonBehavior.DESTROY),
            true);
    public static final Block SAGEBRUSH = registerBlock("sagebrush", settings -> new DesertHerbBlock(StatusEffects.STRENGTH, 5f, settings), AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.CHERRY_SAPLING)
                    .offset(AbstractBlock.OffsetType.XZ)
                    .burnable()
                    .pistonBehavior(PistonBehavior.DESTROY),
            true);

    public static final Block CUREFLOWER = registerBlock("cureflower", settings -> new NetherFlowerBlock(StatusEffects.REGENERATION, 7f, settings), AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_RED)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.NETHER_SPROUTS)
                    .offset(AbstractBlock.OffsetType.XZ)
                    .pistonBehavior(PistonBehavior.DESTROY),
            false);
    public static final Block RENDFLOWER = registerBlock("rendflower", settings -> new NetherFlowerBlock(ModStatusEffects.RENDING, 9f, settings), AbstractBlock.Settings.create()
                    .mapColor(MapColor.CYAN)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.NETHER_SPROUTS)
                    .offset(AbstractBlock.OffsetType.XZ)
                    .pistonBehavior(PistonBehavior.DESTROY),
            false);
    public static final Block SILENT_FLOWER = registerBlock("silent_flower", settings -> new FlowerBlock(ModStatusEffects.STEALTH, 7f, settings), AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.GRASS)
                    .offset(AbstractBlock.OffsetType.XZ)
                    .pistonBehavior(PistonBehavior.DESTROY)
                    .luminance(state -> 3),
            true);

    public static final Block POTTED_CUREFLOWER = registerBlock("potted_cureflower", settings -> new FlowerPotBlock(CUREFLOWER, settings), Blocks.createFlowerPotSettings(), false);
    public static final Block POTTED_RENDFLOWER = registerBlock("potted_rendflower", settings -> new FlowerPotBlock(RENDFLOWER, settings), Blocks.createFlowerPotSettings(), false);
    public static final Block POTTED_SILENT_FLOWER = registerBlock("potted_silent_flower", settings -> new FlowerPotBlock(SILENT_FLOWER, settings), Blocks.createFlowerPotSettings(), false);

    public static final Block HORTENSIA_CROP = registerBlock("hortensia_crop", HortensiaCropBlock::new, AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .noCollision()
                    .ticksRandomly()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.CROP)
                    .pistonBehavior(PistonBehavior.DESTROY),
            false);
    public static final Block PURPLE_HORTENSIA = registerBlock("purple_hortensia", TallPlantBlock::new, AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.CROP)
                    .offset(AbstractBlock.OffsetType.XZ)
                    .burnable()
                    .pistonBehavior(PistonBehavior.DESTROY),
            true);
    public static final Block PINK_HORTENSIA = registerBlock("pink_hortensia", TallPlantBlock::new, AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.CROP)
                    .offset(AbstractBlock.OffsetType.XZ)
                    .burnable()
                    .pistonBehavior(PistonBehavior.DESTROY),
            true);
    public static final Block BLUE_HORTENSIA = registerBlock("blue_hortensia", TallPlantBlock::new, AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.CROP)
                    .offset(AbstractBlock.OffsetType.XZ)
                    .burnable()
                    .pistonBehavior(PistonBehavior.DESTROY),
            true);

    public static final Block KETTLE = registerBlock("kettle", KettleBlock::new, AbstractBlock.Settings.create()
                    .strength(4f)
                    .sounds(BlockSoundGroup.LANTERN)
                    .luminance(createLightLevelFromLitBlockState(7))
                    .nonOpaque(),
            true);

    public static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
        RegistryKey<Block> blockRegistryKey = keyOfBlock(name);
        Block block = factory.apply(settings.registryKey(blockRegistryKey));
        if (shouldRegisterItem) {
            registerBlockItem(name, block);
        }
        return Registry.register(Registries.BLOCK, blockRegistryKey, block);
    }

    public static void registerBlockItem(String name, Block block) {
        RegistryKey<Item> itemRegistryKey = keyOfItem(name);
        Registry.register(Registries.ITEM, itemRegistryKey, new BlockItem(block, new Item.Settings().registryKey(itemRegistryKey).useBlockPrefixedTranslationKey()));
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(MintyBlends.MOD_ID, name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MintyBlends.MOD_ID, name));
    }

    public static void registerBlocks() {
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MINT, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.CATNIP, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MEDICINAL_HERB, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.CULINARY_HERB, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.SAGEBRUSH, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.SILENT_FLOWER, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.PURPLE_HORTENSIA, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.PINK_HORTENSIA, 100, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BLUE_HORTENSIA, 100, 60);
    }
}
