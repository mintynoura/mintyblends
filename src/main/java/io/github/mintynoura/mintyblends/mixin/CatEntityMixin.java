package io.github.mintynoura.mintyblends.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.util.ModTags;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.feline.CatSoundVariant;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Cat.class)
public abstract class CatEntityMixin extends TamableAnimal {

    @Shadow
    protected abstract CatSoundVariant.CatSoundSet getSoundSet();

    protected CatEntityMixin(EntityType<? extends Cat> entityType, Level world) {
        super(entityType, world);
    }

    @WrapOperation(method = "lambda$registerGoals$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/tags/TagKey;)Z"))
    private static boolean mintyBlends$addCatnipToGoal(ItemStack instance, TagKey<Item> tag, Operation<Boolean> original) {
        return original.call(instance, tag) || instance.is(ModTags.Items.CAT_LOVED);
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void mintyBlends$catnipInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (player.getItemInHand(hand).is(ModTags.Items.CAT_LOVED)) {
            if (!this.isTame()) {
                if (!this.level().isClientSide()) {
                    this.tame(player);
                    this.setOrderedToSit(true);
                    this.level().broadcastEntityEvent(this, EntityEvent.TAMING_SUCCEEDED);
                }
                cir.setReturnValue(InteractionResult.SUCCESS);
            }

            if (this.isTame() && this.getAttachedOrElse(MintyBlends.CATNIP_COOLDOWN, 0) == 0 && this.isOwnedBy(player)) {
                this.makeSound(this.getSoundSet().purrSound().value());
                if (!this.level().isClientSide()) {
                    this.dropFromGiftLootTable((ServerLevel) this.level(),
                            BuiltInLootTables.CAT_MORNING_GIFT,
                            (world, stack) -> world.addFreshEntity(
                                    new ItemEntity(world, this.getX(), this.getY(), this.getZ(), stack)
                            ));
                    this.usePlayerItem(player, hand, player.getItemInHand(hand));
                    player.awardStat(Stats.ITEM_USED.get(player.getItemInHand(hand).getItem()));
                    ((ServerLevel) this.level()).sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getRandomY() + 0.25, this.getZ(), 5, 0.25, 0, 0.25, 0);
                    this.setAttached(MintyBlends.CATNIP_COOLDOWN, MintyBlends.CONFIG.catCatnipCooldown.value());
                }
                cir.setReturnValue(InteractionResult.SUCCESS);
            }

            if (this.isTame() && (this.getAttachedOrElse(MintyBlends.CATNIP_COOLDOWN, 0) != 0 || !this.isOwnedBy(player))) {
                this.makeSound(this.getSoundSet().purrSound().value());
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void mintyBlends$tickCatnipCooldown(CallbackInfo ci) {
        if (this.hasAttached(MintyBlends.CATNIP_COOLDOWN)) {
            if (this.getAttachedOrElse(MintyBlends.CATNIP_COOLDOWN, 0) != 0) {
                this.setAttached(MintyBlends.CATNIP_COOLDOWN, this.getAttached(MintyBlends.CATNIP_COOLDOWN)-1);
            }
        }
    }
}
