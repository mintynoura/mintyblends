package io.github.mintynoura.mintyblends.util;

import io.github.mintynoura.mintyblends.MintyBlends;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;

import java.util.ArrayList;
import java.util.List;

import static io.github.mintynoura.mintyblends.util.StatusEffectMap.statusEffectMap;

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
        if (herbalEffect.equals(EXTINGUISH)) livingEntity.extinguishWithSound();
        if (herbalEffect.equals(HEAL)) livingEntity.heal(2);
        if (herbalEffect.equals(FEED) && livingEntity instanceof PlayerEntity) {
            ((PlayerEntity) livingEntity).getHungerManager().add(2, 0.2f);
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
                if (!livingEntity.getWorld().isClient) {
                    ((ServerWorld) livingEntity.getWorld()).spawnParticles(ParticleTypes.HAPPY_VILLAGER, livingEntity.getX(), livingEntity.getRandomBodyY() + 0.25, livingEntity.getZ(), 16, 1, 0.25, 1, 0);
                }
            }
        }
    }

    public static Identifier createEffectId(String name) {
        return Identifier.of(MintyBlends.MOD_ID, name);
    }
}
