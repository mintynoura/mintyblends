package io.github.mintynoura.mintyblends.item;

import io.github.mintynoura.mintyblends.mixin.OcelotEntityInvoker;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class CatnipLeavesItem extends Item {
    public CatnipLeavesItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity instanceof CatEntity && !((CatEntity) entity).isTamed()) {
            if (!entity.getWorld().isClient) {
                ((CatEntity) entity).setTamedBy(user);
                ((CatEntity) entity).setSitting(true);
                entity.playSound(SoundEvents.ENTITY_CAT_PURR);
                entity.getWorld().sendEntityStatus(entity, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
            }
            return ActionResult.SUCCESS;
        }
        if (entity instanceof CatEntity && ((CatEntity) entity).isTamed()) {
            if (!entity.getWorld().isClient) {
                entity.playSound(SoundEvents.ENTITY_CAT_PURR);
            }
            return ActionResult.SUCCESS;
        }
        if (entity instanceof OcelotEntity) {
            if (!((OcelotEntityInvoker) entity).invokeIsTrusting()) {
                if (!entity.getWorld().isClient) {
                    ((OcelotEntityInvoker) entity).invokeSetTrusting(true);
                    ((OcelotEntityInvoker) entity).invokeShowEmoteParticle(true);
                    entity.playSound(SoundEvents.ENTITY_CAT_PURR);
                    entity.getWorld().sendEntityStatus(entity, EntityStatuses.TAME_OCELOT_SUCCESS);
                }
                return ActionResult.SUCCESS;
            }
            if (((OcelotEntityInvoker) entity).invokeIsTrusting()) {
                if (!entity.getWorld().isClient) {
                    entity.playSound(SoundEvents.ENTITY_CAT_PURR);
                }
                return ActionResult.SUCCESS;
            }

        }
        return super.useOnEntity(stack, user, entity, hand);
    }
}
