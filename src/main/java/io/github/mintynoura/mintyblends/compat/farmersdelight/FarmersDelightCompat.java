//package io.github.mintynoura.mintyblends.compat.farmersdelight;
//
//import io.github.mintynoura.mintyblends.registry.ModItems;
//import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
//import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
//import net.minecraft.component.DataComponentTypes;
//import net.minecraft.component.type.FoodComponent;
//import net.minecraft.item.Item;
//import net.minecraft.item.Items;
//import vectorwing.farmersdelight.common.FoodValues;
//import vectorwing.farmersdelight.common.item.ConsumableItem;
//
//public class FarmersDelightCompat {
//
//    public static final Item FRIED_GREENS = ModItems.registerItem("fried_greens", settings -> new ConsumableItem(settings, true, false), new Item.Settings()
//            .food(new FoodComponent.Builder().nutrition(8).saturationModifier(0.8f).build(), FoodValues.ConsumableValues.NOURISHMENT_SHORT_DURATION)
//            .maxCount(16)
//            .useRemainder(Items.BOWL));
//    public static final Item ONION_SOUP = ModItems.registerItem("onion_soup", settings -> new ConsumableItem(settings, true, false), new Item.Settings()
//            .food(new FoodComponent.Builder().nutrition(10).saturationModifier(0.6f).build(), FoodValues.ConsumableValues.COMFORT_MEDIUM_DURATION)
//            .maxCount(16)
//            .useRemainder(Items.BOWL));
//    public static final Item TOMATO_SOUP = ModItems.registerItem("tomato_soup", settings -> new ConsumableItem(settings, true, false), new Item.Settings()
//            .food(new FoodComponent.Builder().nutrition(12).saturationModifier(0.6f).build(), FoodValues.ConsumableValues.COMFORT_MEDIUM_DURATION)
//            .maxCount(16)
//            .useRemainder(Items.BOWL));
//    public static final Item VEGETABLE_PORRIDGE = ModItems.registerItem("vegetable_porridge", settings -> new ConsumableItem(settings, true, false), new Item.Settings()
//            .food(new FoodComponent.Builder().nutrition(12).saturationModifier(0.8f).build(), FoodValues.ConsumableValues.NOURISHMENT_MEDIUM_DURATION)
//            .maxCount(16)
//            .useRemainder(Items.BOWL));
//
//    public static void registerItems() {
//        ItemGroupEvents.modifyEntriesEvent(ModItems.MINTYBLENDS_ITEM_GROUP_KEY).register(entries -> {
//            entries.add(FRIED_GREENS);
//            entries.add(TOMATO_SOUP);
//            entries.add(ONION_SOUP);
//            entries.add(VEGETABLE_PORRIDGE);
//        });
//        addFoodEffects();
//    }
//
//    public static void addFoodEffects() {
//        DefaultItemComponentEvents.MODIFY.register( modifyContext ->  {
//            modifyContext.modify(ModItems.BEETROOT_SALAD, builder -> builder.add(DataComponentTypes.CONSUMABLE, FoodValues.ConsumableValues.FRUIT_SALAD));
//            modifyContext.modify(ModItems.PUMPKIN_STEW, builder -> builder.add(DataComponentTypes.CONSUMABLE, FoodValues.ConsumableValues.COMFORT_SHORT_DURATION));
//        });
//    }
//}
