package io.github.mintynoura.mintyblends.util;

import io.github.mintynoura.mintyblends.item.component.HerbalBrewComponent;
import io.github.mintynoura.mintyblends.item.component.consume_effects.ConvertEffectsConsumeEffect;
import io.github.mintynoura.mintyblends.registry.MintyBlendsComponents;
import io.github.mintynoura.mintyblends.registry.MintyBlendsItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ConsumeEffect;

import java.util.List;

public class MintyBlendsBlends {
    public static final ItemStack REVERSAL_TEA = singleEffectBrew(new HerbalBrewComponent(List.of(), List.of("blend.mintyblends.convert_negative_to_positive")), new ConvertEffectsConsumeEffect(false, true));

    public static ItemStack singleEffectBrew(HerbalBrewComponent brew, ConsumeEffect consumeEffect) {
        ItemStack itemStack = new ItemStack(MintyBlendsItems.HERBAL_BREW);
        itemStack.set(MintyBlendsComponents.HERBAL_BREW, brew);
        itemStack.set(DataComponents.FOOD, new FoodProperties.Builder().nutrition(0).saturationModifier(0f).alwaysEdible().build());
        itemStack.set(DataComponents.CONSUMABLE, Consumables.defaultDrink().onConsume(consumeEffect).build());
        return itemStack;
    }

    public static void initialize() {}
}
