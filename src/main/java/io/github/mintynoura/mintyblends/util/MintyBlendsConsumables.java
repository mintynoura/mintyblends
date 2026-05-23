package io.github.mintynoura.mintyblends.util;

import io.github.mintynoura.mintyblends.item.component.consume_effects.ExtinguishConsumeEffect;
import io.github.mintynoura.mintyblends.item.component.consume_effects.HealConsumeEffect;
import io.github.mintynoura.mintyblends.item.component.consume_effects.ModifySnifferCooldownConsumeEffect;
import io.github.mintynoura.mintyblends.registry.MintyBlendsStatusEffects;
import net.minecraft.core.HolderSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.RemoveStatusEffectsConsumeEffect;

import java.util.Optional;

public class MintyBlendsConsumables {
    public static final Consumable MINT = Consumables.defaultFood()
            .onConsume(new ExtinguishConsumeEffect(Optional.of(ParticleTypes.SNOWFLAKE), false)).build();
    public static final Consumable CATNIP = Consumables.defaultFood()
            .onConsume(new ModifySnifferCooldownConsumeEffect(0.5f, Optional.of(ParticleTypes.HAPPY_VILLAGER)))
            .onConsume(new RemoveStatusEffectsConsumeEffect(MobEffects.SLOWNESS)).build();
    public static final Consumable MEDICINAL_HERB = Consumables.defaultFood()
            .onConsume(new HealConsumeEffect(2f)).build();
    public static final Consumable CULINARY_HERB = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SATURATION, 4, 0), 0.5f)).build();
    public static final Consumable SAGEBRUSH = Consumables.defaultFood()
            .onConsume(new RemoveStatusEffectsConsumeEffect(HolderSet.direct(MobEffects.WEAKNESS, MintyBlendsStatusEffects.RENDING))).build();
    public static final Consumable CUREFLOWER = Consumable.builder()
            .onConsume(new RemoveStatusEffectsConsumeEffect(MobEffects.WITHER)).build();
    public static final Consumable RENDFLOWER = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MintyBlendsStatusEffects.RENDING, 400, 0))).build();
    public static final Consumable INFERNALILY = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MintyBlendsStatusEffects.LAVAWALKER, 200, 0))).build();
    public static final Consumable MINT_TEA = Consumables.defaultDrink()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1800, 0)))
            .onConsume(new ExtinguishConsumeEffect(Optional.of(ParticleTypes.SNOWFLAKE), false)).build();
    public static final Consumable GLOW_BERRY_TEA = Consumables.defaultDrink()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.GLOWING, 600, 0))).build();
    public static final Consumable WILDFLOWER_TEA = Consumables.defaultDrink()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE, 600, 0)))
            .onConsume(new RemoveStatusEffectsConsumeEffect(MobEffects.POISON)).build();
    public static final Consumable TORCHFLOWER_TEA = Consumables.defaultDrink()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1800, 0)))
            .onConsume(new RemoveStatusEffectsConsumeEffect(MobEffects.POISON)).build();
    public static final Consumable MINT_JELLY = Consumables.defaultFood()
            .onConsume(new ExtinguishConsumeEffect(Optional.of(ParticleTypes.SNOWFLAKE), false)).build();
    public static final Consumable STEAK_TARTARE = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NAUSEA, 300, 0), 0.2f)).build();
}
