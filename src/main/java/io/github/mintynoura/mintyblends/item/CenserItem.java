package io.github.mintynoura.mintyblends.item;

import io.github.mintynoura.mintyblends.item.component.CenserComponent;
import io.github.mintynoura.mintyblends.registry.ModComponents;
import io.github.mintynoura.mintyblends.registry.ModSoundEvents;
import io.github.mintynoura.mintyblends.util.ModTags;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class CenserItem extends Item {
    public CenserItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack itemStack = super.getDefaultStack();
        itemStack.set(ModComponents.CENSER_COMPONENT, new CenserComponent(5f, List.of(), List.of(), List.of(), List.of()));
        return itemStack;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack censer = user.getStackInHand(hand);
        ItemStack flintAndSteel = user.getOffHandStack();

        if (!(flintAndSteel.getItem() instanceof FlintAndSteelItem) || censer.getComponents().get(ModComponents.CENSER_COMPONENT) == null) {
            return ActionResult.FAIL;
        }

        float diameter = 2 * censer.getComponents().get(ModComponents.CENSER_COMPONENT).range();
        List<LivingEntity> entitiesList = world.getEntitiesByClass(LivingEntity.class, Box.of(user.getPos(), diameter, diameter, diameter), livingEntity -> livingEntity.isAlive() && livingEntity != user && !livingEntity.getType().isIn(ModTags.EntityTypes.IGNORES_CENSER));
        for (LivingEntity targetEntity : entitiesList) {
            applyIncense(targetEntity, censer);
        }
        censer.damage(1, user, EquipmentSlot.MAINHAND);
        flintAndSteel.damage(1, user, EquipmentSlot.OFFHAND);
        world.playSound(user, user.getBlockPos(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
        world.playSound(user, user.getBlockPos(), ModSoundEvents.ITEM_CENSER_BURN, SoundCategory.PLAYERS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);

        world.emitGameEvent(user, GameEvent.ITEM_INTERACT_FINISH, user.getBlockPos());
        if (!world.isClient) {
            ((ServerWorld) world).spawnParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, user.getX(), user.getY() + 1, user.getZ(), 8, 0.625, 0.25, 0.625, 0);
            user.incrementStat(Stats.USED.getOrCreateStat(user.getStackInHand(hand).getItem()));
        }
        return ActionResult.SUCCESS;
    }

    public void applyIncense(LivingEntity entity, ItemStack stack) {
        stack.getComponents().get(ModComponents.CENSER_COMPONENT).applyIncense(entity, stack);
    }
}
