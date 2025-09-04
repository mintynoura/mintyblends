package io.github.mintynoura.mintyblends.recipe;

import io.github.mintynoura.mintyblends.item.CenserItem;
import io.github.mintynoura.mintyblends.item.component.CenserComponent;
import io.github.mintynoura.mintyblends.registry.ModComponents;
import io.github.mintynoura.mintyblends.registry.ModRecipes;
import io.github.mintynoura.mintyblends.util.ModTags;
import net.minecraft.block.SuspiciousStewIngredient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.*;

public class CenserBlendRecipe extends SpecialCraftingRecipe {
    public CenserBlendRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        ItemStack itemStack;
        boolean hasCenser = false;
        boolean hasIngredients = false;
        if (input.getStackCount() < 2 || input.getStackCount() > 5) {
            return false;
        } else {
            for (int i = 0; i < input.size(); i++) {
                itemStack = input.getStackInSlot(i);
                if (!itemStack.isEmpty()) {
                    if (itemStack.isIn(ModTags.Items.CENSERS) && itemStack.getItem() instanceof CenserItem) {
                        if (hasCenser) {
                            return false;
                        }
                        hasCenser = true;
                    } else if (!itemStack.isIn(ModTags.Items.BLENDING_INGREDIENTS)) {
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
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        ItemStack itemStack;
        ItemStack censer = ItemStack.EMPTY;
        Set<StatusEffectInstance> statusEffectSet = new HashSet<>();
        Set<Identifier> herbalEffectSet = new HashSet<>();
        Set<String> ingredientSet = new HashSet<>();
        List<ConsumeEffect> consumeEffects = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            itemStack = input.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                if (itemStack.isIn(ModTags.Items.CENSERS) && itemStack.getItem() instanceof CenserItem) {
                    censer = new ItemStack(itemStack.getItem());
                } else if (itemStack.isIn(ModTags.Items.BLENDING_INGREDIENTS)) {
                    if (itemStack.getItem() != Items.AIR) {
                        ingredientSet.add(itemStack.getItem().getTranslationKey());
                    }
                    if (itemStack.isIn(ItemTags.SMALL_FLOWERS) || itemStack.isIn(ModTags.Items.HERBS)) {
                        SuspiciousStewIngredient suspiciousStewIngredient = SuspiciousStewIngredient.of(itemStack.getItem());
                        if (suspiciousStewIngredient != null) {
                            StatusEffectInstance statusEffect = new StatusEffectInstance(suspiciousStewIngredient.getStewEffects().effects().getFirst().createStatusEffectInstance());
                            statusEffectSet.add(statusEffect);
                        }
                    }
                    if (itemStack.contains(ModComponents.HERB_COMPONENT)) {
                        Identifier herbalEffect = itemStack.get(ModComponents.HERB_COMPONENT).herbalEffect();
                        herbalEffectSet.add(herbalEffect);
                    }
                    if (itemStack.contains(DataComponentTypes.CONSUMABLE)) {
                        consumeEffects.addAll(itemStack.get(DataComponentTypes.CONSUMABLE).onConsumeEffects());
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
