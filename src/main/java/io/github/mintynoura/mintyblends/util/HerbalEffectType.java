package io.github.mintynoura.mintyblends.util;

import io.github.mintynoura.mintyblends.MintyBlends;
import net.minecraft.util.Identifier;

public class HerbalEffectType {
    public static Identifier EXTINGUISH = createHerbId("extinguish");
    public static Identifier HEAL = createHerbId("heal");
    public static Identifier CURE_POISON_AND_WITHER = createHerbId("cure_poison_and_wither");
    public static Identifier CURE_WEAKNESS_AND_RENDING = createHerbId("cure_weakness_and_rending");
    public static Identifier CONVERT_NEGATIVE_TO_POSITIVE = createHerbId("convert_negative_to_positive");
    public static Identifier CONVERT_POSITIVE_TO_NEGATIVE = createHerbId("convert_positive_to_negative");
    public static Identifier CLEAR_EFFECTS = createHerbId("clear_effects");
    public static Identifier TELEPORT_RANDOMLY = createHerbId("teleport_randomly");

    public static Identifier createHerbId(String name) {
        return Identifier.of(MintyBlends.MOD_ID, name);
    }
}
