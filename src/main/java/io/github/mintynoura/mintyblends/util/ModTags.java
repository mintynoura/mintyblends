package io.github.mintynoura.mintyblends.util;

import io.github.mintynoura.mintyblends.MintyBlends;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class ModTags {
    public static class Items {

        public static final TagKey<Item> HERBS = createTag("herbs");
        public static final TagKey<Item> HERBAL_LEAVES = createTag("herbal_leaves");
        public static final TagKey<Item> BLENDING_INGREDIENTS = createTag("blending_ingredients");
        public static final TagKey<Item> CENSERS = createTag("censers");
        public static final TagKey<Item> CAT_LOVED = createTag("cat_loved");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(MintyBlends.MOD_ID, name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> HERBS = createTag("herbs");
        public static final TagKey<Block> CUREFLOWER_GROWN_ON = createTag("cureflower_grown_on");
        public static final TagKey<Block> RENDFLOWER_GROWN_ON = createTag("rendflower_grown_on");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(MintyBlends.MOD_ID, name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> PRODUCES_PURPLE_HORTENSIAS = createTag("produces_purple_hortensias");
        public static final TagKey<Biome> PRODUCES_PINK_HORTENSIAS = createTag("produces_pink_hortensias");
        public static final TagKey<Biome> PRODUCES_BLUE_HORTENSIAS = createTag("produces_blue_hortensias");

        public static final TagKey<Biome> HAS_MINT = createTag("has_mint");
        public static final TagKey<Biome> HAS_CATNIP = createTag("has_catnip");
        public static final TagKey<Biome> HAS_MEDICINAL_HERB = createTag("has_medicinal_herb");
        public static final TagKey<Biome> HAS_CULINARY_HERB = createTag("has_culinary_herb");
        public static final TagKey<Biome> HAS_SAGEBRUSH = createTag("has_sagebrush");

        private static TagKey<Biome> createTag(String name) {
            return TagKey.of(RegistryKeys.BIOME, Identifier.of(MintyBlends.MOD_ID, name));
        }
    }

    public static class EntityTypes {
        public static final TagKey<EntityType<?>> IGNORES_CENSER = createTag("ignores_censer");

        private static TagKey<EntityType<?>> createTag(String name) {
            return TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MintyBlends.MOD_ID, name));
        }
    }
}
