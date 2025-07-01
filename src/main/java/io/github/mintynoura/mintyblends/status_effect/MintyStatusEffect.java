package io.github.mintynoura.mintyblends.status_effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class MintyStatusEffect extends StatusEffect {
    public MintyStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    public static float stealthRangeModifier = 0.2f;
    public static float rendingDamageModifier = 0.2f;
}
