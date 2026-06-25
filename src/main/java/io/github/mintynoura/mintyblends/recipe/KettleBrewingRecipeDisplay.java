package io.github.mintynoura.mintyblends.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.List;

public record KettleBrewingRecipeDisplay(List<SlotDisplay> ingredients, SlotDisplay container, SlotDisplay result, SlotDisplay craftingStation, int brewingTime) implements RecipeDisplay {
    public static final MapCodec<KettleBrewingRecipeDisplay> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            SlotDisplay.CODEC.listOf().fieldOf("ingredients").forGetter(KettleBrewingRecipeDisplay::ingredients),
            SlotDisplay.CODEC.fieldOf("container").forGetter(KettleBrewingRecipeDisplay::container),
            SlotDisplay.CODEC.fieldOf("result").forGetter(KettleBrewingRecipeDisplay::result),
            SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(KettleBrewingRecipeDisplay::craftingStation),
            Codec.INT.fieldOf("brewing_time").forGetter(KettleBrewingRecipeDisplay::brewingTime)
    ).apply(i, KettleBrewingRecipeDisplay::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, KettleBrewingRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
            SlotDisplay.STREAM_CODEC.apply(ByteBufCodecs.list()),
            KettleBrewingRecipeDisplay::ingredients,
            SlotDisplay.STREAM_CODEC,
            KettleBrewingRecipeDisplay::container,
            SlotDisplay.STREAM_CODEC,
            KettleBrewingRecipeDisplay::result,
            SlotDisplay.STREAM_CODEC,
            KettleBrewingRecipeDisplay::craftingStation,
            ByteBufCodecs.VAR_INT,
            KettleBrewingRecipeDisplay::brewingTime,
            KettleBrewingRecipeDisplay::new
    );

    public static final RecipeDisplay.Type<KettleBrewingRecipeDisplay> TYPE = new Type<>(CODEC, STREAM_CODEC);

    @Override
    public Type<? extends RecipeDisplay> type() {
        return TYPE;
    }

    @Override
    public boolean isEnabled(FeatureFlagSet enabledFeatures) {
        return this.ingredients.stream().allMatch(e -> e.isEnabled(enabledFeatures)) && RecipeDisplay.super.isEnabled(enabledFeatures);
    }
}
