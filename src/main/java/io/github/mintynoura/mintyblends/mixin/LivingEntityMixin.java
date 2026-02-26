package io.github.mintynoura.mintyblends.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.registry.ModItems;
import io.github.mintynoura.mintyblends.registry.ModStatusEffects;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract boolean hasEffect(Holder<MobEffect> effect);


    @Shadow public abstract @Nullable MobEffectInstance getEffect(Holder<MobEffect> effect);

    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "getDamageAfterMagicAbsorb", at = @At("HEAD"), cancellable = true)
    private void mintyBlends$addRendingDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (this.hasEffect(ModStatusEffects.RENDING) && !source.is(DamageTypeTags.BYPASSES_EFFECTS)) {
            float rendingModifier = 1 + (this.getEffect(ModStatusEffects.RENDING).getAmplifier() + 1) * MintyBlends.CONFIG.statusEffectSection.rendingDamageModifier.value();
            cir.setReturnValue(amount * rendingModifier);
        }
    }

    @ModifyReturnValue(method = "getVisibilityPercent", at = @At("RETURN"))
    private double mintyBlends$modifyStealthDetection(double original) {
        return this.hasEffect(ModStatusEffects.STEALTH) ? original * (1 - (this.getEffect(ModStatusEffects.STEALTH).getAmplifier() + 1) * MintyBlends.CONFIG.statusEffectSection.stealthRangeModifier.value()) : original;
    }

    @Inject(method = "dropFromLootTable(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;Z)V", at = @At("TAIL"), cancellable = true)
    private void mintyBlends$addMintDrop(ServerLevel world, DamageSource damageSource, boolean causedByPlayer, CallbackInfo ci) {
        if (((LivingEntity)(Object) this) instanceof ServerPlayer) {
            if (this.getName().getString().matches("mintynoura")) {
                this.spawnAtLocation(world, new ItemStack(ModItems.MINT_LEAVES, 2));
            }
        }
    }
}
