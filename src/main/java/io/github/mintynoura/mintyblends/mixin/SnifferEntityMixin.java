package io.github.mintynoura.mintyblends.mixin;

import io.github.mintynoura.mintyblends.registry.ModComponents;
import io.github.mintynoura.mintyblends.util.HerbalEffectType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Unit;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnifferEntity.class)
public abstract class SnifferEntityMixin extends AnimalEntity {
    @Shadow protected abstract SnifferEntity.State getState();

    protected SnifferEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void mintyBlends$catnipInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (player.getStackInHand(hand).contains(ModComponents.HERB_COMPONENT) && (this.getState() == SnifferEntity.State.IDLING || this.getState() == SnifferEntity.State.SCENTING || this.getState() == SnifferEntity.State.SNIFFING)) {
            if (player.getStackInHand(hand).get(ModComponents.HERB_COMPONENT).herbalEffect().equals(HerbalEffectType.LOWER_SNIFFER_COOLDOWN)) {
                long cooldown = this.getBrain().getMemoryExpiry(MemoryModuleType.SNIFF_COOLDOWN);
                if (cooldown > 0) {
                    if (!this.getWorld().isClient) {
                        this.getBrain().remember(MemoryModuleType.SNIFF_COOLDOWN, Unit.INSTANCE, cooldown / 2);
                        ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getRandomBodyY() + 0.25, this.getZ(), 16, 1, 0.25, 1, 0);
                        this.playEatSound();
                        this.eat(player, hand, player.getStackInHand(hand));
                        player.incrementStat(Stats.USED.getOrCreateStat(player.getStackInHand(hand).getItem()));
                    }
                }
                this.playSound(SoundEvents.ENTITY_SNIFFER_HAPPY, 1.0F, 1.0F);
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
