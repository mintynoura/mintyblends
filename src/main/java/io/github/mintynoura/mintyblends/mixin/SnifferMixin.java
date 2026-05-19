package io.github.mintynoura.mintyblends.mixin;

import io.github.mintynoura.mintyblends.item.component.consume_effects.ModifySnifferCooldownConsumeEffect;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sniffer.class)
public abstract class SnifferMixin extends Animal {
    @Shadow protected abstract Sniffer.State getState();

    protected SnifferMixin(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void mintyBlends$modifyCooldownInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        boolean cooldownModified = false;
        if (player.getItemInHand(hand).has(DataComponents.CONSUMABLE) && (this.getState() == Sniffer.State.IDLING || this.getState() == Sniffer.State.SCENTING || this.getState() == Sniffer.State.SNIFFING) && this.getBrain().hasMemoryValue(MemoryModuleType.SNIFF_COOLDOWN)) {
            if (!player.getItemInHand(hand).get(DataComponents.CONSUMABLE).onConsumeEffects().isEmpty()) {
                for (ConsumeEffect consumeEffect : player.getItemInHand(hand).get(DataComponents.CONSUMABLE).onConsumeEffects()) {
                    if (consumeEffect instanceof ModifySnifferCooldownConsumeEffect modifyCooldownEffect) {
                        modifyCooldownEffect.apply(player.level(), player.getItemInHand(hand), ((Sniffer)(Object) this));
                        cooldownModified = true;
                    }
                }
                if (cooldownModified) {
                    this.playEatingSound();
                    this.usePlayerItem(player, hand, player.getItemInHand(hand));
                    player.awardStat(Stats.ITEM_USED.get(player.getItemInHand(hand).getItem()));
                    this.playSound(SoundEvents.SNIFFER_HAPPY, 1.0F, 1.0F);
                    cir.setReturnValue(InteractionResult.SUCCESS_SERVER);
                }
            }
        }
    }
}
