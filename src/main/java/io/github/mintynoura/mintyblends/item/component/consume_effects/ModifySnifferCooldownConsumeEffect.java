package io.github.mintynoura.mintyblends.item.component.consume_effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.mintynoura.mintyblends.registry.MintyBlendsConsumeEffects;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

import java.util.Optional;

public record ModifySnifferCooldownConsumeEffect(float modifier, Optional<ParticleOptions> particle) implements ConsumeEffect {
    public static final MapCodec<ModifySnifferCooldownConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ExtraCodecs.NON_NEGATIVE_FLOAT.optionalFieldOf("modifier", 0.5f).forGetter(ModifySnifferCooldownConsumeEffect::modifier),
            ParticleTypes.CODEC.optionalFieldOf("particle").forGetter(ModifySnifferCooldownConsumeEffect::particle)
    ).apply(i, ModifySnifferCooldownConsumeEffect::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ModifySnifferCooldownConsumeEffect> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            ModifySnifferCooldownConsumeEffect::modifier,
            ParticleTypes.STREAM_CODEC.apply(ByteBufCodecs::optional),
            ModifySnifferCooldownConsumeEffect::particle,
            ModifySnifferCooldownConsumeEffect::new
    );
    @Override
    public Type<? extends ConsumeEffect> getType() {
        return MintyBlendsConsumeEffects.MODIFY_SNIFFER_COOLDOWN;
    }

    @Override
    public boolean apply(Level level, ItemStack stack, LivingEntity user) {
        if (user instanceof Sniffer sniffer) {
            long cooldown = sniffer.getBrain().getTimeUntilExpiry(MemoryModuleType.SNIFF_COOLDOWN);
            if (cooldown > 0 && cooldown != Long.MAX_VALUE) {
                if (!sniffer.level().isClientSide()) {
                    sniffer.getBrain().setMemoryWithExpiry(MemoryModuleType.SNIFF_COOLDOWN, Unit.INSTANCE, (long) (cooldown * modifier));
                    particle.ifPresent(particleOptions -> ((ServerLevel) sniffer.level()).sendParticles(particleOptions, sniffer.getX(), sniffer.getRandomY() + 0.25, sniffer.getZ(), 16, 1, 0.25, 1, 0));
                }
                return true;
            } else return false;
        } else return false;
    }
}
