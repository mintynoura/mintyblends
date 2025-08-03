package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSoundEvents {
    public static final SoundEvent BLOCK_KETTLE_AMBIENT = register("block.kettle.ambient");
    public static final SoundEvent BLOCK_KETTLE_EXTINGUISH = register("block.kettle.extinguish");
    public static final SoundEvent ITEM_CENSER_BURN = register("item.censer.burn");

    public static SoundEvent register(String name) {
        return Registry.register(Registries.SOUND_EVENT, Identifier.of(MintyBlends.MOD_ID, name), SoundEvent.of(Identifier.of(MintyBlends.MOD_ID, name)));
    }

    public static void registerSoundEffects() {}
}
