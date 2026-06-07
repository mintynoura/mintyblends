package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class MintyBlendsSoundEvents {
    public static final SoundEvent KETTLE_AMBIENT = register("block.kettle.ambient");
    public static final SoundEvent KETTLE_EXTINGUISH = register("block.kettle.extinguish");
    public static final SoundEvent CENSER_BURN = register("item.censer.burn");
    public static final SoundEvent BREW_CONVERT_EFFECT = register("item.brew.convert_effect");

    public static SoundEvent register(String name) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, Identifier.fromNamespaceAndPath(MintyBlends.ID, name), SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(MintyBlends.ID, name)));
    }

    public static void initialize() {}
}
