package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
    public static final SoundEvent BLOCK_KETTLE_AMBIENT = register("block.kettle.ambient");
    public static final SoundEvent BLOCK_KETTLE_EXTINGUISH = register("block.kettle.extinguish");
    public static final SoundEvent ITEM_CENSER_BURN = register("item.censer.burn");

    public static SoundEvent register(String name) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, name), SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, name)));
    }

    public static void registerSoundEffects() {}
}
