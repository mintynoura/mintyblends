package io.github.mintynoura.mintyblends.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.registry.ModStatusEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TargetGoal.class)
public abstract class TrackTargetGoalMixin {

    @Shadow @Final protected Mob mob;

    @Shadow @Nullable protected LivingEntity targetMob;

    @ModifyReturnValue(method = "getFollowDistance", at = @At("RETURN"))
    private double mintyBlends$modifyStealthRange(double original) {
        if (this.mob != null && this.targetMob != null) {
            return this.targetMob.hasEffect(ModStatusEffects.STEALTH) ? original * (1 - (this.targetMob.getEffect(ModStatusEffects.STEALTH).getAmplifier() + 1) * MintyBlends.CONFIG.statusEffectSection.stealthRangeModifier.value()) : original;
        } else return original;
    }
}
