package io.github.mintynoura.mintyblends.item.component;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.mintynoura.mintyblends.util.HerbalEffectType;
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

import java.util.List;
import java.util.function.Consumer;

public record HerbalBrewComponent(List<Identifier> herbalEffects, List<StatusEffectInstance> potionEffects, List<String> ingredients) implements Consumable, TooltipAppender {
    public static final Codec<HerbalBrewComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Identifier.CODEC.listOf().optionalFieldOf("herbal_effects", List.of()).forGetter(HerbalBrewComponent::herbalEffects),
            StatusEffectInstance.CODEC.listOf().optionalFieldOf("potion_effects", List.of()).forGetter(HerbalBrewComponent::potionEffects),
            Codec.STRING.listOf().optionalFieldOf("ingredients",List.of()).forGetter(HerbalBrewComponent::ingredients)
            ).apply(builder, HerbalBrewComponent::new));

    public List<StatusEffectInstance> potionEffects() {
        return Lists.transform(this.potionEffects, StatusEffectInstance::new);
    }

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
            textConsumer.accept(Text.translatableWithFallback("tooltip.mintyblends.no_effects", "No Status Effects").formatted(Formatting.GRAY));
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
        if (!this.ingredients.isEmpty()) {
            textConsumer.accept(Text.translatableWithFallback("tooltip.mintyblends.ingredients", "Ingredients:").formatted(Formatting.GRAY));
            for (String ingredient : ingredients) {
                textConsumer.accept(
                    ScreenTexts.space().append(
                            Text.translatable(
                                    ingredient
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
        for (Identifier herbalEffect : herbalEffects) {
            HerbalEffectType.applyHerb(user, herbalEffect);
        }
        this.apply(user, stack.getOrDefault(DataComponentTypes.POTION_DURATION_SCALE, 1.0F));
    }
}
