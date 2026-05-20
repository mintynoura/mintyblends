package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.item.component.CenserComponent;
import io.github.mintynoura.mintyblends.item.component.HerbalBrewComponent;
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

import java.util.function.UnaryOperator;

public class MintyBlendsComponents {
    public static final DataComponentType<HerbalBrewComponent> HERBAL_BREW = register("herbal_brew",
            b -> b.persistent(HerbalBrewComponent.CODEC).networkSynchronized(HerbalBrewComponent.STREAM_CODEC));
    public static final DataComponentType<CenserComponent> CENSER = register("censer",
            b -> b.persistent(CenserComponent.CODEC).networkSynchronized(CenserComponent.STREAM_CODEC));
    public static final DataComponentType<Float> BLEND_EFFECT_DURATION_MODIFIER = register("blend_effect_duration_modifier",
        b -> b.persistent(ExtraCodecs.POSITIVE_FLOAT).networkSynchronized(ByteBufCodecs.FLOAT));

    public static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath(MintyBlends.ID, name), builder.apply(DataComponentType.builder()).build());
    }

    public static void initialize() {
        ItemComponentTooltipProviderRegistry.addFirst(HERBAL_BREW);
        ItemComponentTooltipProviderRegistry.addFirst(CENSER);
    }
}
