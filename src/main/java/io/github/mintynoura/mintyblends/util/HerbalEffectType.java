package io.github.mintynoura.mintyblends.util;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.MintyBlendsConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.player.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HerbalEffectType {

    public static final List<MobEffectInstance> effectRemoval = new ArrayList<>();
    public static final List<MobEffectInstance> effectAddition = new ArrayList<>();

    public static Identifier EXTINGUISH = createEffectId("extinguish");
    public static Identifier HEAL = createEffectId("heal");
    public static Identifier FEED = createEffectId("feed");
    public static Identifier CONVERT_NEGATIVE_TO_POSITIVE = createEffectId("convert_negative_to_positive");
    public static Identifier CONVERT_POSITIVE_TO_NEGATIVE = createEffectId("convert_positive_to_negative");
    public static Identifier CLEAR_NEGATIVE = createEffectId("clear_negative");
    public static Identifier CLEAR_POSITIVE = createEffectId("clear_positive");
    public static Identifier LOWER_SNIFFER_COOLDOWN = createEffectId("lower_sniffer_cooldown");

    public static void applyHerb(LivingEntity livingEntity, Identifier herbalEffect) {
        Holder<MobEffect> effectType;
        Map<Holder<MobEffect>, Holder<MobEffect>> configMap = new HashMap<>();
        MintyBlendsConfig config = MintyBlends.CONFIG;
        for (Map.Entry<String, String> pair : config.statusEffectSection.statusEffectMap.value()) {
            Holder<MobEffect> key = null;
            Holder<MobEffect> value = null;
            if (BuiltInRegistries.MOB_EFFECT.get(Identifier.tryParse(pair.getKey())).isPresent()) {
                key = BuiltInRegistries.MOB_EFFECT.get(Identifier.tryParse(pair.getKey())).get();
            }
            if (BuiltInRegistries.MOB_EFFECT.get(Identifier.tryParse(pair.getValue())).isPresent()) {
                value = BuiltInRegistries.MOB_EFFECT.get(Identifier.tryParse(pair.getValue())).get();
            }
            if (key != null && value != null) {
                configMap.put(key, value);
            }
        }

        if (herbalEffect.equals(EXTINGUISH)) livingEntity.extinguishFire();
        if (herbalEffect.equals(HEAL)) livingEntity.heal(config.healAmount.value());
        if (herbalEffect.equals(FEED) && livingEntity instanceof Player) {
            ((Player) livingEntity).getFoodData().eat(config.nutritionAmount.value(), config.saturationModifier.value());
        }
        if (herbalEffect.equals(CONVERT_POSITIVE_TO_NEGATIVE)) {
            for (MobEffectInstance positiveEffect : livingEntity.getActiveEffectsMap().values()) {
                effectType = positiveEffect.getEffect();
                if (!positiveEffect.isAmbient() && !livingEntity.hasEffect(configMap.get(effectType)) && configMap.containsKey(effectType)) {
                    effectRemoval.add(positiveEffect);
                    effectAddition.add(new MobEffectInstance(configMap.get(effectType), positiveEffect.getDuration(), positiveEffect.getAmplifier()));
                }
            }
            for (MobEffectInstance removal : effectRemoval) {
                livingEntity.removeEffect(removal.getEffect());
            }
            for (MobEffectInstance addition : effectAddition) {
                livingEntity.addEffect(addition, livingEntity);
            }
            effectRemoval.clear();
            effectAddition.clear();
        }
        if (herbalEffect.equals(CONVERT_NEGATIVE_TO_POSITIVE)) {
            for (MobEffectInstance negativeEffect : livingEntity.getActiveEffectsMap().values()) {
                effectType = negativeEffect.getEffect();
                if (!negativeEffect.isAmbient() && !livingEntity.hasEffect(getKeyEffect(effectType, configMap)) && configMap.containsValue(effectType)) {
                    effectRemoval.add(negativeEffect);
                    effectAddition.add(new MobEffectInstance(getKeyEffect(effectType, configMap), negativeEffect.getDuration(), negativeEffect.getAmplifier()));
                }
            }
            for (MobEffectInstance removal : effectRemoval) {
                livingEntity.removeEffect(removal.getEffect());
            }
            for (MobEffectInstance addition : effectAddition) {
                livingEntity.addEffect(addition, livingEntity);
            }
            effectRemoval.clear();
            effectAddition.clear();
        }
        if (herbalEffect.equals(CLEAR_NEGATIVE)) {
            for (MobEffectInstance negativeEffect : livingEntity.getActiveEffectsMap().values()) {
                if (negativeEffect.getEffect().value().getCategory().equals(MobEffectCategory.HARMFUL)) {
                    effectRemoval.add(negativeEffect);
                }
            }
            for (MobEffectInstance removal : effectRemoval) {
                livingEntity.removeEffect(removal.getEffect());
            }
            effectRemoval.clear();
        }
        if (herbalEffect.equals(CLEAR_POSITIVE)) {
            for (MobEffectInstance positiveEffect : livingEntity.getActiveEffectsMap().values()) {
                if (positiveEffect.getEffect().value().getCategory().equals(MobEffectCategory.BENEFICIAL)) {
                    effectRemoval.add(positiveEffect);
                }
            }
            for (MobEffectInstance removal : effectRemoval) {
                livingEntity.removeEffect(removal.getEffect());
            }
            effectRemoval.clear();
        }
        if (herbalEffect.equals(LOWER_SNIFFER_COOLDOWN) && livingEntity instanceof Sniffer) {
            long cooldown = ((Sniffer) livingEntity).getBrain().getTimeUntilExpiry(MemoryModuleType.SNIFF_COOLDOWN);
            if (cooldown > 0) {
                livingEntity.getBrain().setMemoryWithExpiry(MemoryModuleType.SNIFF_COOLDOWN, Unit.INSTANCE, cooldown / 2);
                if (!livingEntity.level().isClientSide()) {
                    ((ServerLevel) livingEntity.level()).sendParticles(ParticleTypes.HAPPY_VILLAGER, livingEntity.getX(), livingEntity.getRandomY() + 0.25, livingEntity.getZ(), 16, 1, 0.25, 1, 0);
                }
            }
        }
    }

    public static Holder<MobEffect> getKeyEffect(Holder<MobEffect> value, Map<Holder<MobEffect>, Holder<MobEffect>> map) {
        for (Map.Entry<Holder<MobEffect>, Holder<MobEffect>> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static Identifier createEffectId(String name) {
        return Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, name);
    }
}
