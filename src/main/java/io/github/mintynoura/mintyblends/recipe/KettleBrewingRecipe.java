package io.github.mintynoura.mintyblends.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.mintynoura.mintyblends.registry.ModRecipes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class KettleBrewingRecipe implements Recipe<KettleBrewingRecipeInput> {

    private final List<Ingredient> ingredients;
    private final ItemStack result;
    private final ItemStack container;
    private final int brewingTime;
    @Nullable
    private IngredientPlacement ingredientPlacement;

    public static final ItemStack defaultContainer = PotionContentsComponent.createStack(Items.POTION, Potions.WATER);
    public static final int defaultBrewingTime = 200;

    public KettleBrewingRecipe(List<Ingredient> ingredients, ItemStack result, ItemStack container, int brewingTime) {
        this.ingredients = ingredients;
        this.result = result;
        this.container = container;
        this.brewingTime = brewingTime;
    }

    public ItemStack getContainer() {
        return container;
    }

    public int getBrewingTime() {
        return brewingTime;
    }

    @Override
    public boolean matches(KettleBrewingRecipeInput recipeInput, World world) {
        if (recipeInput.getStackCount() != this.ingredients.size()) {
            return false;
        } else {
            return recipeInput.size() == 1 && this.ingredients.size() == 1
                    ? this.ingredients.getFirst().test(recipeInput.getStackInSlot(0))
                    : recipeInput.getRecipeMatcher().isCraftable(this, null);
        }
    }
    @Override
    public ItemStack craft(KettleBrewingRecipeInput input, RegistryWrapper.WrapperLookup registries) {
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
    public IngredientPlacement getIngredientPlacement() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = IngredientPlacement.forShapeless(this.ingredients);
        }
        return this.ingredientPlacement;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
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
        public static final PacketCodec<RegistryByteBuf, KettleBrewingRecipe> PACKET_CODEC = PacketCodec.tuple(
                Ingredient.PACKET_CODEC.collect(PacketCodecs.toList()),
                recipe -> recipe.ingredients,
                ItemStack.PACKET_CODEC,
                recipe -> recipe.result,
                ItemStack.PACKET_CODEC,
                recipe -> recipe.container,
                PacketCodecs.INTEGER,
                recipe -> recipe.brewingTime,
                KettleBrewingRecipe::new
        );


        @Override
        public MapCodec<KettleBrewingRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, KettleBrewingRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
