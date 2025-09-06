package io.github.mintynoura.mintyblends.compat.rei;

import io.github.mintynoura.mintyblends.registry.ModBlocks;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;

public class KettleBrewingDisplayCategory implements DisplayCategory<KettleBrewingDisplay> {


    @Override
    public CategoryIdentifier<? extends KettleBrewingDisplay> getCategoryIdentifier() {
        return CommonPlugin.KETTLE_BREWING_CATEGORY_ID;
    }

    @Override
    public Text getTitle() {
        return Text.translatableWithFallback("recipe.mintyblends.kettle_brewing", "Kettle Brewing");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.KETTLE.asItem());
    }
}
