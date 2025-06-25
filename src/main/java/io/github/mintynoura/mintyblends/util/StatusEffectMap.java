package io.github.mintynoura.mintyblends.util;

import io.github.mintynoura.mintyblends.registry.ModStatusEffects;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.HashMap;
import java.util.Map;

public class StatusEffectMap {
    public static Map<RegistryEntry<StatusEffect>, RegistryEntry<StatusEffect>> statusEffectMap = new HashMap<>();

    public static RegistryEntry<StatusEffect> getKeyEffect(RegistryEntry<StatusEffect> value) {
        for (Map.Entry<RegistryEntry<StatusEffect>, RegistryEntry<StatusEffect>> entry : statusEffectMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static void addEffectsToMap() {
        statusEffectMap.put(StatusEffects.REGENERATION, StatusEffects.POISON);
        statusEffectMap.put(StatusEffects.SPEED, StatusEffects.SLOWNESS);
        statusEffectMap.put(StatusEffects.STRENGTH, StatusEffects.WEAKNESS);
        statusEffectMap.put(StatusEffects.RESISTANCE, ModStatusEffects.RENDING);
        statusEffectMap.put(StatusEffects.HASTE, StatusEffects.MINING_FATIGUE);
        statusEffectMap.put(StatusEffects.LUCK, StatusEffects.UNLUCK);
    }
}
