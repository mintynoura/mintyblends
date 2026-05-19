package io.github.mintynoura.mintyblends.item.component.consume_effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.mintynoura.mintyblends.registry.MintyBlendsConsumeEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

import java.util.*;

// TODO: sound effect and particles
public record ConvertEffectsConsumeEffect(boolean positiveToNegative, boolean negativeToPositive, Optional<List<EffectConversion>> effectConversions,
                                          Optional<ParticleOptions> particle, boolean particlesOnlyWhenConverted,
                                          Optional<Holder<SoundEvent>> soundEvent) implements ConsumeEffect {
    public static final MapCodec<ConvertEffectsConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.BOOL.optionalFieldOf("positive_to_negative", true).forGetter(ConvertEffectsConsumeEffect::positiveToNegative),
            Codec.BOOL.optionalFieldOf("negative_to_positive", true).forGetter(ConvertEffectsConsumeEffect::negativeToPositive),
            ExtraCodecs.compactListCodec(EffectConversion.CODEC).optionalFieldOf("effect_conversions").forGetter(ConvertEffectsConsumeEffect::effectConversions),
            ParticleTypes.CODEC.optionalFieldOf("particle").forGetter(ConvertEffectsConsumeEffect::particle),
            Codec.BOOL.optionalFieldOf("particles_only_when_converted", false).forGetter(ConvertEffectsConsumeEffect::particlesOnlyWhenConverted),
            SoundEvent.CODEC.optionalFieldOf("sound").forGetter(ConvertEffectsConsumeEffect::soundEvent)
            ).apply(i, ConvertEffectsConsumeEffect::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ConvertEffectsConsumeEffect> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            ConvertEffectsConsumeEffect::positiveToNegative,
            ByteBufCodecs.BOOL,
            ConvertEffectsConsumeEffect::negativeToPositive,
            EffectConversion.STREAM_CODEC.apply(ByteBufCodecs.list()).apply(ByteBufCodecs::optional),
            ConvertEffectsConsumeEffect::effectConversions,
            ParticleTypes.STREAM_CODEC.apply(ByteBufCodecs::optional),
            ConvertEffectsConsumeEffect::particle,
            ByteBufCodecs.BOOL,
            ConvertEffectsConsumeEffect::particlesOnlyWhenConverted,
            SoundEvent.STREAM_CODEC.apply(ByteBufCodecs::optional),
            ConvertEffectsConsumeEffect::soundEvent,
            ConvertEffectsConsumeEffect::new
    );
    public static Map<Holder<MobEffect>, Holder<MobEffect>> conversionMap = new HashMap<>();

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

        if (!conversionMap.isEmpty()) {
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
            if (soundEvent.isPresent()) {
                level.playSound(null, user.getOnPos(), soundEvent.orElseThrow().value(), SoundSource.PLAYERS);
            }
        }
        if ((!particlesOnlyWhenConverted || anyConverted) && level instanceof ServerLevel serverLevel)
            particle.ifPresent(particleOptions -> serverLevel.sendParticles(particleOptions, user.getX(), user.getY(0.5), user.getZ(), 5, 0.25, 0.25, 0.25, 0));
        return anyConverted;
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
