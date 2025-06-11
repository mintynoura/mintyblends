package io.github.mintynoura.mintyblends.util;

import io.github.mintynoura.mintyblends.MintyBlends;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> HERBAL_LEAVES = createTag("herbal_leaves");

        public static final TagKey<Item> CAT_LOVED = createTag("cat_loved");

        public static final TagKey<Item> HERBS = createTag("herbs");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(MintyBlends.MOD_ID, name));
        }
    }
    public static class Blocks {
        public static final TagKey<Block> HERBS = createTag("herbs");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(MintyBlends.MOD_ID, name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> PRODUCES_COLD_HORTENSIAS = createTag("produces_cold_hortensias");
        public static final TagKey<Biome> PRODUCES_TEMPERATE_HORTENSIAS = createTag("produces_temperate_hortensias");
        public static final TagKey<Biome> PRODUCES_WARM_HORTENSIAS = createTag("produces_warm_hortensias");

        private static TagKey<Biome> createTag(String name) {
            return TagKey.of(RegistryKeys.BIOME, Identifier.of(MintyBlends.MOD_ID, name));
        }
    }
}
