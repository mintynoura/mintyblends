package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.item.CenserItem;
import io.github.mintynoura.mintyblends.item.HerbalBrewItem;
import io.github.mintynoura.mintyblends.item.component.CenserComponent;
import io.github.mintynoura.mintyblends.item.component.HerbComponent;
import io.github.mintynoura.mintyblends.item.component.HerbalBrewComponent;
import io.github.mintynoura.mintyblends.util.HerbalEffectType;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Function;

public class ModItems {
    public static final Item MINT_LEAVES = registerItem("mint_leaves", Item::new, new Item.Settings().food(new FoodComponent(0, 0, true)).component(ModComponents.HERB_COMPONENT, new HerbComponent(HerbalEffectType.EXTINGUISH)));
    public static final Item CATNIP_LEAVES = registerItem("catnip_leaves", Item::new, new Item.Settings());
    public static final Item MEDICINAL_LEAVES = registerItem("medicinal_leaves", Item::new, new Item.Settings().food(new FoodComponent(0, 0, true)).component(ModComponents.HERB_COMPONENT, new HerbComponent(HerbalEffectType.HEAL)));
    public static final Item CULINARY_LEAVES = registerItem("culinary_leaves", Item::new, new Item.Settings().food(new FoodComponent(2, 0.2f, false)));
    public static final Item SAGEBRUSH_LEAVES = registerItem("sagebrush_leaves", Item::new, new Item.Settings().food(new FoodComponent(0, 0, true)).component(ModComponents.HERB_COMPONENT, new HerbComponent(HerbalEffectType.CURE_WEAKNESS_AND_RENDING)));

    public static final Item HORTENSIA_SEEDS = registerItem("hortensia_seeds", settings -> new BlockItem(ModBlocks.HORTENSIA_CROP, settings), new Item.Settings().useItemPrefixedTranslationKey());

    public static final Item HERBAL_BREW = registerItem("herbal_brew", HerbalBrewItem::new, new Item.Settings()
            .component(ModComponents.HERBAL_BREW_COMPONENT, new HerbalBrewComponent(List.of(), List.of()))
            .component(DataComponentTypes.CONSUMABLE, ConsumableComponents.DRINK)
            .useRemainder(Items.GLASS_BOTTLE)
    );

    public static final Item COPPER_CENSER = registerItem("copper_censer", CenserItem::new, new Item.Settings()
            .component(ModComponents.CENSER_COMPONENT, new CenserComponent(5f, List.of(), List.of()))
            .maxDamage(3));
    public static final Item GOLDEN_CENSER = registerItem("golden_censer", CenserItem::new, new Item.Settings()
            .component(ModComponents.CENSER_COMPONENT, new CenserComponent(7f, List.of(), List.of()))
            .maxDamage(2));
    public static final Item IRON_CENSER = registerItem("iron_censer", CenserItem::new, new Item.Settings()
            .component(ModComponents.CENSER_COMPONENT, new CenserComponent(3f, List.of(), List.of()))
            .maxDamage(4));

    public static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        RegistryKey<Item> itemRegistryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MintyBlends.MOD_ID, name));
        Item item = factory.apply(settings.registryKey(itemRegistryKey));
        Registry.register(Registries.ITEM, itemRegistryKey, item);
        return item;
    }

    public static final ItemGroup MINTYBLENDS_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(MintyBlends.MOD_ID, "item_group"), FabricItemGroup.builder()
            .icon(() -> new ItemStack(MINT_LEAVES))
            .displayName(Text.translatableWithFallback("itemGroup.mintyblends", "Minty Blends"))
            .entries((displayContext, entries) -> {
                entries.add(ModBlocks.MINT);
                entries.add(MINT_LEAVES);
                entries.add(ModBlocks.CATNIP);
                entries.add(CATNIP_LEAVES);
                entries.add(ModBlocks.MEDICINAL_HERB);
                entries.add(MEDICINAL_LEAVES);
                entries.add(ModBlocks.CULINARY_HERB);
                entries.add(CULINARY_LEAVES);
                entries.add(ModBlocks.SAGEBRUSH);
                entries.add(SAGEBRUSH_LEAVES);
                entries.add(ModBlocks.SILENT_FLOWER);
                entries.add(ModBlocks.CUREFLOWER);
                entries.add(ModBlocks.RENDFLOWER);
                entries.add(HORTENSIA_SEEDS);
                entries.add(ModBlocks.PURPLE_HORTENSIA);
                entries.add(ModBlocks.PINK_HORTENSIA);
                entries.add(ModBlocks.BLUE_HORTENSIA);
                entries.add(ModBlocks.KETTLE);
                entries.add(HERBAL_BREW);
                entries.add(COPPER_CENSER);
                entries.add(IRON_CENSER);
                entries.add(GOLDEN_CENSER);
            })
            .build()
    );

    public static void registerItems() {}
}
