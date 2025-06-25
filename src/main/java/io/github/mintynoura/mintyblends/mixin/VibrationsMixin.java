package io.github.mintynoura.mintyblends.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.mintynoura.mintyblends.registry.ModStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.Vibrations;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

@Mixin(Vibrations.VibrationListener.class)
public abstract class VibrationsMixin {
    @Shadow
    @Final
    private Vibrations receiver;

    @WrapMethod(
            method = "listen(" +
                    "Lnet/minecraft/server/world/ServerWorld;" +
                    "Lnet/minecraft/registry/entry/RegistryEntry;" +
                    "Lnet/minecraft/world/event/GameEvent$Emitter;" +
                    "Lnet/minecraft/util/math/Vec3d;" +
                    ")Z"
    )
    private boolean mintyBlends$distanceShenanigans(ServerWorld world, RegistryEntry<GameEvent> event, GameEvent.Emitter emitter, Vec3d emitterPos, Operation<Boolean> original) {
        Entity source = emitter.sourceEntity();
        if (!(source instanceof LivingEntity entity)) return original.call(world, event, emitter, emitterPos);

        Vibrations.Callback callback = receiver.getVibrationCallback();
        Optional<Vec3d> listenerPos = callback.getPositionSource().getPos(world);
        if (listenerPos.isEmpty()) return original.call(world, event, emitter, emitterPos);

        int listenerRange = callback.getRange();
        double offset = mintyBlends$getOffset(entity);
        double distance = emitterPos.distanceTo(listenerPos.get()) + offset;

        if (listenerRange < distance) {
            return false;
        }

        return original.call(world, event, emitter, emitterPos);
    }

    @Unique
    private double mintyBlends$getOffset(LivingEntity entity) {
        StatusEffectInstance effectInstance = entity.getStatusEffect(ModStatusEffects.STEALTH);
        if (effectInstance == null) return 0;

        return (effectInstance.getAmplifier() + 1) * 2;
    }
}