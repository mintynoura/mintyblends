package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.item.CenserItem;
import io.github.mintynoura.mintyblends.item.component.CenserComponent;
import io.github.mintynoura.mintyblends.item.component.HerbalBrewComponent;
import io.github.mintynoura.mintyblends.util.MintyBlendsConsumables;
import io.github.mintynoura.mintyblends.util.MintyBlendsFoods;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumables;

import java.util.List;
import java.util.function.Function;

public class MintyBlendsItems {
    public static final Item MINT_LEAVES = registerItem("mint_leaves", Item::new, new Item.Properties()
            .food(MintyBlendsFoods.MINT, MintyBlendsConsumables.MINT));
    public static final Item CATNIP_LEAVES = registerItem("catnip_leaves", Item::new, new Item.Properties()
            .food(MintyBlendsFoods.CATNIP, MintyBlendsConsumables.CATNIP));
    public static final Item MEDICINAL_LEAVES = registerItem("medicinal_leaves", Item::new, new Item.Properties()
            .food(MintyBlendsFoods.MEDICINAL_HERB, MintyBlendsConsumables.MEDICINAL_HERB));
    public static final Item CULINARY_LEAVES = registerItem("culinary_leaves", Item::new, new Item.Properties()
            .food(MintyBlendsFoods.CULINARY_HERB, MintyBlendsConsumables.CULINARY_HERB));
    public static final Item SAGEBRUSH_LEAVES = registerItem("sagebrush_leaves", Item::new, new Item.Properties()
            .food(MintyBlendsFoods.SAGEBRUSH, MintyBlendsConsumables.SAGEBRUSH));

    public static final Item CUREFLOWER = registerItem("cureflower", settings -> new BlockItem(MintyBlendsBlocks.CUREFLOWER, settings), new Item.Properties()
            .useBlockDescriptionPrefix()
            .food(MintyBlendsFoods.CUREFLOWER, MintyBlendsConsumables.CUREFLOWER));
    public static final Item RENDFLOWER = registerItem("rendflower", settings -> new BlockItem(MintyBlendsBlocks.RENDFLOWER, settings), new Item.Properties()
            .useBlockDescriptionPrefix()
            .food(MintyBlendsFoods.RENDFLOWER, MintyBlendsConsumables.RENDFLOWER));
    public static final Item HORTENSIA_SEEDS = registerItem("hortensia_seeds", settings -> new BlockItem(MintyBlendsBlocks.HORTENSIA_CROP, settings), new Item.Properties().useItemDescriptionPrefix());

    public static final Item HERBAL_BREW = registerItem("herbal_brew", Item::new, new Item.Properties()
            .component(MintyBlendsComponents.HERBAL_BREW, new HerbalBrewComponent(List.of(), List.of()))
            .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK)
            .usingConvertsTo(Items.GLASS_BOTTLE));


    public static final Item MINT_TEA = registerItem("mint_tea", Item::new, new Item.Properties()
            .food(MintyBlendsFoods.MINT_TEA, MintyBlendsConsumables.MINT_TEA)
            .usingConvertsTo(Items.GLASS_BOTTLE));
    public static final Item GLOW_BERRY_TEA = registerItem("glow_berry_tea", Item::new, new Item.Properties()
            .food(MintyBlendsFoods.GLOW_BERRY_TEA, MintyBlendsConsumables.GLOW_BERRY_TEA)
            .usingConvertsTo(Items.GLASS_BOTTLE));
    public static final Item SWEET_BERRY_TEA = registerItem("sweet_berry_tea", Item::new, new Item.Properties()
            .food(MintyBlendsFoods.SWEET_BERRY_TEA, Consumables.DEFAULT_DRINK)
            .usingConvertsTo(Items.GLASS_BOTTLE));
    public static final Item WILDFLOWER_TEA = registerItem("wildflower_tea", Item::new, new Item.Properties()
            .food(MintyBlendsFoods.WILDFLOWER_TEA, MintyBlendsConsumables.WILDFLOWER_TEA)
            .usingConvertsTo(Items.GLASS_BOTTLE));
    public static final Item TORCHFLOWER_TEA = registerItem("torchflower_tea", Item::new, new Item.Properties()
            .food(MintyBlendsFoods.TORCHFLOWER_TEA, MintyBlendsConsumables.TORCHFLOWER_TEA)
            .usingConvertsTo(Items.GLASS_BOTTLE));

    public static final Item MINT_JELLY = registerItem("mint_jelly", Item::new, new Item.Properties()
            .food(MintyBlendsFoods.MINT_JELLY, MintyBlendsConsumables.MINT_JELLY)
            .stacksTo(16)
            .usingConvertsTo(Items.BOWL));
    public static final Item BEETROOT_SALAD = registerItem("beetroot_salad", Item::new, new Item.Properties()
            .food(MintyBlendsFoods.BEETROOT_SALAD)
            .stacksTo(16)
            .usingConvertsTo(Items.BOWL));
    public static final Item PUMPKIN_STEW = registerItem("pumpkin_stew", Item::new, new Item.Properties()
            .food(MintyBlendsFoods.PUMPKIN_STEW)
            .stacksTo(16)
            .usingConvertsTo(Items.BOWL));
    public static final Item STEAK_TARTARE = registerItem("steak_tartare", Item::new, new Item.Properties()
            .food(MintyBlendsFoods.STEAK_TARTARE, MintyBlendsConsumables.STEAK_TARTARE));



    public static final Item COPPER_CENSER = registerItem("copper_censer", CenserItem::new, new Item.Properties()
            .component(MintyBlendsComponents.CENSER, new CenserComponent(5f, List.of(), List.of(), List.of()))
            .durability(3));
    public static final Item GOLDEN_CENSER = registerItem("golden_censer", CenserItem::new, new Item.Properties()
            .component(MintyBlendsComponents.CENSER, new CenserComponent(7f, List.of(), List.of(), List.of()))
            .durability(2));
    public static final Item IRON_CENSER = registerItem("iron_censer", CenserItem::new, new Item.Properties()
            .component(MintyBlendsComponents.CENSER, new CenserComponent(3f, List.of(), List.of(), List.of()))
            .durability(4));

    public static Item registerItem(String name, Function<Item.Properties, Item> factory, Item.Properties settings) {
        ResourceKey<Item> itemRegistryKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MintyBlends.ID, name));
        Item item = factory.apply(settings.setId(itemRegistryKey));
        Registry.register(BuiltInRegistries.ITEM, itemRegistryKey, item);
        return item;
    }

    public static final ResourceKey<CreativeModeTab> MINTYBLENDS_ITEM_GROUP_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(MintyBlends.ID, "item_group"));

    public static void initialize() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MINTYBLENDS_ITEM_GROUP_KEY, FabricCreativeModeTab.builder()
                .icon(() -> new ItemStack(MINT_LEAVES))
                .title(Component.translatableWithFallback("itemGroup.mintyblends", "Minty Blends"))
                .displayItems((_, output) -> {
                    output.accept(MintyBlendsBlocks.MINT);
                    output.accept(MINT_LEAVES);
                    output.accept(MintyBlendsBlocks.CATNIP);
                    output.accept(CATNIP_LEAVES);
                    output.accept(MintyBlendsBlocks.MEDICINAL_HERB);
                    output.accept(MEDICINAL_LEAVES);
                    output.accept(MintyBlendsBlocks.CULINARY_HERB);
                    output.accept(CULINARY_LEAVES);
                    output.accept(MintyBlendsBlocks.SAGEBRUSH);
                    output.accept(SAGEBRUSH_LEAVES);
                    output.accept(MintyBlendsBlocks.SILENT_FLOWER);
                    output.accept(CUREFLOWER);
                    output.accept(RENDFLOWER);
                    output.accept(HORTENSIA_SEEDS);
                    output.accept(MintyBlendsBlocks.PURPLE_HORTENSIA);
                    output.accept(MintyBlendsBlocks.PINK_HORTENSIA);
                    output.accept(MintyBlendsBlocks.BLUE_HORTENSIA);
                    output.accept(MintyBlendsBlocks.KETTLE);
                    output.accept(COPPER_CENSER);
                    output.accept(IRON_CENSER);
                    output.accept(GOLDEN_CENSER);
                    output.accept(HERBAL_BREW);
                    output.accept(MINT_TEA);
                    output.accept(GLOW_BERRY_TEA);
                    output.accept(SWEET_BERRY_TEA);
                    output.accept(WILDFLOWER_TEA);
                    output.accept(TORCHFLOWER_TEA);
                    output.accept(MINT_JELLY);
                    output.accept(BEETROOT_SALAD);
                    output.accept(STEAK_TARTARE);
                    output.accept(PUMPKIN_STEW);
                })
                .build()
        );

        DefaultItemComponentEvents.MODIFY.register(context -> {
            context.modify(Items.SUGAR, builder -> builder.set(MintyBlendsComponents.BLEND_EFFECT_DURATION_MODIFIER, MintyBlends.CONFIG.sugarDurationModifier.value()));
            if (MintyBlends.CONFIG.modifyPotionStackSize.value()) {
                context.modify(Items.POTION, builder -> builder.set(DataComponents.MAX_STACK_SIZE, MintyBlends.CONFIG.potionStackSize.value()));
            }
        });
    }
}
