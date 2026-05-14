package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.item.component.CenserComponent;
import io.github.mintynoura.mintyblends.item.component.HerbComponent;
import io.github.mintynoura.mintyblends.item.component.HerbalBrewComponent;
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class MintyBlendsComponents {
    public static final DataComponentType<HerbalBrewComponent> HERBAL_BREW_COMPONENT = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "herbal_brew"), DataComponentType.<HerbalBrewComponent>builder().persistent(HerbalBrewComponent.CODEC).build());
    public static final DataComponentType<CenserComponent> CENSER_COMPONENT = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "censer_contents"), DataComponentType.<CenserComponent>builder().persistent(CenserComponent.CODEC).build());
    public static final DataComponentType<HerbComponent> HERB_COMPONENT = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "herb"), DataComponentType.<HerbComponent>builder().persistent(HerbComponent.CODEC).build());

    public static void registerComponents() {
        ItemComponentTooltipProviderRegistry.addFirst(HERBAL_BREW_COMPONENT);
        ItemComponentTooltipProviderRegistry.addFirst(CENSER_COMPONENT);
    }
}
