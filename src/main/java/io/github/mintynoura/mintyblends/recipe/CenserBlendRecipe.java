package io.github.mintynoura.mintyblends.recipe;

import com.mojang.serialization.MapCodec;
import io.github.mintynoura.mintyblends.item.CenserItem;
import io.github.mintynoura.mintyblends.item.component.CenserComponent;
import io.github.mintynoura.mintyblends.registry.ModComponents;
import io.github.mintynoura.mintyblends.registry.ModRecipes;
import io.github.mintynoura.mintyblends.util.ModTags;
import java.util.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import org.jspecify.annotations.NonNull;

public class CenserBlendRecipe extends CustomRecipe {
    public static final CenserBlendRecipe INSTANCE = new CenserBlendRecipe();
    public static final MapCodec<CenserBlendRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, CenserBlendRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public boolean matches(CraftingInput input, Level level) {
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
    public @NonNull ItemStack assemble(CraftingInput input) {
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

    public static final RecipeSerializer<CenserBlendRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public @NonNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipes.CENSER_BLEND_RECIPE_SERIALIZER;
    }
}
