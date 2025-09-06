package io.github.mintynoura.mintyblends.compat.rei;

import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class KettleBrewingDisplay extends BasicDisplay {
    public KettleBrewingDisplay(RecipeEntry<KettleBrewingRecipe> recipe) {
        this(EntryIngredients.ofIngredients(recipe.value().getIngredients()), Collections.singletonList(EntryIngredients.ofItemStacks(List.of(recipe.value().getResult()))), Optional.ofNullable(recipe.id().getValue()));
    }

    public KettleBrewingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location) {
        super(inputs, outputs, location);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return null;
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return null;
    }
}
