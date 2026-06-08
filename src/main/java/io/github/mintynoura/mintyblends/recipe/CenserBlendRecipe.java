package io.github.mintynoura.mintyblends.recipe;

import com.mojang.serialization.MapCodec;
import io.github.mintynoura.mintyblends.item.CenserItem;
import io.github.mintynoura.mintyblends.registry.MintyBlendsRecipes;
import io.github.mintynoura.mintyblends.util.BlendUtils;
import io.github.mintynoura.mintyblends.util.MintyBlendsTags;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class CenserBlendRecipe extends CustomRecipe {
    public static final CenserBlendRecipe INSTANCE = new CenserBlendRecipe();
    public static final MapCodec<CenserBlendRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, CenserBlendRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public boolean matches(CraftingInput input, @NonNull Level level) {
        ItemStack itemStack;
        boolean hasCenser = false;
        boolean hasIngredients = false;
        if (input.ingredientCount() < 2 || input.ingredientCount() > 5) {
            return false;
        } else {
            for (int i = 0; i < input.size(); i++) {
                itemStack = input.getItem(i);
                if (!itemStack.isEmpty()) {
                    if (itemStack.is(MintyBlendsTags.Items.CENSERS) && itemStack.getItem() instanceof CenserItem) {
                        if (hasCenser) {
                            return false;
                        }
                        hasCenser = true;
                    } else if (!itemStack.is(MintyBlendsTags.Items.BLENDING_INGREDIENTS)) {
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
    public @NonNull ItemStack assemble(@NonNull CraftingInput input) {
        return BlendUtils.blendCenser(input, RandomSource.create());
    }

    public static final RecipeSerializer<CenserBlendRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public @NonNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return MintyBlendsRecipes.CENSER_BLEND_RECIPE_SERIALIZER;
    }
}
