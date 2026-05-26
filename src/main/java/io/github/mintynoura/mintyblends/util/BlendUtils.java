package io.github.mintynoura.mintyblends.util;

import io.github.mintynoura.mintyblends.item.CenserItem;
import io.github.mintynoura.mintyblends.item.component.CenserComponent;
import io.github.mintynoura.mintyblends.item.component.HerbalBrewComponent;
import io.github.mintynoura.mintyblends.item.component.consume_effects.HealConsumeEffect;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipeInput;
import io.github.mintynoura.mintyblends.registry.MintyBlendsComponents;
import io.github.mintynoura.mintyblends.registry.MintyBlendsItems;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.level.block.SuspiciousEffectHolder;

import java.util.*;

public class BlendUtils {
    public static ItemStack blendBrew(KettleBrewingRecipeInput recipeInput, RandomSource random) {
        List<MobEffectInstance> mobEffects = new ArrayList<>();
        Set<MobEffectInstance> appliedMobEffects = new HashSet<>();
        List<String> ingredients = new ArrayList<>();
        Set<ConsumeEffect> consumeEffects = new HashSet<>();
        float durationModifier = 1f;
        float healStack = 0;
        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack itemStack = recipeInput.getItem(i);
            if (!itemStack.isEmpty()) {
                if (itemStack.is(MintyBlendsTags.Items.BLENDING_INGREDIENTS)) {
                    ingredients.add(itemStack.getItem().getDescriptionId());
                    if (itemStack.is(ItemTags.SMALL_FLOWERS) || itemStack.is(MintyBlendsTags.Items.HERBS)) {
                        SuspiciousEffectHolder suspiciousStewIngredient = SuspiciousEffectHolder.tryGet(itemStack.getItem());
                        if (suspiciousStewIngredient != null) {
                            suspiciousStewIngredient.getSuspiciousEffects().effects().stream()
                                    .map(SuspiciousStewEffects.Entry::createEffectInstance).forEach(mobEffects::add);
                        }
                    }
                    if (itemStack.has(DataComponents.CONSUMABLE) && !itemStack.get(DataComponents.CONSUMABLE).onConsumeEffects().isEmpty()) {
                        for (ConsumeEffect consumeEffect : itemStack.get(DataComponents.CONSUMABLE).onConsumeEffects()) {
                            if (consumeEffect instanceof ApplyStatusEffectsConsumeEffect(List<MobEffectInstance> effects, float probability)) {
                                if (random.nextFloat() < probability) mobEffects.addAll(effects);
                            } else if (consumeEffect instanceof HealConsumeEffect(float amount)) {
                                healStack += amount;
                            } else consumeEffects.add(consumeEffect);
                        }
                    }
                    if (itemStack.has(MintyBlendsComponents.BLEND_EFFECT_DURATION_MODIFIER)) {
                        durationModifier *= itemStack.getOrDefault(MintyBlendsComponents.BLEND_EFFECT_DURATION_MODIFIER, 1f);
                    }
                }
            }
        }
        if (healStack > 0) consumeEffects.add(new HealConsumeEffect(healStack));
        if (!mobEffects.isEmpty()) stackEffects(mobEffects, appliedMobEffects, durationModifier);
        ItemStack herbalBrew = new ItemStack(MintyBlendsItems.HERBAL_BREW);
        herbalBrew.set(MintyBlendsComponents.HERBAL_BREW, new HerbalBrewComponent(List.copyOf(appliedMobEffects), ingredients));
        herbalBrew.set(DataComponents.CONSUMABLE, new Consumable(1.6f, ItemUseAnimation.DRINK, SoundEvents.GENERIC_DRINK, false, List.copyOf(consumeEffects)));
        return herbalBrew;
    }

    public static ItemStack blendCenser(CraftingInput recipeInput, RandomSource random) {
        ItemStack censer = ItemStack.EMPTY;
        List<MobEffectInstance> mobEffects = new ArrayList<>();
        Set<MobEffectInstance> appliedMobEffects = new HashSet<>();
        List<String> ingredients = new ArrayList<>();
        Set<ConsumeEffect> consumeEffects = new HashSet<>();
        float durationModifier = 1f;
        float healStack = 0;
        int maxUses = 1;
        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack itemStack = recipeInput.getItem(i);
            if (!itemStack.isEmpty()) {
                if (itemStack.is(MintyBlendsTags.Items.CENSERS) && itemStack.getItem() instanceof CenserItem censerItem) {
                    censer = new ItemStack(itemStack.getItem());
                    maxUses = censerItem.getMaxUses();
                } else if (itemStack.is(MintyBlendsTags.Items.BLENDING_INGREDIENTS)) {
                    ingredients.add(itemStack.getItem().getDescriptionId());
                    if (itemStack.is(ItemTags.SMALL_FLOWERS) || itemStack.is(MintyBlendsTags.Items.HERBS)) {
                        SuspiciousEffectHolder suspiciousStewIngredient = SuspiciousEffectHolder.tryGet(itemStack.getItem());
                        if (suspiciousStewIngredient != null) {
                            suspiciousStewIngredient.getSuspiciousEffects().effects().stream()
                                    .map(SuspiciousStewEffects.Entry::createEffectInstance).forEach(mobEffects::add);
                        }
                    }
                    if (itemStack.has(DataComponents.CONSUMABLE) && !itemStack.get(DataComponents.CONSUMABLE).onConsumeEffects().isEmpty()) {
                        for (ConsumeEffect consumeEffect : itemStack.get(DataComponents.CONSUMABLE).onConsumeEffects()) {
                            if (consumeEffect instanceof ApplyStatusEffectsConsumeEffect(List<MobEffectInstance> effects, float probability)) {
                                if (random.nextFloat() < probability) mobEffects.addAll(effects);
                            } else if (consumeEffect instanceof HealConsumeEffect(float amount)) {
                                healStack += amount;
                            } else consumeEffects.add(consumeEffect);
                        }
                    }
                    if (itemStack.has(MintyBlendsComponents.BLEND_EFFECT_DURATION_MODIFIER)) {
                        durationModifier *= itemStack.getOrDefault(MintyBlendsComponents.BLEND_EFFECT_DURATION_MODIFIER, 1f);
                    }
                }
            }
        }
        if (healStack > 0) consumeEffects.add(new HealConsumeEffect(healStack));
        if (!mobEffects.isEmpty()) stackEffects(mobEffects, appliedMobEffects, durationModifier);
        censer.set(MintyBlendsComponents.CENSER, new CenserComponent(maxUses ,censer.get(MintyBlendsComponents.CENSER).range(), List.copyOf(appliedMobEffects), ingredients, List.copyOf(consumeEffects)));
        return censer;
    }

    public static void stackEffects(List<MobEffectInstance> mobEffects, Set<MobEffectInstance> appliedMobEffects, float durationModifier) {
        Map<Holder<MobEffect>, Boolean> duplicateMap = new HashMap<>();
        for (MobEffectInstance effectInstance : mobEffects) {
            duplicateMap.put(effectInstance.getEffect(), duplicateMap.get(effectInstance.getEffect()) != null);
            if (!duplicateMap.get(effectInstance.getEffect())) {
                appliedMobEffects.add(new MobEffectInstance(effectInstance.getEffect(), Mth.floor(effectInstance.getDuration() * durationModifier), effectInstance.getAmplifier()));
            } else {
                int durationStack = 0;
                int amplifier = 0;
                for (Map.Entry<Holder<MobEffect>, Boolean> duplicate : duplicateMap.entrySet()) {
                    if (duplicate.getValue()) {
                        List<MobEffectInstance> effectsToStack = mobEffects.stream()
                                .filter(duplicateEffect -> duplicateEffect.getEffect().equals(duplicate.getKey())).toList();
                        for (MobEffectInstance stackingEffect : effectsToStack) {
                            durationStack += stackingEffect.getDuration();
                            if (amplifier <= stackingEffect.getAmplifier()) {
                                amplifier = stackingEffect.getAmplifier();
                            }
                        }
                        MobEffectInstance stackedEffect = new MobEffectInstance(duplicate.getKey(), Mth.floor(durationStack * durationModifier), amplifier);
                        appliedMobEffects.removeIf(initialEffect -> initialEffect.getEffect().equals(duplicate.getKey()));
                        appliedMobEffects.add(stackedEffect);
                        durationStack = 0;
                        amplifier = 0;
                    }
                }
            }
        }
    }

    public static List<ItemStack> recipeRemainders(KettleBrewingRecipeInput recipeInput) {
        List<ItemStack> recipeRemainders = new ArrayList<>();
        for (int i = 0; i < recipeInput.size(); i++) {
            Item item = recipeInput.getItem(i).getItem();
            if (item.getCraftingRemainder() != null) recipeRemainders.add(item.getCraftingRemainder().create());
        }
        return recipeRemainders;
    }
}
