package io.github.mintynoura.mintyblends.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.mintynoura.mintyblends.registry.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.recipe.*;
import net.minecraft.world.item.ItemStack;
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

import java.util.List;

@SuppressWarnings("deprecation")
public class KettleBrewingRecipe implements Recipe<KettleBrewingRecipeInput> {

    private final List<Ingredient> ingredients;
    private final ItemStack result;
    private final ItemStack container;
    private final int brewingTime;
    @Nullable
    private PlacementInfo ingredientPlacement;

    public static final ItemStack defaultContainer = PotionContents.createItemStack(Items.POTION, Potions.WATER);
    public static final int defaultBrewingTime = 200;

    public KettleBrewingRecipe(List<Ingredient> ingredients, ItemStack result, ItemStack container, int brewingTime) {
        this.ingredients = ingredients;
        this.result = result;
        this.container = container;
        this.brewingTime = brewingTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public ItemStack getResult() {
        return result;
    }

    public ItemStack getContainer() {
        return container;
    }

    public int getBrewingTime() {
        return brewingTime;
    }

    @Override
    public boolean matches(KettleBrewingRecipeInput recipeInput, Level world) {
        if (recipeInput.getStackCount() != this.ingredients.size()) {
            return false;
        } else {
            return recipeInput.size() == 1 && this.ingredients.size() == 1
                    ? this.ingredients.getFirst().test(recipeInput.getItem(0))
                    : recipeInput.getRecipeMatcher().canCraft(this, null);
        }
    }
    @Override
    public ItemStack craft(KettleBrewingRecipeInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<KettleBrewingRecipeInput>> getSerializer() {
        return ModRecipes.KETTLE_BREWING_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<KettleBrewingRecipeInput>> getType() {
        return ModRecipes.KETTLE_BREWING_RECIPE_TYPE;
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = PlacementInfo.create(this.ingredients);
        }
        return this.ingredientPlacement;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    public static class Serializer implements RecipeSerializer<KettleBrewingRecipe> {
        private static final MapCodec<KettleBrewingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.CODEC.listOf(1, 4).fieldOf("ingredients").forGetter(recipe -> recipe.ingredients),
                                ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                                ItemStack.CODEC.fieldOf("container").orElse(defaultContainer).forGetter(recipe -> recipe.container),
                                Codec.INT.fieldOf("brewing_time").orElse(defaultBrewingTime).forGetter(recipe -> recipe.brewingTime)
                        )
                        .apply(instance, KettleBrewingRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, KettleBrewingRecipe> PACKET_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
                recipe -> recipe.ingredients,
                ItemStack.STREAM_CODEC,
                recipe -> recipe.result,
                ItemStack.STREAM_CODEC,
                recipe -> recipe.container,
                ByteBufCodecs.INT,
                recipe -> recipe.brewingTime,
                KettleBrewingRecipe::new
        );


        @Override
        public MapCodec<KettleBrewingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, KettleBrewingRecipe> streamCodec() {
            return PACKET_CODEC;
        }
    }
}
