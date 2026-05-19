package io.github.mintynoura.mintyblends.item.component;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record CenserComponent(float range, List<MobEffectInstance> potionEffects, List<String> ingredients, List<ConsumeEffect> consumeEffects) implements TooltipProvider {
    public static final Codec<CenserComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("range", 5f).forGetter(CenserComponent::range),
            MobEffectInstance.CODEC.listOf().optionalFieldOf("potion_effects", List.of()).forGetter(CenserComponent::potionEffects),
            Codec.STRING.listOf().optionalFieldOf("ingredients", List.of()).forGetter(CenserComponent::ingredients),
            ConsumeEffect.CODEC.listOf().optionalFieldOf("consume_effects", List.of()).forGetter(CenserComponent::consumeEffects)
            ).apply(builder, CenserComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, CenserComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            CenserComponent::range,
            MobEffectInstance.STREAM_CODEC.apply(ByteBufCodecs.list()),
            CenserComponent::potionEffects,
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()),
            CenserComponent::ingredients,
            ConsumeEffect.STREAM_CODEC.apply(ByteBufCodecs.list()),
            CenserComponent::consumeEffects,
            CenserComponent::new
    );

    @Override
    public float range() {
        return range;
    }

    public List<MobEffectInstance> potionEffects() {
        return Lists.transform(this.potionEffects, MobEffectInstance::new);
    }

    public static MutableComponent getEffectText(Holder<MobEffect> effect, int amplifier) {
        MutableComponent mutableText = Component.translatable(effect.value().getDescriptionId());
        return amplifier > 0 ? Component.translatable("potion.withAmplifier", mutableText, Component.translatable("potion.potency." + amplifier)) : mutableText;
    }

    public Iterable<MobEffectInstance> getEffects() {
        return this.potionEffects;
    }

    public void forEachEffect(Consumer<MobEffectInstance> effectConsumer, float durationMultiplier) {
        for (MobEffectInstance statusEffectInstance : this.potionEffects) {
            effectConsumer.accept(statusEffectInstance.withScaledDuration(durationMultiplier));
        }
    }

    public void apply(LivingEntity user, float durationMultiplier) {
        if (!user.level().isClientSide()) {
            this.forEachEffect(user::addEffect, durationMultiplier);
        }
    }

    public static void buildTooltip(Iterable<MobEffectInstance> effects, Consumer<Component> textConsumer, float durationMultiplier, float tickRate) {
        List<Pair<Holder<Attribute>, AttributeModifier>> list = Lists.newArrayList();
        boolean bl = true;
        for (MobEffectInstance statusEffectInstance : effects) {
            bl = false;
            Holder<MobEffect> registryEntry = statusEffectInstance.getEffect();
            int i = statusEffectInstance.getAmplifier();
            registryEntry.value().createModifiers(i, (attribute, modifier) -> list.add(new Pair<>(attribute, modifier)));
            MutableComponent mutableText = getEffectText(registryEntry, i);
            if (!statusEffectInstance.endsWithin(20)) {
                mutableText = Component.translatable("potion.withDuration", mutableText, MobEffectUtil.formatDuration(statusEffectInstance, durationMultiplier, tickRate));
            }

            textConsumer.accept(mutableText.withStyle(registryEntry.value().getCategory().getTooltipFormatting()));
        }

        if (bl) {
            textConsumer.accept(Component.translatableWithFallback("tooltip.mintyblends.no_effects", "No Potion Effects").withStyle(ChatFormatting.GRAY));
        }

        if (!list.isEmpty()) {
            textConsumer.accept(CommonComponents.EMPTY);
            textConsumer.accept(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));

            for (Pair<Holder<Attribute>, AttributeModifier> pair : list) {
                AttributeModifier entityAttributeModifier = pair.getSecond();
                double d = entityAttributeModifier.amount();
                double e;
                if (entityAttributeModifier.operation() != AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                        && entityAttributeModifier.operation() != AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                    e = entityAttributeModifier.amount();
                } else {
                    e = entityAttributeModifier.amount() * 100.0;
                }

                if (d > 0.0) {
                    textConsumer.accept(
                            Component.translatable(
                                            "attribute.modifier.plus." + entityAttributeModifier.operation().id(),
                                            ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(e),
                                            Component.translatable(pair.getFirst().value().getDescriptionId())
                                    )
                                    .withStyle(ChatFormatting.BLUE)
                    );
                } else if (d < 0.0) {
                    e *= -1.0;
                    textConsumer.accept(
                            Component.translatable(
                                            "attribute.modifier.take." + entityAttributeModifier.operation().id(),
                                            ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(e),
                                            Component.translatable(pair.getFirst().value().getDescriptionId())
                                    )
                                    .withStyle(ChatFormatting.RED)
                    );
                }
            }
        }
    }


    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag type, DataComponentGetter components) {
        if (type.isAdvanced()) {
            consumer.accept(Component.translatableWithFallback("tooltip.mintyblends.range", "Range:").append(CommonComponents.space().append(String.valueOf(range))));
            consumer.accept(CommonComponents.EMPTY);
        }
        if (!this.ingredients.isEmpty()) {
            Set<String> ingredientSet = new HashSet<>();
            consumer.accept(Component.translatableWithFallback("tooltip.mintyblends.ingredients", "Ingredients:").withStyle(ChatFormatting.GRAY));
            for (String ingredient : ingredients) {
                if (!ingredientSet.contains(ingredient)) {
                    if (Collections.frequency(ingredients, ingredient) > 1) {
                        consumer.accept(
                                CommonComponents.space().append(
                                        Component.translatable(ingredient).append(" x" + Collections.frequency(ingredients, ingredient)).withStyle(ChatFormatting.DARK_AQUA)
                                ));
                    } else consumer.accept(
                            CommonComponents.space().append(
                                    Component.translatable(ingredient).withStyle(ChatFormatting.DARK_AQUA)
                            )
                    );
                    ingredientSet.add(ingredient);
                }
            }
            consumer.accept(CommonComponents.EMPTY);
        }
        buildTooltip(this.getEffects(), consumer, components.getOrDefault(DataComponents.POTION_DURATION_SCALE, 1.0F), context.tickRate());
    }

    public void applyIncense(LivingEntity entity, ItemStack stack) {
        if (!entity.level().isClientSide()) {
            this.consumeEffects.forEach(effect -> effect.apply(entity.level(), stack, entity));
        }
        this.apply(entity, stack.getOrDefault(DataComponents.POTION_DURATION_SCALE, 1.0F));
        if (entity.level().isClientSide()) {
            for (int i = 0; i < 4; i++) {
                entity.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, entity.getRandomX(1.25), entity.getRandomY(), entity.getRandomZ(1.25), 0, 0, 0);
            }
        }
    }
}

