package io.github.mintynoura.mintyblends.recipe;

import io.github.mintynoura.mintyblends.item.CenserItem;
import io.github.mintynoura.mintyblends.item.component.CenserComponent;
import io.github.mintynoura.mintyblends.registry.ModComponents;
import io.github.mintynoura.mintyblends.registry.ModRecipes;
import io.github.mintynoura.mintyblends.util.ModTags;
import java.util.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SuspiciousEffectHolder;

public class CenserBlendRecipe extends CustomRecipe {
    public CenserBlendRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level world) {
        ItemStack itemStack;
        boolean hasCenser = false;
        boolean hasIngredients = false;
        if (input.ingredientCount() < 2 || input.ingredientCount() > 5) {
            return false;
        } else {
            for (int i = 0; i < input.size(); i++) {
                itemStack = input.getItem(i);
                if (!itemStack.isEmpty()) {
                    if (itemStack.is(ModTags.Items.CENSERS) && itemStack.getItem() instanceof CenserItem) {
                        if (hasCenser) {
                            return false;
                        }
                        hasCenser = true;
                    } else if (!itemStack.is(ModTags.Items.BLENDING_INGREDIENTS)) {
                        return false;
                    } else {
                        hasIngredients = true;
                    }
                }
            }
            return hasCenser && hasIngredients;
        }
    }

    @Override
    public ItemStack craft(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack itemStack;
        ItemStack censer = ItemStack.EMPTY;
        Set<MobEffectInstance> statusEffectSet = new HashSet<>();
        Set<Identifier> herbalEffectSet = new HashSet<>();
        Set<String> ingredientSet = new HashSet<>();
        List<ConsumeEffect> consumeEffects = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            itemStack = input.getItem(i);
            if (!itemStack.isEmpty()) {
                if (itemStack.is(ModTags.Items.CENSERS) && itemStack.getItem() instanceof CenserItem) {
                    censer = new ItemStack(itemStack.getItem());
                } else if (itemStack.is(ModTags.Items.BLENDING_INGREDIENTS)) {
                    if (itemStack.getItem() != Items.AIR) {
                        ingredientSet.add(itemStack.getItem().getDescriptionId());
                    }
                    if (itemStack.is(ItemTags.SMALL_FLOWERS) || itemStack.is(ModTags.Items.HERBS)) {
                        SuspiciousEffectHolder suspiciousStewIngredient = SuspiciousEffectHolder.tryGet(itemStack.getItem());
                        if (suspiciousStewIngredient != null) {
                            MobEffectInstance statusEffect = new MobEffectInstance(suspiciousStewIngredient.getSuspiciousEffects().effects().getFirst().createEffectInstance());
                            statusEffectSet.add(statusEffect);
                        }
                    }
                    if (itemStack.has(ModComponents.HERB_COMPONENT)) {
                        Identifier herbalEffect = itemStack.get(ModComponents.HERB_COMPONENT).herbalEffect();
                        herbalEffectSet.add(herbalEffect);
                    }
                    if (itemStack.has(DataComponents.CONSUMABLE)) {
                        consumeEffects.addAll(itemStack.get(DataComponents.CONSUMABLE).onConsumeEffects());
                    }
                }
            }
        }
        CenserComponent censerComponent = new CenserComponent(censer.get(ModComponents.CENSER_COMPONENT).range(), List.copyOf(herbalEffectSet), List.copyOf(statusEffectSet), List.copyOf(ingredientSet), List.copyOf(consumeEffects));
        censer.set(ModComponents.CENSER_COMPONENT, censerComponent) ;
        return censer;
    }

    @Override
    public RecipeSerializer<CenserBlendRecipe> getSerializer() {
        return ModRecipes.CENSER_BLEND_RECIPE_SERIALIZER;
    }

}
