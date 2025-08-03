package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.status_effect.MintyStatusEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import static io.github.mintynoura.mintyblends.status_effect.MintyStatusEffect.reachingRangeIncrease;

public class ModStatusEffects {
    public static final RegistryEntry<StatusEffect> REACHING = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MintyBlends.MOD_ID, "reaching"), new MintyStatusEffect(StatusEffectCategory.BENEFICIAL, 0xe5d4c0)
            .addAttributeModifier(EntityAttributes.BLOCK_INTERACTION_RANGE, Identifier.of(MintyBlends.MOD_ID, "effect.reaching"), reachingRangeIncrease, EntityAttributeModifier.Operation.ADD_VALUE)
            .addAttributeModifier(EntityAttributes.ENTITY_INTERACTION_RANGE, Identifier.of(MintyBlends.MOD_ID, "effect.reaching"), reachingRangeIncrease, EntityAttributeModifier.Operation.ADD_VALUE));
    public static final RegistryEntry<StatusEffect> RENDING = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MintyBlends.MOD_ID, "rending"), new MintyStatusEffect(StatusEffectCategory.HARMFUL, 0xd5a3dc));
    public static final RegistryEntry<StatusEffect> STEALTH = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MintyBlends.MOD_ID, "stealth"), new MintyStatusEffect(StatusEffectCategory.BENEFICIAL, 0x6167cf));

    public static void registerStatusEffects() {}
}
