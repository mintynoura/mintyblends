package io.github.mintynoura.mintyblends.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.util.ModTags;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CatEntity.class)
public abstract class CatEntityMixin extends TameableEntity {

    protected CatEntityMixin(EntityType<? extends CatEntity> entityType, World world) {
        super(entityType, world);
    }

    @WrapOperation(method = "method_58365", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private static boolean addCatnipToGoal(ItemStack instance, TagKey<Item> tag, Operation<Boolean> original) {
        return original.call(instance, tag) || instance.isIn(ModTags.Items.CAT_LOVED);
    }

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    public void catnipInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (this.isTamed() && this.getAttachedOrElse(MintyBlends.CATNIP_COOLDOWN, 0) == 0 && this.isOwner(player) && player.getStackInHand(hand).isIn(ModTags.Items.CAT_LOVED)) {
            this.playSound(SoundEvents.ENTITY_CAT_PURR);
            if (!this.getWorld().isClient) {
                this.forEachGiftedItem((ServerWorld) this.getWorld(),
                    LootTables.CAT_MORNING_GIFT_GAMEPLAY,
                    (world, stack) -> world.spawnEntity(
                            new ItemEntity(world, this.getX(), this.getY(), this.getZ(), stack)
                    ));
                this.eat(player, hand, player.getStackInHand(hand));
                this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
                this.setAttached(MintyBlends.CATNIP_COOLDOWN, 6000);
        }

            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tickCatnipCooldown(CallbackInfo ci) {
        if (!(this.getAttachedOrElse(MintyBlends.CATNIP_COOLDOWN, 0) == 0) && this.getAttached(MintyBlends.CATNIP_COOLDOWN) != null) {
            this.setAttached(MintyBlends.CATNIP_COOLDOWN, this.getAttached(MintyBlends.CATNIP_COOLDOWN)-1);
        }
    }
}
