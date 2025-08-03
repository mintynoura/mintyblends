package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticleTypes {
    public static final SimpleParticleType KETTLE_STEAM = Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MintyBlends.MOD_ID, "kettle_steam"), FabricParticleTypes.simple());

    public static void registerParticleTypes() {}
}
