//package io.github.mintynoura.mintyblends.item.component;
//
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import io.github.mintynoura.mintyblends.MintyBlends;
//import io.github.mintynoura.mintyblends.util.HerbalEffectType;
//import net.minecraft.resources.Identifier;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.component.Consumable;
//import net.minecraft.world.item.component.ConsumableListener;
//import net.minecraft.world.level.Level;
//
//public record HerbComponent(Identifier herbalEffect) implements ConsumableListener {
//
//    public static final Codec<HerbComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
//            Identifier.CODEC.optionalFieldOf("herbal_effect", Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "empty")).forGetter(HerbComponent::herbalEffect)
//    ).apply(builder, HerbComponent::new));
//
//    @Override
//    public void onConsume(Level world, LivingEntity user, ItemStack stack, Consumable consumable) {
//            HerbalEffectType.applyHerb(user, herbalEffect);
//    }
//}
