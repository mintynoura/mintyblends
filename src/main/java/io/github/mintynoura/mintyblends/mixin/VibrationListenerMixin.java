package io.github.mintynoura.mintyblends.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.mintynoura.mintyblends.registry.MintyBlendsStatusEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;

@Mixin(VibrationSystem.Listener.class)
public abstract class VibrationListenerMixin {
    @Shadow
    @Final
    private VibrationSystem system;

    @WrapMethod(
            method = "handleGameEvent(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/Holder;Lnet/minecraft/world/level/gameevent/GameEvent$Context;Lnet/minecraft/world/phys/Vec3;)Z"
    )
    private boolean mintyBlends$distanceShenanigans(ServerLevel level, Holder<GameEvent> event, GameEvent.Context context, Vec3 sourcePosition, Operation<Boolean> original) {
        Entity source = context.sourceEntity();
        if (!(source instanceof LivingEntity entity)) return original.call(level, event, context, sourcePosition);

        VibrationSystem.User callback = system.getVibrationUser();
        Optional<Vec3> listenerPos = callback.getPositionSource().getPosition(level);
        if (listenerPos.isEmpty()) return original.call(level, event, context, sourcePosition);

        int listenerRange = callback.getListenerRadius();
        double offset = mintyBlends$getOffset(entity);
        double distance = sourcePosition.distanceTo(listenerPos.get()) + offset;

        if (listenerRange < distance) {
            return false;
        }

        return original.call(level, event, context, sourcePosition);
    }

    @Unique
    private double mintyBlends$getOffset(LivingEntity entity) {
        MobEffectInstance effectInstance = entity.getEffect(MintyBlendsStatusEffects.STEALTH);
        if (effectInstance == null) return 0;

        return (effectInstance.getAmplifier() + 1) * 2;
    }
}