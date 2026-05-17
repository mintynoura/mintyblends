package io.github.mintynoura.mintyblends.item.component.consume_effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.mintynoura.mintyblends.registry.MintyBlendsConsumeEffects;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

public record HealConsumeEffect(float amount) implements ConsumeEffect {
    public static final MapCodec<HealConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ExtraCodecs.NON_NEGATIVE_FLOAT.optionalFieldOf("amount", 2f).forGetter(HealConsumeEffect::amount)
    ).apply(i, HealConsumeEffect::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, HealConsumeEffect> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            HealConsumeEffect::amount,
            HealConsumeEffect::new
    );
    @Override
    public Type<? extends ConsumeEffect> getType() {
        return MintyBlendsConsumeEffects.HEAL;
    }

    @Override
    public boolean apply(Level level, ItemStack stack, LivingEntity user) {
        user.heal(amount);
        return true;
    }
}
