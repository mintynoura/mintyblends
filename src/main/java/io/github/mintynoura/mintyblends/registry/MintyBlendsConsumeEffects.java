package io.github.mintynoura.mintyblends.registry;

import com.mojang.serialization.MapCodec;
import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.item.component.consume_effects.ConvertEffectsConsumeEffect;
import io.github.mintynoura.mintyblends.item.component.consume_effects.ExtinguishConsumeEffect;
import io.github.mintynoura.mintyblends.item.component.consume_effects.HealConsumeEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;

import java.util.Map;

import static io.github.mintynoura.mintyblends.item.component.consume_effects.ConvertEffectsConsumeEffect.conversionMap;

public class MintyBlendsConsumeEffects {
    public static final ConsumeEffect.Type<ExtinguishConsumeEffect> EXTINGUISH = register("extinguish", ExtinguishConsumeEffect.CODEC, ExtinguishConsumeEffect.STREAM_CODEC);
    public static final ConsumeEffect.Type<HealConsumeEffect> HEAL = register("heal", HealConsumeEffect.CODEC, HealConsumeEffect.STREAM_CODEC);
    public static final ConsumeEffect.Type<ConvertEffectsConsumeEffect> CONVERT_EFFECTS = register("convert_effects", ConvertEffectsConsumeEffect.CODEC, ConvertEffectsConsumeEffect.STREAM_CODEC);

    public static <T extends ConsumeEffect> ConsumeEffect.Type<T> register(final String name, final MapCodec<T> codec, final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec) {
        return Registry.register(BuiltInRegistries.CONSUME_EFFECT_TYPE, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, name), new ConsumeEffect.Type<>(codec, streamCodec));
    }

    public static void populateEffectConversionMap() {
        Map<String, String> configMap = MintyBlends.CONFIG.statusEffectSection.statusEffectMap.value();

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

    public static void initialize() {
        populateEffectConversionMap();
    }
}
