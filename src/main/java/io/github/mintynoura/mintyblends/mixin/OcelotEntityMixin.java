package io.github.mintynoura.mintyblends.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.registry.ModLootTables;
import io.github.mintynoura.mintyblends.util.ModTags;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OcelotEntity.class)
public abstract class OcelotEntityMixin extends AnimalEntity {

    @Shadow abstract boolean isTrusting();

    @Shadow protected abstract void setTrusting(boolean trusting);

    @Shadow protected abstract void showEmoteParticle(boolean positive);

    protected OcelotEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @WrapOperation(method = "method_58370", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private static boolean mintyBlends$addCatnipToGoal(ItemStack instance, TagKey<Item> tag, Operation<Boolean> original) {
        return original.call(instance, tag) || instance.isIn(ModTags.Items.CAT_LOVED);
    }

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void mintyBlends$catnipInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
       if (player.getStackInHand(hand).isIn(ModTags.Items.CAT_LOVED)) {
           if (!this.isTrusting()) {
               if (!this.getWorld().isClient) {
                   this.setTrusting(true);
                   this.showEmoteParticle(true);
                   this.getWorld().sendEntityStatus(this, EntityStatuses.TAME_OCELOT_SUCCESS);
               }
               cir.setReturnValue(ActionResult.SUCCESS);
           }

           if (this.isTrusting() && this.getAttachedOrElse(MintyBlends.CATNIP_COOLDOWN, 0) == 0 ) {
               this.playSound(SoundEvents.ENTITY_CAT_PURR);
               if (!this.getWorld().isClient) {
                   this.forEachGiftedItem((ServerWorld) this.getWorld(),
                           ModLootTables.OCELOT_GIFT,
                           (world, stack) -> world.spawnEntity(
                                   new ItemEntity(world, this.getX(), this.getY(), this.getZ(), stack)
                           ));
                   this.eat(player, hand, player.getStackInHand(hand));
                   player.incrementStat(Stats.USED.getOrCreateStat(player.getStackInHand(hand).getItem()));
                   ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getRandomBodyY() + 0.25, this.getZ(), 5, 0.25, 0, 0.25, 0);
                   this.setAttached(MintyBlends.CATNIP_COOLDOWN, 6000);
               }
               cir.setReturnValue(ActionResult.SUCCESS);
           }

           if (this.isTrusting() && this.getAttachedOrElse(MintyBlends.CATNIP_COOLDOWN, 0) != 0) {
               this.playSound(SoundEvents.ENTITY_CAT_PURR);
               cir.setReturnValue(ActionResult.SUCCESS);
           }
       }
    }

    @Inject(method = "mobTick", at = @At("HEAD"), cancellable = true)
    private void mintyBlends$tickCatnipCooldown(CallbackInfo ci) {
        if (this.getAttachedOrElse(MintyBlends.CATNIP_COOLDOWN, 0) != 0 && this.getAttached(MintyBlends.CATNIP_COOLDOWN) != null) {
            this.setAttached(MintyBlends.CATNIP_COOLDOWN, this.getAttached(MintyBlends.CATNIP_COOLDOWN)-1);
        }
    }
}
