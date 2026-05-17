package io.github.mintynoura.mintyblends.compat.farmersdelight;

import io.github.mintynoura.mintyblends.registry.MintyBlendsItems;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.item.ConsumableItem;

public class FarmersDelightCompat {

    public static final Item FRIED_GREENS = MintyBlendsItems.registerItem("fried_greens", settings -> new ConsumableItem(settings, true, false), new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.8f).build(), FoodValues.ConsumableValues.NOURISHMENT_SHORT_DURATION)
            .stacksTo(16)
            .usingConvertsTo(Items.BOWL));
    public static final Item TOMATO_SOUP = MintyBlendsItems.registerItem("tomato_soup", settings -> new ConsumableItem(settings, true, false), new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(12).saturationModifier(0.6f).build(), FoodValues.ConsumableValues.NOURISHMENT_MEDIUM_DURATION)
            .stacksTo(16)
            .usingConvertsTo(Items.BOWL));
    public static final Item VEGETABLE_PORRIDGE = MintyBlendsItems.registerItem("vegetable_porridge", settings -> new ConsumableItem(settings, true, false), new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(12).saturationModifier(0.8f).build(), FoodValues.ConsumableValues.NOURISHMENT_MEDIUM_DURATION)
            .stacksTo(16)
            .usingConvertsTo(Items.BOWL));

    public static void registerItems() {
        CreativeModeTabEvents.modifyOutputEvent(MintyBlendsItems.MINTYBLENDS_ITEM_GROUP_KEY).register(output -> {
            output.accept(FRIED_GREENS);
            output.accept(TOMATO_SOUP);
            output.accept(VEGETABLE_PORRIDGE);
        });
        addFoodEffects();
    }

    public static void addFoodEffects() {
        DefaultItemComponentEvents.MODIFY.register( modifyContext ->  {
            modifyContext.modify(MintyBlendsItems.BEETROOT_SALAD, builder -> builder.set(DataComponents.CONSUMABLE, FoodValues.ConsumableValues.FRUIT_SALAD));
            modifyContext.modify(MintyBlendsItems.PUMPKIN_STEW, builder -> builder.set(DataComponents.CONSUMABLE, FoodValues.ConsumableValues.NOURISHMENT_SHORT_DURATION));
        });
    }
}
