package io.github.mintynoura.mintyblends.util;

import io.github.mintynoura.mintyblends.MintyBlends;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class MintyBlendsTags {
    public static class Items {
        public static final TagKey<Item> HERBS = bind("herbs");
        public static final TagKey<Item> HERBAL_LEAVES = bind("herbal_leaves");
        public static final TagKey<Item> BLENDING_INGREDIENTS = bind("blending_ingredients");
        public static final TagKey<Item> FRYABLE_GREENS = bind("fryable_greens");
        public static final TagKey<Item> CENSERS = bind("censers");
        public static final TagKey<Item> CAT_LOVED = bind("cat_loved");
        public static final TagKey<Item> INFERNALILY_FERTILIZERS = bind("infernalily_fertilizers");

        private static TagKey<Item> bind(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MintyBlends.ID, name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> HERBS = bind("herbs");
        public static final TagKey<Block> CUREFLOWER_GROWN_ON = bind("cureflower_grown_on");
        public static final TagKey<Block> RENDFLOWER_GROWN_ON = bind("rendflower_grown_on");
        public static TagKey<Block> SUPPORTS_INFERNALILY = bind("supports_infernalily");

        private static TagKey<Block> bind(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MintyBlends.ID, name));
        }
    }

    public static class Fluids {
        public static TagKey<Fluid> SUPPORTS_INFERNALILY = TagKey.create(Registries.FLUID, Identifier.fromNamespaceAndPath(MintyBlends.ID, "supports_infernalily"));
    }

    public static class Biomes {
        public static final TagKey<Biome> PRODUCES_PURPLE_HORTENSIAS = bind("produces_purple_hortensias");
        public static final TagKey<Biome> PRODUCES_PINK_HORTENSIAS = bind("produces_pink_hortensias");
        public static final TagKey<Biome> PRODUCES_BLUE_HORTENSIAS = bind("produces_blue_hortensias");

        public static final TagKey<Biome> HAS_MINT = bind("has_mint");
        public static final TagKey<Biome> HAS_CATNIP = bind("has_catnip");
        public static final TagKey<Biome> HAS_MEDICINAL_HERB = bind("has_medicinal_herb");
        public static final TagKey<Biome> HAS_CULINARY_HERB = bind("has_culinary_herb");
        public static final TagKey<Biome> HAS_SAGEBRUSH = bind("has_sagebrush");

        private static TagKey<Biome> bind(String name) {
            return TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MintyBlends.ID, name));
        }
    }

    public static class EntityTypes {
        public static final TagKey<EntityType<?>> IGNORES_CENSER = bind("ignores_censer");

        private static TagKey<EntityType<?>> bind(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MintyBlends.ID, name));
        }
    }
}
