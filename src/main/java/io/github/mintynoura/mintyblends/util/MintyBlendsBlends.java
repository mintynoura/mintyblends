package io.github.mintynoura.mintyblends.util;

import io.github.mintynoura.mintyblends.item.component.HerbalBrewComponent;
import io.github.mintynoura.mintyblends.item.component.consume_effects.ClearEffectsByCategoryConsumeEffect;
import io.github.mintynoura.mintyblends.item.component.consume_effects.ConvertEffectsConsumeEffect;
import io.github.mintynoura.mintyblends.registry.MintyBlendsComponents;
import io.github.mintynoura.mintyblends.registry.MintyBlendsItems;
import io.github.mintynoura.mintyblends.registry.MintyBlendsStatusEffects;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ClearAllStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;

import java.util.List;

public class MintyBlendsBlends {
    public static final Component CLEAR_ALL_EFFECTS_TEA_NAME = Component.translatableWithFallback("item.mintyblends.clear_all_effects_tea", "Clearing Tea");
    public static final Component CLEAR_NEGATIVE_TEA_NAME = Component.translatableWithFallback("item.mintyblends.clear_negative_tea", "Curing Tea");
    public static final Component CLEAR_POSITIVE_TEA_NAME = Component.translatableWithFallback("item.mintyblends.clear_positive_tea", "Fatiguing Tea");
    public static final Component CONVERT_NEGATIVE_TO_POSITIVE_TEA_NAME = Component.translatableWithFallback("item.mintyblends.convert_negative_to_positive_tea", "Bane Reversal Tea");
    public static final Component CONVERT_POSITIVE_TO_NEGATIVE_TEA_NAME = Component.translatableWithFallback("item.mintyblends.convert_positive_to_negative_tea", "Boon Inversion Tea");
    public static final Component REACHING_TEA_NAME = Component.translatableWithFallback("item.mintyblends.reaching_tea", "Reaching Tea");
    public static final Component STRONG_REACHING_TEA_NAME = Component.translatableWithFallback("item.mintyblends.strong_reaching_tea", "Strong Reaching Tea");
    public static final Component RENDING_TEA_NAME = Component.translatableWithFallback("item.mintyblends.rending_tea", "Rending Tea");
    public static final Component STRONG_RENDING_TEA_NAME = Component.translatableWithFallback("item.mintyblends.strong_rending_tea", "Strong Rending Tea");
    public static final Component STEALTH_TEA_NAME = Component.translatableWithFallback("item.mintyblends.stealth_tea", "Stealth Tea");
    public static final Component STRONG_STEALTH_TEA_NAME = Component.translatableWithFallback("item.mintyblends.strong_stealth_tea", "Strong Stealth Tea");

    public static final ItemStackTemplate CLEAR_ALL_EFFECTS_TEA = singleEffectBrew(CLEAR_ALL_EFFECTS_TEA_NAME,"blend.mintyblends.clear_all_effects", new ClearAllStatusEffectsConsumeEffect());
    public static final ItemStackTemplate CLEAR_NEGATIVE_TEA = singleEffectBrew(CLEAR_NEGATIVE_TEA_NAME,"blend.mintyblends.clear_negative", new ClearEffectsByCategoryConsumeEffect(ClearEffectsByCategoryConsumeEffect.Category.HARMFUL));
    public static final ItemStackTemplate CLEAR_POSITIVE_TEA = singleEffectBrew(CLEAR_POSITIVE_TEA_NAME,"blend.mintyblends.clear_positive", new ClearEffectsByCategoryConsumeEffect(ClearEffectsByCategoryConsumeEffect.Category.BENEFICIAL));
    public static final ItemStackTemplate CONVERT_NEGATIVE_TO_POSITIVE_TEA = singleEffectBrew(CONVERT_NEGATIVE_TO_POSITIVE_TEA_NAME,"blend.mintyblends.convert_negative_to_positive", new ConvertEffectsConsumeEffect(false, true));
    public static final ItemStackTemplate CONVERT_POSITIVE_TO_NEGATIVE_TEA = singleEffectBrew(CONVERT_POSITIVE_TO_NEGATIVE_TEA_NAME,"blend.mintyblends.convert_positive_to_negative", new ConvertEffectsConsumeEffect(true, false));
    public static final ItemStackTemplate REACHING_TEA = singlePotionBrew(REACHING_TEA_NAME,"blend.mintyblends.reaching", new MobEffectInstance(MintyBlendsStatusEffects.REACHING, 3600));
    public static final ItemStackTemplate STRONG_REACHING_TEA = singlePotionBrew(STRONG_REACHING_TEA_NAME,"blend.mintyblends.strong_reaching", new MobEffectInstance(MintyBlendsStatusEffects.REACHING, 1800, 1));
    public static final ItemStackTemplate RENDING_TEA = singlePotionBrew(RENDING_TEA_NAME,"blend.mintyblends.rending", new MobEffectInstance(MintyBlendsStatusEffects.RENDING, 3600));
    public static final ItemStackTemplate STRONG_RENDING_TEA = singlePotionBrew(STRONG_RENDING_TEA_NAME,"blend.mintyblends.strong_rending", new MobEffectInstance(MintyBlendsStatusEffects.RENDING, 1800, 1));
    public static final ItemStackTemplate STEALTH_TEA = singlePotionBrew(STEALTH_TEA_NAME,"blend.mintyblends.stealth", new MobEffectInstance(MintyBlendsStatusEffects.STEALTH, 6000));
    public static final ItemStackTemplate STRONG_STEALTH_TEA = singlePotionBrew(STRONG_STEALTH_TEA_NAME,"blend.mintyblends.strong_stealth", new MobEffectInstance(MintyBlendsStatusEffects.STEALTH, 3600, 1));


    public static ItemStackTemplate singleEffectBrew(Component name, String blend, ConsumeEffect consumeEffect) {
        return new ItemStackTemplate(MintyBlendsItems.HERBAL_BREW, DataComponentPatch.builder()
                .set(DataComponents.ITEM_NAME, name)
                .set(MintyBlendsComponents.HERBAL_BREW, new HerbalBrewComponent(List.of(), List.of(blend)))
                .set(DataComponents.FOOD, new FoodProperties.Builder().nutrition(0).saturationModifier(0f).alwaysEdible().build())
                .set(DataComponents.CONSUMABLE, Consumables.defaultDrink().onConsume(consumeEffect).build())
                .build());
    }

    public static ItemStackTemplate singlePotionBrew(Component name, String blend, MobEffectInstance effect) {
        return new ItemStackTemplate(MintyBlendsItems.HERBAL_BREW, DataComponentPatch.builder()
                .set(DataComponents.ITEM_NAME, name)
                .set(MintyBlendsComponents.HERBAL_BREW, new HerbalBrewComponent(List.of(effect), List.of(blend)))
                .set(DataComponents.FOOD, new FoodProperties.Builder().nutrition(0).saturationModifier(0f).alwaysEdible().build())
                .set(DataComponents.CONSUMABLE, Consumables.defaultDrink().build())
                .build());
    }

    public static List<ItemStackTemplate> blends = List.of(
            CLEAR_ALL_EFFECTS_TEA,
            CLEAR_NEGATIVE_TEA,
            CLEAR_POSITIVE_TEA,
            CONVERT_NEGATIVE_TO_POSITIVE_TEA,
            CONVERT_POSITIVE_TO_NEGATIVE_TEA,
            REACHING_TEA,
            STRONG_REACHING_TEA,
            RENDING_TEA,
            STRONG_RENDING_TEA,
            STEALTH_TEA,
            STRONG_STEALTH_TEA
    );

    public static void initialize() {}
}
