package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.status_effect.MintyBlendsStatusEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class MintyBlendsStatusEffects {
    public static final Holder<MobEffect> REACHING = register( "reaching", new MintyBlendsStatusEffect(MobEffectCategory.BENEFICIAL, 0xe5d4c0)
            .addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, Identifier.fromNamespaceAndPath(MintyBlends.ID, "effect.reaching"),
                    MintyBlends.CONFIG.statusEffectSection.reachingBlockRangeModifier.value(), AttributeModifier.Operation.ADD_VALUE)
            .addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, Identifier.fromNamespaceAndPath(MintyBlends.ID, "effect.reaching"),
                    MintyBlends.CONFIG.statusEffectSection.reachingEntityRangeModifier.value(), AttributeModifier.Operation.ADD_VALUE));
    public static final Holder<MobEffect> RENDING = register("rending", new MintyBlendsStatusEffect(MobEffectCategory.HARMFUL, 0xd5a3dc));
    public static final Holder<MobEffect> STEALTH = register( "stealth", new MintyBlendsStatusEffect(MobEffectCategory.BENEFICIAL, 0x6167cf));
    public static final Holder<MobEffect> LAVAWALKER = register("lavawalker", new MintyBlendsStatusEffect(MobEffectCategory.BENEFICIAL, 0xff6433));
    public static final Holder<MobEffect> WATERWALKER = register("waterwalker", new MintyBlendsStatusEffect(MobEffectCategory.BENEFICIAL, 0xa6f0ff));
    public static final Holder<MobEffect> FAST_FALLING = register("fast_falling", new MintyBlendsStatusEffect(MobEffectCategory.HARMFUL, 0xb9ddf3)
            .addAttributeModifier(Attributes.GRAVITY, Identifier.fromNamespaceAndPath(MintyBlends.ID, "effect.fast_falling"),
                    MintyBlends.CONFIG.statusEffectSection.fastFallingGravityModifier.value(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static Holder<MobEffect> register(String name, MobEffect effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(MintyBlends.ID, name), effect);
    }

    public static void initialize() {}
}
