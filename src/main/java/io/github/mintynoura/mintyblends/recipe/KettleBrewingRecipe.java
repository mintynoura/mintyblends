package io.github.mintynoura.mintyblends.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.mintynoura.mintyblends.registry.MintyBlendsRecipes;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

@SuppressWarnings("deprecation")
public class KettleBrewingRecipe implements Recipe<KettleBrewingRecipeInput> {

    private final List<Ingredient> ingredients;
    private final ItemStackTemplate result;
    private final ItemStackTemplate container;
    private final int brewingTime;
    @Nullable
    private PlacementInfo placementInfo;

    public static final ItemStackTemplate defaultContainer = new ItemStackTemplate(Items.POTION.builtInRegistryHolder(), 1, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER)).build());
    public static final int defaultBrewingTime = 400;

    public KettleBrewingRecipe(List<Ingredient> ingredients, ItemStackTemplate result, ItemStackTemplate container, int brewingTime) {
        this.ingredients = ingredients;
        this.result = result;
        this.container = container;
        this.brewingTime = brewingTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public ItemStackTemplate getResult() {
        return result;
    }

    public ItemStackTemplate getContainer() {
        return container;
    }

    public int getBrewingTime() {
        return brewingTime;
    }

    @Override
    public boolean matches(KettleBrewingRecipeInput recipeInput, @NonNull Level world) {
        if (recipeInput.getStackCount() != this.ingredients.size()) {
            return false;
        } else {
            return recipeInput.size() == 1 && this.ingredients.size() == 1
                    ? this.ingredients.getFirst().test(recipeInput.getItem(0))
                    : recipeInput.getRecipeMatcher().canCraft(this, null);
        }
    }
    @Override
    public @NonNull ItemStack assemble(@NonNull KettleBrewingRecipeInput input) {
        return this.result.create();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public @NonNull String group() {
        return "";
    }

    @Override
    public @NonNull RecipeSerializer<? extends Recipe<KettleBrewingRecipeInput>> getSerializer() {
        return MintyBlendsRecipes.KETTLE_BREWING_RECIPE_SERIALIZER;
    }

    @Override
    public @NonNull RecipeType<? extends Recipe<KettleBrewingRecipeInput>> getType() {
        return MintyBlendsRecipes.KETTLE_BREWING_RECIPE_TYPE;
    }

    @Override
    public @NonNull PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.create(this.ingredients);
        }
        return this.placementInfo;
    }

    @Override
    public @Nullable RecipeBookCategory recipeBookCategory() {
        return null;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer {
        private static final MapCodec<KettleBrewingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.CODEC.listOf(1, 4).fieldOf("ingredients").forGetter(recipe -> recipe.ingredients),
                                ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                                ItemStackTemplate.CODEC.optionalFieldOf("container", defaultContainer).forGetter(recipe -> recipe.container),
                                ExtraCodecs.POSITIVE_INT.optionalFieldOf("brewing_time", defaultBrewingTime).forGetter(recipe -> recipe.brewingTime)
                        )
                        .apply(instance, KettleBrewingRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, KettleBrewingRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
                recipe -> recipe.ingredients,
                ItemStackTemplate.STREAM_CODEC,
                recipe -> recipe.result,
                ItemStackTemplate.STREAM_CODEC,
                recipe -> recipe.container,
                ByteBufCodecs.INT,
                recipe -> recipe.brewingTime,
                KettleBrewingRecipe::new
        );
    }
    public static final RecipeSerializer<KettleBrewingRecipe> SERIALIZER = new RecipeSerializer<>(Serializer.CODEC, Serializer.STREAM_CODEC);
}
