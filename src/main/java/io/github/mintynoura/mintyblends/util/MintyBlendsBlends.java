package io.github.mintynoura.mintyblends.util;

import io.github.mintynoura.mintyblends.item.component.HerbalBrewComponent;
import io.github.mintynoura.mintyblends.item.component.consume_effects.ClearEffectsByCategoryConsumeEffect;
import io.github.mintynoura.mintyblends.item.component.consume_effects.ConvertEffectsConsumeEffect;
import io.github.mintynoura.mintyblends.registry.MintyBlendsComponents;
import io.github.mintynoura.mintyblends.registry.MintyBlendsItems;
import io.github.mintynoura.mintyblends.registry.MintyBlendsStatusEffects;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ClearAllStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;

import java.util.List;

public class MintyBlendsBlends {
    public static final ItemStack CONVERT_NEGATIVE_TO_POSITIVE_TEA = singleEffectBrew("blend.mintyblends.convert_negative_to_positive", new ConvertEffectsConsumeEffect(false, true));
    public static final ItemStack CONVERT_POSITIVE_TO_NEGATIVE_TEA = singleEffectBrew("blend.mintyblends.convert_positive_to_negative", new ConvertEffectsConsumeEffect(true, false));
    public static final ItemStack CLEAR_ALL_EFFECTS_TEA = singleEffectBrew("blend.mintyblends.clear_all_effects", new ClearAllStatusEffectsConsumeEffect());
    public static final ItemStack CLEAR_NEGATIVE_TEA = singleEffectBrew("blend.mintyblends.clear_negative", new ClearEffectsByCategoryConsumeEffect(ClearEffectsByCategoryConsumeEffect.Category.HARMFUL));
    public static final ItemStack CLEAR_POSITIVE_TEA = singleEffectBrew("blend.mintyblends.clear_positive", new ClearEffectsByCategoryConsumeEffect(ClearEffectsByCategoryConsumeEffect.Category.BENEFICIAL));
    public static final ItemStack REACHING_TEA = singlePotionBrew("blend.mintyblends.reaching", new MobEffectInstance(MintyBlendsStatusEffects.REACHING, 3600));
    public static final ItemStack STRONG_REACHING_TEA = singlePotionBrew("blend.mintyblends.strong_reaching", new MobEffectInstance(MintyBlendsStatusEffects.REACHING, 1800, 1));
    public static final ItemStack RENDING_TEA = singlePotionBrew("blend.mintyblends.rending", new MobEffectInstance(MintyBlendsStatusEffects.RENDING, 3600));
    public static final ItemStack STRONG_RENDING_TEA = singlePotionBrew("blend.mintyblends.strong_rending", new MobEffectInstance(MintyBlendsStatusEffects.RENDING, 1800, 1));
    public static final ItemStack STEALTH_TEA = singlePotionBrew("blend.mintyblends.stealth", new MobEffectInstance(MintyBlendsStatusEffects.STEALTH, 6000));
    public static final ItemStack STRONG_STEALTH_TEA = singlePotionBrew("blend.mintyblends.strong_stealth", new MobEffectInstance(MintyBlendsStatusEffects.STEALTH, 3600, 1));


    public static ItemStack singleEffectBrew(String blend, ConsumeEffect consumeEffect) {
        ItemStack itemStack = new ItemStack(MintyBlendsItems.HERBAL_BREW);
        itemStack.set(MintyBlendsComponents.HERBAL_BREW, new HerbalBrewComponent(List.of(), List.of(blend)));
        itemStack.set(DataComponents.FOOD, new FoodProperties.Builder().nutrition(0).saturationModifier(0f).alwaysEdible().build());
        itemStack.set(DataComponents.CONSUMABLE, Consumables.defaultDrink().onConsume(consumeEffect).build());
        return itemStack;
    }

    public static ItemStack singlePotionBrew(String blend, MobEffectInstance effect) {
        ItemStack itemStack = new ItemStack(MintyBlendsItems.HERBAL_BREW);
        itemStack.set(MintyBlendsComponents.HERBAL_BREW, new HerbalBrewComponent(List.of(effect), List.of(blend)));
        itemStack.set(DataComponents.FOOD, new FoodProperties.Builder().nutrition(0).saturationModifier(0f).alwaysEdible().build());
        itemStack.set(DataComponents.CONSUMABLE, Consumables.defaultDrink().build());
        return itemStack;
    }

    public static void initialize() {}
}
