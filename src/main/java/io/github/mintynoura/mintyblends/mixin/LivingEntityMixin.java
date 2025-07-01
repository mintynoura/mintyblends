package io.github.mintynoura.mintyblends.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.mintynoura.mintyblends.registry.ModStatusEffects;
import io.github.mintynoura.mintyblends.status_effect.MintyStatusEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);


    @Shadow public abstract @Nullable StatusEffectInstance getStatusEffect(RegistryEntry<StatusEffect> effect);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "modifyAppliedDamage", at = @At("HEAD"), cancellable = true)
    private void mintyBlends$addRendingDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (this.hasStatusEffect(ModStatusEffects.RENDING) && !source.isIn(DamageTypeTags.BYPASSES_EFFECTS)) {
            float rendingModifier = 1 + (this.getStatusEffect(ModStatusEffects.RENDING).getAmplifier() + 1) * MintyStatusEffect.rendingDamageModifier;
            cir.setReturnValue(amount * rendingModifier);
        }
    }

    @ModifyReturnValue(method = "getAttackDistanceScalingFactor", at = @At("RETURN"))
    private double mintyBlends$modifyStealthDetection(double original) {
        return this.hasStatusEffect(ModStatusEffects.STEALTH) ? original * (1 - (this.getStatusEffect(ModStatusEffects.STEALTH).getAmplifier() + 1) * MintyStatusEffect.stealthRangeModifier) : original;
    }
}
