package io.github.mintynoura.mintyblends.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.mintynoura.mintyblends.registry.ModStatusEffects;
import io.github.mintynoura.mintyblends.status_effect.MintyStatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TrackTargetGoal.class)
public abstract class TrackTargetGoalMixin {

    @Shadow @Final protected MobEntity mob;

    @Shadow @Nullable protected LivingEntity target;

    @ModifyReturnValue(method = "getFollowRange", at = @At("RETURN"))
    private double mintyBlends$modifyStealthRange(double original) {
        if (this.mob != null && this.target != null) {
            return this.target.hasStatusEffect(ModStatusEffects.STEALTH) ? original * (1 - (this.target.getStatusEffect(ModStatusEffects.STEALTH).getAmplifier() + 1) * MintyStatusEffect.stealthRangeModifier) : original;
        } else return original;
    }
}
