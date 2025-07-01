package io.github.mintynoura.mintyblends.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.util.HerbalEffectType;
import net.minecraft.component.type.Consumable;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public record HerbComponent(Identifier herbalEffect) implements Consumable {
    public static final Codec<HerbComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Identifier.CODEC.optionalFieldOf("herbal_effect", Identifier.of(MintyBlends.MOD_ID, "empty")).forGetter(HerbComponent::herbalEffect)
    ).apply(builder, HerbComponent::new));

    @Override
    public void onConsume(World world, LivingEntity user, ItemStack stack, ConsumableComponent consumable) {
            HerbalEffectType.applyHerb(user, herbalEffect);
    }
}
