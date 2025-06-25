package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.item.component.HerbalBrewComponent;
import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModComponents {
    public static final ComponentType<HerbalBrewComponent> HERBAL_BREW_COMPONENT = Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MintyBlends.MOD_ID, "herbal_brew"), ComponentType.<HerbalBrewComponent>builder().codec(HerbalBrewComponent.CODEC).build());

    public static void registerComponents() {
        ComponentTooltipAppenderRegistry.addFirst(HERBAL_BREW_COMPONENT);
    }
}
