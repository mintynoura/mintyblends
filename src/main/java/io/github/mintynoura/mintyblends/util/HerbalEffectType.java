package io.github.mintynoura.mintyblends.util;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.MintyBlendsConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HerbalEffectType {

    public static final List<StatusEffectInstance> effectRemoval = new ArrayList<>();
    public static final List<StatusEffectInstance> effectAddition = new ArrayList<>();

    public static Identifier EXTINGUISH = createEffectId("extinguish");
    public static Identifier HEAL = createEffectId("heal");
    public static Identifier FEED = createEffectId("feed");
    public static Identifier CONVERT_NEGATIVE_TO_POSITIVE = createEffectId("convert_negative_to_positive");
    public static Identifier CONVERT_POSITIVE_TO_NEGATIVE = createEffectId("convert_positive_to_negative");
    public static Identifier CLEAR_NEGATIVE = createEffectId("clear_negative");
    public static Identifier CLEAR_POSITIVE = createEffectId("clear_positive");
    public static Identifier LOWER_SNIFFER_COOLDOWN = createEffectId("lower_sniffer_cooldown");

    public static void applyHerb(LivingEntity livingEntity, Identifier herbalEffect) {
        RegistryEntry<StatusEffect> effectType;
        Map<RegistryEntry<StatusEffect>, RegistryEntry<StatusEffect>> configMap = new HashMap<>();
        MintyBlendsConfig config = MintyBlends.CONFIG;
        for (Map.Entry<String, String> pair : config.statusEffectSection.statusEffectMap.value()) {
            RegistryEntry<StatusEffect> key = null;
            RegistryEntry<StatusEffect> value = null;
            if (Registries.STATUS_EFFECT.getEntry(Identifier.tryParse(pair.getKey())).isPresent()) {
                key = Registries.STATUS_EFFECT.getEntry(Identifier.tryParse(pair.getKey())).get();
            }
            if (Registries.STATUS_EFFECT.getEntry(Identifier.tryParse(pair.getValue())).isPresent()) {
                value = Registries.STATUS_EFFECT.getEntry(Identifier.tryParse(pair.getValue())).get();
            }
            if (key != null && value != null) {
                configMap.put(key, value);
            }
        }

        if (herbalEffect.equals(EXTINGUISH)) livingEntity.extinguishWithSound();
        if (herbalEffect.equals(HEAL)) livingEntity.heal(config.healAmount.value());
        if (herbalEffect.equals(FEED) && livingEntity instanceof PlayerEntity) {
            ((PlayerEntity) livingEntity).getHungerManager().add(config.nutritionAmount.value(), config.saturationModifier.value());
        }
        if (herbalEffect.equals(CONVERT_POSITIVE_TO_NEGATIVE)) {
            for (StatusEffectInstance positiveEffect : livingEntity.getActiveStatusEffects().values()) {
                effectType = positiveEffect.getEffectType();
                if (!positiveEffect.isAmbient() && !livingEntity.hasStatusEffect(configMap.get(effectType)) && configMap.containsKey(effectType)) {
                    effectRemoval.add(positiveEffect);
                    effectAddition.add(new StatusEffectInstance(configMap.get(effectType), positiveEffect.getDuration(), positiveEffect.getAmplifier()));
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
                if (!negativeEffect.isAmbient() && !livingEntity.hasStatusEffect(getKeyEffect(effectType, configMap)) && configMap.containsValue(effectType)) {
                    effectRemoval.add(negativeEffect);
                    effectAddition.add(new StatusEffectInstance(getKeyEffect(effectType, configMap), negativeEffect.getDuration(), negativeEffect.getAmplifier()));
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
        if (herbalEffect.equals(LOWER_SNIFFER_COOLDOWN) && livingEntity instanceof SnifferEntity) {
            long cooldown = ((SnifferEntity) livingEntity).getBrain().getMemoryExpiry(MemoryModuleType.SNIFF_COOLDOWN);
            if (cooldown > 0) {
                livingEntity.getBrain().remember(MemoryModuleType.SNIFF_COOLDOWN, Unit.INSTANCE, cooldown / 2);
                if (!livingEntity.getEntityWorld().isClient()) {
                    ((ServerWorld) livingEntity.getEntityWorld()).spawnParticles(ParticleTypes.HAPPY_VILLAGER, livingEntity.getX(), livingEntity.getRandomBodyY() + 0.25, livingEntity.getZ(), 16, 1, 0.25, 1, 0);
                }
            }
        }
    }

    public static RegistryEntry<StatusEffect> getKeyEffect(RegistryEntry<StatusEffect> value, Map<RegistryEntry<StatusEffect>, RegistryEntry<StatusEffect>> map) {
        for (Map.Entry<RegistryEntry<StatusEffect>, RegistryEntry<StatusEffect>> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static Identifier createEffectId(String name) {
        return Identifier.of(MintyBlends.MOD_ID, name);
    }
}
