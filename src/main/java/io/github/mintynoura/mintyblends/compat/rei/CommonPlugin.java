package io.github.mintynoura.mintyblends.compat.rei;

import io.github.mintynoura.mintyblends.MintyBlends;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;

public class CommonPlugin implements REICommonPlugin {
    public static final CategoryIdentifier<KettleBrewingDisplay> KETTLE_BREWING_CATEGORY_ID = CategoryIdentifier.of(MintyBlends.MOD_ID, "kettle_brewing");

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        REICommonPlugin.super.registerDisplaySerializer(registry);
    }
}
