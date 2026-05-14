package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.status_effect.MintyStatusEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class MintyBlendsStatusEffects {
    public static final Holder<MobEffect> REACHING = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "reaching"), new MintyStatusEffect(MobEffectCategory.BENEFICIAL, 0xe5d4c0)
            .addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "effect.reaching"), MintyBlends.CONFIG.statusEffectSection.reachingBlockRangeModifier.value(), AttributeModifier.Operation.ADD_VALUE)
            .addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "effect.reaching"), MintyBlends.CONFIG.statusEffectSection.reachingEntityRangeModifier.value(), AttributeModifier.Operation.ADD_VALUE));
    public static final Holder<MobEffect> RENDING = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "rending"), new MintyStatusEffect(MobEffectCategory.HARMFUL, 0xd5a3dc));
    public static final Holder<MobEffect> STEALTH = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "stealth"), new MintyStatusEffect(MobEffectCategory.BENEFICIAL, 0x6167cf));

    public static void registerStatusEffects() {}
}
