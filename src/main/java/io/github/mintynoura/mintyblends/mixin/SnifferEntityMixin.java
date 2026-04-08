package io.github.mintynoura.mintyblends.mixin;

import io.github.mintynoura.mintyblends.registry.ModComponents;
import io.github.mintynoura.mintyblends.util.HerbalEffectType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sniffer.class)
public abstract class SnifferEntityMixin extends Animal {
    @Shadow protected abstract Sniffer.State getState();

    protected SnifferEntityMixin(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void mintyBlends$catnipInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (player.getItemInHand(hand).has(ModComponents.HERB_COMPONENT) && (this.getState() == Sniffer.State.IDLING || this.getState() == Sniffer.State.SCENTING || this.getState() == Sniffer.State.SNIFFING) && this.getBrain().hasMemoryValue(MemoryModuleType.SNIFF_COOLDOWN)) {
            if (player.getItemInHand(hand).get(ModComponents.HERB_COMPONENT).herbalEffect().equals(HerbalEffectType.LOWER_SNIFFER_COOLDOWN)) {
                long cooldown = this.getBrain().getTimeUntilExpiry(MemoryModuleType.SNIFF_COOLDOWN);
                if (cooldown > 0) {
                    if (!this.level().isClientSide()) {
                        this.getBrain().setMemoryWithExpiry(MemoryModuleType.SNIFF_COOLDOWN, Unit.INSTANCE, cooldown / 2);
                        ((ServerLevel) this.level()).sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getRandomY() + 0.25, this.getZ(), 16, 1, 0.25, 1, 0);
                        this.playEatingSound();
                        this.usePlayerItem(player, hand, player.getItemInHand(hand));
                        player.awardStat(Stats.ITEM_USED.get(player.getItemInHand(hand).getItem()));
                    }
                }
                this.playSound(SoundEvents.SNIFFER_HAPPY, 1.0F, 1.0F);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }
}
