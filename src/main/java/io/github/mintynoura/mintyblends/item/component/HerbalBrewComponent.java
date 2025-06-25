package io.github.mintynoura.mintyblends.item.component;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.mintynoura.mintyblends.registry.ModStatusEffects;
import io.github.mintynoura.mintyblends.util.HerbalEffectType;
import io.github.mintynoura.mintyblends.util.StatusEffectMap;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.Consumable;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static io.github.mintynoura.mintyblends.util.StatusEffectMap.statusEffectMap;

public record HerbalBrewComponent(List<Identifier> herbEffects, List<StatusEffectInstance> potionEffects) implements Consumable, TooltipAppender {
    public static final Codec<HerbalBrewComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Identifier.CODEC.listOf().optionalFieldOf("herb_effects", List.of()).forGetter(HerbalBrewComponent::herbEffects),
            StatusEffectInstance.CODEC.listOf().optionalFieldOf("potion_effects", List.of()).forGetter(HerbalBrewComponent::potionEffects)
            ).apply(builder, HerbalBrewComponent::new));

    public List<StatusEffectInstance> potionEffects() {
        return Lists.transform(this.potionEffects, StatusEffectInstance::new);
    }

    public List<Identifier> herbEffects() {
        return this.herbEffects;
    }

    public boolean hasHerbalEffect(Identifier herbalEffect) {
        return this.herbEffects.contains(herbalEffect);
    }

    public static final List<StatusEffectInstance> effectRemoval = new ArrayList<>();
    public static final List<StatusEffectInstance> effectAddition = new ArrayList<>();

    public static MutableText getEffectText(RegistryEntry<StatusEffect> effect, int amplifier) {
        MutableText mutableText = Text.translatable(effect.value().getTranslationKey());
        return amplifier > 0 ? Text.translatable("potion.withAmplifier", mutableText, Text.translatable("potion.potency." + amplifier)) : mutableText;
    }

    public Iterable<StatusEffectInstance> getEffects() {
        return this.potionEffects;
    }

    public void forEachEffect(Consumer<StatusEffectInstance> effectConsumer, float durationMultiplier) {
        for (StatusEffectInstance statusEffectInstance : this.potionEffects) {
            effectConsumer.accept(statusEffectInstance.withScaledDuration(durationMultiplier));
        }
    }

    public void apply(LivingEntity user, float durationMultiplier) {
        if (user.getWorld() instanceof ServerWorld serverWorld) {
            PlayerEntity playerEntity2 = user instanceof PlayerEntity playerEntity ? playerEntity : null;
            this.forEachEffect(effect -> {
                if (effect.getEffectType().value().isInstant()) {
                    effect.getEffectType().value().applyInstantEffect(serverWorld, playerEntity2, playerEntity2, user, effect.getAmplifier(), 1.0);
                } else {
                    user.addStatusEffect(effect);
                }
            }, durationMultiplier);
        }
    }

    public static void buildTooltip(Iterable<StatusEffectInstance> effects, Consumer<Text> textConsumer, float durationMultiplier, float tickRate) {
        List<Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier>> list = Lists.newArrayList();
        boolean bl = true;
        for (StatusEffectInstance statusEffectInstance : effects) {
            bl = false;
            RegistryEntry<StatusEffect> registryEntry = statusEffectInstance.getEffectType();
            int i = statusEffectInstance.getAmplifier();
            registryEntry.value().forEachAttributeModifier(i, (attribute, modifier) -> list.add(new Pair<>(attribute, modifier)));
            MutableText mutableText = getEffectText(registryEntry, i);
            if (!statusEffectInstance.isDurationBelow(20)) {
                mutableText = Text.translatable("potion.withDuration", mutableText, StatusEffectUtil.getDurationText(statusEffectInstance, durationMultiplier, tickRate));
            }

            textConsumer.accept(mutableText.formatted(registryEntry.value().getCategory().getFormatting()));
        }

        if (bl) {
            textConsumer.accept(Text.translatable("effect.none").formatted(Formatting.GRAY));
        }

        if (!list.isEmpty()) {
            textConsumer.accept(ScreenTexts.EMPTY);
            textConsumer.accept(Text.translatable("potion.whenDrank").formatted(Formatting.DARK_PURPLE));

            for (Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier> pair : list) {
                EntityAttributeModifier entityAttributeModifier = pair.getSecond();
                double d = entityAttributeModifier.value();
                double e;
                if (entityAttributeModifier.operation() != EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                        && entityAttributeModifier.operation() != EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                    e = entityAttributeModifier.value();
                } else {
                    e = entityAttributeModifier.value() * 100.0;
                }

                if (d > 0.0) {
                    textConsumer.accept(
                            Text.translatable(
                                            "attribute.modifier.plus." + entityAttributeModifier.operation().getId(),
                                            AttributeModifiersComponent.DECIMAL_FORMAT.format(e),
                                            Text.translatable(pair.getFirst().value().getTranslationKey())
                                    )
                                    .formatted(Formatting.BLUE)
                    );
                } else if (d < 0.0) {
                    e *= -1.0;
                    textConsumer.accept(
                            Text.translatable(
                                            "attribute.modifier.take." + entityAttributeModifier.operation().getId(),
                                            AttributeModifiersComponent.DECIMAL_FORMAT.format(e),
                                            Text.translatable(pair.getFirst().value().getTranslationKey())
                                    )
                                    .formatted(Formatting.RED)
                    );
                }
            }
        }
    }


    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        if (!this.herbEffects.isEmpty()) {
            textConsumer.accept(Text.literal("Herbs:").formatted(Formatting.GRAY));
            for (Identifier herbalEffectId : herbEffects) {
                textConsumer.accept(
                    ScreenTexts.space().append(
                            Text.translatable(
                                    herbalEffectId.toTranslationKey("brew")
                            ).formatted(Formatting.DARK_AQUA)
                    )
                );
            }
            textConsumer.accept(ScreenTexts.EMPTY);
        }
        buildTooltip(this.getEffects(), textConsumer, components.getOrDefault(DataComponentTypes.POTION_DURATION_SCALE, 1.0F), context.getUpdateTickRate());
    }

    @Override
    public void onConsume(World world, LivingEntity user, ItemStack stack, ConsumableComponent consumable) {
        RegistryEntry<StatusEffect> effectType;
        if (this.hasHerbalEffect(HerbalEffectType.EXTINGUISH)) user.extinguishWithSound();
        if (this.hasHerbalEffect(HerbalEffectType.HEAL)) user.heal(2);
        if (this.hasHerbalEffect(HerbalEffectType.CURE_POISON_AND_WITHER)) {
            user.removeStatusEffect(StatusEffects.POISON);
            user.removeStatusEffect(StatusEffects.WITHER);
        }
        if (this.hasHerbalEffect(HerbalEffectType.CURE_WEAKNESS_AND_RENDING)) {
            user.removeStatusEffect(StatusEffects.WEAKNESS);
            user.removeStatusEffect(ModStatusEffects.RENDING);
        }
        if (this.hasHerbalEffect(HerbalEffectType.CONVERT_POSITIVE_TO_NEGATIVE)) {
            for (StatusEffectInstance positiveEffect : user.getActiveStatusEffects().values()) {
                 effectType = positiveEffect.getEffectType();
                if (!positiveEffect.isAmbient() && !user.hasStatusEffect(statusEffectMap.get(effectType)) && statusEffectMap.containsKey(effectType)) {
                        effectRemoval.add(positiveEffect);
                        effectAddition.add(new StatusEffectInstance(statusEffectMap.get(effectType), positiveEffect.getDuration(), positiveEffect.getAmplifier()));
                }
            }
            for (StatusEffectInstance removal : effectRemoval) {
                user.removeStatusEffect(removal.getEffectType());
            }
            for (StatusEffectInstance addition : effectAddition) {
                user.addStatusEffect(addition, user);
            }
            effectRemoval.clear();
            effectAddition.clear();
        }
        if (this.hasHerbalEffect(HerbalEffectType.CONVERT_NEGATIVE_TO_POSITIVE)) {
            for (StatusEffectInstance negativeEffect : user.getActiveStatusEffects().values()) {
                effectType = negativeEffect.getEffectType();
                if (!negativeEffect.isAmbient() && !user.hasStatusEffect(StatusEffectMap.getKeyEffect(effectType)) && statusEffectMap.containsValue(effectType)) {
                        effectRemoval.add(negativeEffect);
                        effectAddition.add(new StatusEffectInstance(StatusEffectMap.getKeyEffect(effectType), negativeEffect.getDuration(), negativeEffect.getAmplifier()));
                }
            }
            for (StatusEffectInstance removal : effectRemoval) {
                user.removeStatusEffect(removal.getEffectType());

            }
            for (StatusEffectInstance addition : effectAddition) {
                user.addStatusEffect(addition, user);
            }
            effectRemoval.clear();
            effectAddition.clear();
        }
        this.apply(user, stack.getOrDefault(DataComponentTypes.POTION_DURATION_SCALE, 1.0F));
    }
}
