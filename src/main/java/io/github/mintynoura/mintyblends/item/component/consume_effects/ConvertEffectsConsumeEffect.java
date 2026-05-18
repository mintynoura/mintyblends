package io.github.mintynoura.mintyblends.item.component.consume_effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.registry.MintyBlendsConsumeEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

import java.util.*;

// TODO: sound effect and particles
public record ConvertEffectsConsumeEffect(boolean positiveToNegative, boolean negativeToPositive, Optional<List<EffectConversion>> effectConversions) implements ConsumeEffect {
    public static final MapCodec<ConvertEffectsConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.BOOL.optionalFieldOf("positive_to_negative", true).forGetter(ConvertEffectsConsumeEffect::positiveToNegative),
            Codec.BOOL.optionalFieldOf("negative_to_positive", true).forGetter(ConvertEffectsConsumeEffect::negativeToPositive),
            EffectConversion.CODEC.listOf().optionalFieldOf("effect_conversions").forGetter(ConvertEffectsConsumeEffect::effectConversions)
    ).apply(i, ConvertEffectsConsumeEffect::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ConvertEffectsConsumeEffect> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            ConvertEffectsConsumeEffect::positiveToNegative,
            ByteBufCodecs.BOOL,
            ConvertEffectsConsumeEffect::negativeToPositive,
            EffectConversion.STREAM_CODEC.apply(ByteBufCodecs.list()).apply(ByteBufCodecs::optional),
            ConvertEffectsConsumeEffect::effectConversions,
            ConvertEffectsConsumeEffect::new
    );
    @Override
    public Type<? extends ConsumeEffect> getType() {
        return MintyBlendsConsumeEffects.CONVERT_EFFECTS;
    }

    @Override
    public boolean apply(Level level, ItemStack stack, LivingEntity user) {
        boolean anyConverted = false;

        Holder<MobEffect> mobEffect;
        List<MobEffectInstance> effectsToRemove = new ArrayList<>();
        List<MobEffectInstance> effectsToAdd = new ArrayList<>();
        Map<Holder<MobEffect>, Holder<MobEffect>> conversionMap = new HashMap<>();
        Map<String, String> configMap = MintyBlends.CONFIG.statusEffectSection.statusEffectMap.value();

        if (!configMap.isEmpty()) {
            for (Map.Entry<String, String> entry : configMap.entrySet()) {
                if (isValidIdentifier(entry.getKey()) && isValidIdentifier(entry.getValue())) {
                    Identifier keyId = Identifier.parse(entry.getKey());
                    Identifier valueId = Identifier.parse(entry.getValue());
                    if (isValidEffect(keyId) && isValidEffect(valueId)) {
                        Holder<MobEffect> key = BuiltInRegistries.MOB_EFFECT.get(keyId).orElseThrow();
                        Holder<MobEffect> value = BuiltInRegistries.MOB_EFFECT.get(valueId).orElseThrow();
                        conversionMap.put(key, value);
                    }
                }
            }
            if (positiveToNegative || effectConversions.isPresent()) {
                effectConversions.ifPresent(conversions -> conversions.forEach(effectConversion -> conversionMap.put(effectConversion.effect, effectConversion.convertedEffect)));
                for (MobEffectInstance effect : user.getActiveEffects()) {
                    mobEffect = effect.getEffect();
                    if (!effect.isAmbient() && conversionMap.containsKey(mobEffect) && !user.hasEffect(conversionMap.get(mobEffect))) {
                        effectsToRemove.add(effect);
                        effectsToAdd.add(new MobEffectInstance(conversionMap.get(mobEffect), effect.getDuration(), effect.getAmplifier()));
                    }
                }
            }
            if (negativeToPositive) {
                for (MobEffectInstance negativeEffect : user.getActiveEffects()) {
                    mobEffect = negativeEffect.getEffect();
                    if (!negativeEffect.isAmbient() && conversionMap.containsValue(mobEffect) && !user.hasEffect(getKeyEffect(mobEffect, conversionMap))) {
                        effectsToRemove.add(negativeEffect);
                        effectsToAdd.add(new MobEffectInstance(getKeyEffect(mobEffect, conversionMap), negativeEffect.getDuration(), negativeEffect.getAmplifier()));
                    }
                }
            }
        }
        if (!effectsToAdd.isEmpty() && !effectsToRemove.isEmpty()) {
            effectsToRemove.forEach(removedEffect -> user.removeEffect(removedEffect.getEffect()));
            effectsToAdd.forEach(addedEffect -> user.addEffect(addedEffect, user));
            anyConverted = true;
            effectsToRemove.clear();
            effectsToAdd.clear();
        }
        return anyConverted;
    }

    public static boolean isValidIdentifier(String effect) {
        boolean bl = Identifier.tryParse(effect) != null;
        if (!bl) MintyBlends.LOGGER.error("Invalid identifier: {}", effect);
        return bl;
    }

    public static boolean isValidEffect(Identifier id) {
        boolean bl = BuiltInRegistries.MOB_EFFECT.get(id).isPresent();
        if (!bl) MintyBlends.LOGGER.error("Unknown effect: {}", id);
        return bl;
    }

    public static Holder<MobEffect> getKeyEffect(Holder<MobEffect> value, Map<Holder<MobEffect>, Holder<MobEffect>> map) {
        return map.keySet().stream().filter(key -> map.get(key).equals(value)).findFirst().orElseThrow();
    }

    public record EffectConversion(Holder<MobEffect> effect, Holder<MobEffect> convertedEffect) {
        public static final Codec<EffectConversion> CODEC = RecordCodecBuilder.create(i -> i.group(
                MobEffect.CODEC.fieldOf("effect").forGetter(EffectConversion::effect),
                MobEffect.CODEC.fieldOf("converted_effect").forGetter(EffectConversion::convertedEffect)
        ).apply(i, EffectConversion::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, EffectConversion> STREAM_CODEC = StreamCodec.composite(
          MobEffect.STREAM_CODEC,
          EffectConversion::effect,
          MobEffect.STREAM_CODEC,
          EffectConversion::convertedEffect,
          EffectConversion::new
        );
    }
}
