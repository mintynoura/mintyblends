package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.status_effect.MintyStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModStatusEffects {
    public static final RegistryEntry<StatusEffect> RENDING = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MintyBlends.MOD_ID, "rending"), new MintyStatusEffect(StatusEffectCategory.HARMFUL, 0xd5a3dc));
    public static final RegistryEntry<StatusEffect> STEALTH = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MintyBlends.MOD_ID, "stealth"), new MintyStatusEffect(StatusEffectCategory.BENEFICIAL, 0xb91ccf));

    public static void registerStatusEffects() {}
}
