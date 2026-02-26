package io.github.mintynoura.mintyblends.compat.farmersdelight;

import io.github.mintynoura.mintyblends.registry.ModItems;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.item.ConsumableItem;

public class FarmersDelightCompat {

    public static final Item FRIED_GREENS = ModItems.registerItem("fried_greens", settings -> new ConsumableItem(settings, true, false), new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.8f).build(), FoodValues.ConsumableValues.NOURISHMENT_SHORT_DURATION)
            .stacksTo(16)
            .usingConvertsTo(Items.BOWL));
    public static final Item ONION_SOUP = ModItems.registerItem("onion_soup", settings -> new ConsumableItem(settings, true, false), new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.6f).build(), FoodValues.ConsumableValues.COMFORT_MEDIUM_DURATION)
            .stacksTo(16)
            .usingConvertsTo(Items.BOWL));
    public static final Item TOMATO_SOUP = ModItems.registerItem("tomato_soup", settings -> new ConsumableItem(settings, true, false), new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(12).saturationModifier(0.6f).build(), FoodValues.ConsumableValues.COMFORT_MEDIUM_DURATION)
            .stacksTo(16)
            .usingConvertsTo(Items.BOWL));
    public static final Item VEGETABLE_PORRIDGE = ModItems.registerItem("vegetable_porridge", settings -> new ConsumableItem(settings, true, false), new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(12).saturationModifier(0.8f).build(), FoodValues.ConsumableValues.NOURISHMENT_MEDIUM_DURATION)
            .stacksTo(16)
            .usingConvertsTo(Items.BOWL));

    public static void registerItems() {
        ItemGroupEvents.modifyEntriesEvent(ModItems.MINTYBLENDS_ITEM_GROUP_KEY).register(entries -> {
            entries.accept(FRIED_GREENS);
            entries.accept(TOMATO_SOUP);
            entries.accept(ONION_SOUP);
            entries.accept(VEGETABLE_PORRIDGE);
        });
        addFoodEffects();
    }

    public static void addFoodEffects() {
        DefaultItemComponentEvents.MODIFY.register( modifyContext ->  {
            modifyContext.modify(ModItems.BEETROOT_SALAD, builder -> builder.set(DataComponents.CONSUMABLE, FoodValues.ConsumableValues.FRUIT_SALAD));
            modifyContext.modify(ModItems.PUMPKIN_STEW, builder -> builder.set(DataComponents.CONSUMABLE, FoodValues.ConsumableValues.COMFORT_SHORT_DURATION));
        });
    }
}
