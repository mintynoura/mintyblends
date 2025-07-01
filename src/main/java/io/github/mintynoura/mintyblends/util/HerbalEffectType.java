package io.github.mintynoura.mintyblends.util;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.registry.ModStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import static io.github.mintynoura.mintyblends.util.StatusEffectMap.statusEffectMap;

public class HerbalEffectType {

    public static final List<StatusEffectInstance> effectRemoval = new ArrayList<>();
    public static final List<StatusEffectInstance> effectAddition = new ArrayList<>();

    public static Identifier EXTINGUISH = createHerbId("extinguish");
    public static Identifier HEAL = createHerbId("heal");
    public static Identifier CURE_POISON_AND_WITHER = createHerbId("cure_poison_and_wither");
    public static Identifier CURE_WEAKNESS_AND_RENDING = createHerbId("cure_weakness_and_rending");
    public static Identifier CONVERT_NEGATIVE_TO_POSITIVE = createHerbId("convert_negative_to_positive");
    public static Identifier CONVERT_POSITIVE_TO_NEGATIVE = createHerbId("convert_positive_to_negative");
    public static Identifier CLEAR_EFFECTS = createHerbId("clear_effects");
    public static Identifier CLEAR_NEGATIVE = createHerbId("clear_negative");
    public static Identifier CLEAR_POSITIVE = createHerbId("clear_positive");

    public static void applyHerb(LivingEntity livingEntity, Identifier herbalEffect) {
        RegistryEntry<StatusEffect> effectType;
        if (herbalEffect.equals(EXTINGUISH)) livingEntity.extinguishWithSound();
        if (herbalEffect.equals(HEAL)) livingEntity.heal(2);
        if (herbalEffect.equals(CURE_POISON_AND_WITHER)) {
            livingEntity.removeStatusEffect(StatusEffects.POISON);
            livingEntity.removeStatusEffect(StatusEffects.WITHER);
        }
        if (herbalEffect.equals(CURE_WEAKNESS_AND_RENDING)) {
            livingEntity.removeStatusEffect(StatusEffects.WEAKNESS);
            livingEntity.removeStatusEffect(ModStatusEffects.RENDING);
        }
        if (herbalEffect.equals(CONVERT_POSITIVE_TO_NEGATIVE)) {
            for (StatusEffectInstance positiveEffect : livingEntity.getActiveStatusEffects().values()) {
                effectType = positiveEffect.getEffectType();
                if (!positiveEffect.isAmbient() && !livingEntity.hasStatusEffect(statusEffectMap.get(effectType)) && statusEffectMap.containsKey(effectType)) {
                    effectRemoval.add(positiveEffect);
                    effectAddition.add(new StatusEffectInstance(statusEffectMap.get(effectType), positiveEffect.getDuration(), positiveEffect.getAmplifier()));
                }
            }
            for (StatusEffectInstance removal : effectRemoval) {
                livingEntity.removeStatusEffect(removal.getEffectType());
            }
            for (StatusEffectInstance addition : effectAddition) {
                livingEntity.addStatusEffect(addition, livingEntity);
            }
            effectRemoval.clear();
            effectAddition.clear();
        }
        if (herbalEffect.equals(CONVERT_NEGATIVE_TO_POSITIVE)) {
            for (StatusEffectInstance negativeEffect : livingEntity.getActiveStatusEffects().values()) {
                effectType = negativeEffect.getEffectType();
                if (!negativeEffect.isAmbient() && !livingEntity.hasStatusEffect(StatusEffectMap.getKeyEffect(effectType)) && statusEffectMap.containsValue(effectType)) {
                    effectRemoval.add(negativeEffect);
                    effectAddition.add(new StatusEffectInstance(StatusEffectMap.getKeyEffect(effectType), negativeEffect.getDuration(), negativeEffect.getAmplifier()));
                }
            }
            for (StatusEffectInstance removal : effectRemoval) {
                livingEntity.removeStatusEffect(removal.getEffectType());
            }
            for (StatusEffectInstance addition : effectAddition) {
                livingEntity.addStatusEffect(addition, livingEntity);
            }
            effectRemoval.clear();
            effectAddition.clear();
        }
        if (herbalEffect.equals(CLEAR_EFFECTS)) {
            livingEntity.clearStatusEffects();
        }
        if (herbalEffect.equals(CLEAR_NEGATIVE)) {
            for (StatusEffectInstance negativeEffect : livingEntity.getActiveStatusEffects().values()) {
                if (negativeEffect.getEffectType().value().getCategory().equals(StatusEffectCategory.HARMFUL)) {
                    effectRemoval.add(negativeEffect);
                }
            }
            for (StatusEffectInstance removal : effectRemoval) {
                livingEntity.removeStatusEffect(removal.getEffectType());
            }
            effectRemoval.clear();
        }
        if (herbalEffect.equals(CLEAR_POSITIVE)) {
            for (StatusEffectInstance positiveEffect : livingEntity.getActiveStatusEffects().values()) {
                if (positiveEffect.getEffectType().value().getCategory().equals(StatusEffectCategory.BENEFICIAL)) {
                    effectRemoval.add(positiveEffect);
                }
            }
            for (StatusEffectInstance removal : effectRemoval) {
                livingEntity.removeStatusEffect(removal.getEffectType());
            }
            effectRemoval.clear();
        }
    }

    public static Identifier createHerbId(String name) {
        return Identifier.of(MintyBlends.MOD_ID, name);
    }
}
