package io.github.mintynoura.mintyblends.item.component.consume_effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.mintynoura.mintyblends.registry.MintyBlendsConsumeEffects;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public record ClearEffectsByCategoryConsumeEffect(Category category) implements ConsumeEffect {
    public static final MapCodec<ClearEffectsByCategoryConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Category.CODEC.optionalFieldOf("category", Category.HARMFUL).forGetter(ClearEffectsByCategoryConsumeEffect::category)
    ).apply(i, ClearEffectsByCategoryConsumeEffect::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClearEffectsByCategoryConsumeEffect> STREAM_CODEC = StreamCodec.composite(
            Category.STREAM_CODEC,
            ClearEffectsByCategoryConsumeEffect::category,
            ClearEffectsByCategoryConsumeEffect::new
    );
    @Override
    public Type<? extends ConsumeEffect> getType() {
        return MintyBlendsConsumeEffects.CLEAR_EFFECTS_BY_CATEGORY;
    }

    @Override
    public boolean apply(Level level, ItemStack stack, LivingEntity user) {
        boolean anyCleared = false;
        List<Holder<MobEffect>> effectsToClear = new ArrayList<>();
        switch (category) {
            case BENEFICIAL -> effectsToClear = filterEffects(user, MobEffectCategory.BENEFICIAL);
            case HARMFUL -> effectsToClear = filterEffects(user, MobEffectCategory.HARMFUL);
            case NEUTRAL -> effectsToClear = filterEffects(user, MobEffectCategory.NEUTRAL);
        }
        if (!effectsToClear.isEmpty()) {
            effectsToClear.forEach(user::removeEffect);
            anyCleared = true;
        }
        return anyCleared;
    }

    public List<Holder<MobEffect>> filterEffects(LivingEntity user, MobEffectCategory category) {
        return user.getActiveEffects().stream().map(MobEffectInstance::getEffect).filter(effect -> effect.value().getCategory().equals(category)).toList();
    }

    public enum Category implements StringRepresentable {
        BENEFICIAL("beneficial"),
        HARMFUL("harmful"),
        NEUTRAL("neutral");

        private final String name;

        Category(final String name) {
            this.name = name;
        }

        public static final Codec<Category> CODEC = StringRepresentable.fromEnum(Category::values);
        public static final StreamCodec<ByteBuf, Category> STREAM_CODEC = ByteBufCodecs.idMapper(ordinal -> Category.values()[ordinal], Enum::ordinal);

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
