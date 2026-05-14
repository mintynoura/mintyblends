package io.github.mintynoura.mintyblends.util;

import io.github.mintynoura.mintyblends.item.component.HerbalBrewComponent;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipeInput;
import io.github.mintynoura.mintyblends.registry.MintyBlendsComponents;
import io.github.mintynoura.mintyblends.registry.MintyBlendsItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.block.SuspiciousEffectHolder;

import java.util.ArrayList;
import java.util.List;

public class BlendingHelper {
    public static ItemStack blendBrew(KettleBrewingRecipeInput recipeInput) {
        List<MobEffectInstance> mobEffects = new ArrayList<>();
        List<Identifier> herbalEffects = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();
        List<ConsumeEffect> consumeEffects = new ArrayList<>();
        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack itemStack = recipeInput.getItem(i);
            if (!itemStack.isEmpty()) {
                if (itemStack.is(ModTags.Items.BLENDING_INGREDIENTS)) {
                    if (!itemStack.isEmpty()) {
                        ingredients.add(itemStack.getItem().getDescriptionId());
                    }
                    if (itemStack.is(ItemTags.SMALL_FLOWERS) || itemStack.is(ModTags.Items.HERBS)) {
                        SuspiciousEffectHolder suspiciousStewIngredient = SuspiciousEffectHolder.tryGet(itemStack.getItem());
                        if (suspiciousStewIngredient != null) {
                            // TODO: effect stacking logic
                            int durationStack = 1;
                            for (SuspiciousStewEffects.Entry entry : suspiciousStewIngredient.getSuspiciousEffects().effects()) {
                                MobEffectInstance statusEffect = new MobEffectInstance(entry.createEffectInstance());
                                if (mobEffects.contains(statusEffect)) {
                                    durationStack += 1;
                                    mobEffects.remove(statusEffect);
                                    mobEffects.add(new MobEffectInstance(statusEffect.getEffect(), statusEffect.getDuration() * durationStack, statusEffect.getAmplifier()));
                                }
                                mobEffects.add(statusEffect);
                            }
                        }
                    }
                    if (itemStack.has(MintyBlendsComponents.HERB_COMPONENT)) {
                        Identifier herbalEffect = itemStack.get(MintyBlendsComponents.HERB_COMPONENT).herbalEffect();
                        herbalEffects.add(herbalEffect);
                    }
                    if (itemStack.has(DataComponents.CONSUMABLE)) {
                        consumeEffects.addAll(itemStack.get(DataComponents.CONSUMABLE).onConsumeEffects());
                    }
                }
            }
        }
        HerbalBrewComponent herbalBrewComponent = new HerbalBrewComponent(herbalEffects, mobEffects, ingredients);
        Consumable consumableComponent = new Consumable(1.6f, ItemUseAnimation.DRINK, SoundEvents.GENERIC_DRINK, false, consumeEffects);
        ItemStack herbalBrew = new ItemStack(MintyBlendsItems.HERBAL_BREW);
        herbalBrew.set(MintyBlendsComponents.HERBAL_BREW_COMPONENT, herbalBrewComponent);
        herbalBrew.set(DataComponents.CONSUMABLE, consumableComponent);
        return herbalBrew;
    }

    public static List<ItemStack> blendRemainders(KettleBrewingRecipeInput recipeInput) {
        List<ItemStack> recipeRemainders = new ArrayList<>();
        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack itemStack = recipeInput.getItem(i);
            Item item = itemStack.getItem();
            if (item.getCraftingRemainder(itemStack) != null) recipeRemainders.add(item.getCraftingRemainder(itemStack).create());
        }
        return recipeRemainders;
    }
}
