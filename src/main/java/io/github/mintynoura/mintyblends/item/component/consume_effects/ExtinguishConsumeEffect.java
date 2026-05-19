package io.github.mintynoura.mintyblends.item.component.consume_effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.mintynoura.mintyblends.registry.MintyBlendsConsumeEffects;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

import java.util.Optional;

public record ExtinguishConsumeEffect(Optional<ParticleOptions> particle, boolean particlesOnlyWhenExtinguished) implements ConsumeEffect {
    public static final MapCodec<ExtinguishConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ParticleTypes.CODEC.optionalFieldOf("particle").forGetter(ExtinguishConsumeEffect::particle),
            Codec.BOOL.optionalFieldOf("particles_only_when_extinguished", false).forGetter(ExtinguishConsumeEffect::particlesOnlyWhenExtinguished)
    ).apply(i, ExtinguishConsumeEffect::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ExtinguishConsumeEffect> STREAM_CODEC = StreamCodec.composite(
            ParticleTypes.STREAM_CODEC.apply(ByteBufCodecs::optional),
            ExtinguishConsumeEffect::particle,
            ByteBufCodecs.BOOL,
            ExtinguishConsumeEffect::particlesOnlyWhenExtinguished,
            ExtinguishConsumeEffect::new
    );
    @Override
    public Type<? extends ConsumeEffect> getType() {
        return MintyBlendsConsumeEffects.EXTINGUISH;
    }

    @Override
    public boolean apply(Level level, ItemStack stack, LivingEntity user) {
        if (level instanceof ServerLevel serverLevel) {
            if (!particlesOnlyWhenExtinguished || user.isOnFire()) {
                particle.ifPresent(particleOptions -> serverLevel.sendParticles(particleOptions, user.getX(), user.getY(0.5), user.getZ(), 5, 0.25, 0.25, 0.25, 0));
            }
            user.extinguishFire();
        }
        return true;
    }
}
