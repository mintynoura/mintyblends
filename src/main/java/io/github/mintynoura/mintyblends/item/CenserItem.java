package io.github.mintynoura.mintyblends.item;

import io.github.mintynoura.mintyblends.item.component.CenserComponent;
import io.github.mintynoura.mintyblends.registry.ModComponents;
import io.github.mintynoura.mintyblends.registry.ModSoundEvents;
import io.github.mintynoura.mintyblends.util.ModTags;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class CenserItem extends Item {
    public CenserItem(Properties settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack itemStack = super.getDefaultInstance();
        itemStack.set(ModComponents.CENSER_COMPONENT, new CenserComponent(5f, List.of(), List.of(), List.of(), List.of()));
        return itemStack;
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack censer = user.getItemInHand(hand);
        ItemStack flintAndSteel = user.getOffhandItem();

        if (!(flintAndSteel.getItem() instanceof FlintAndSteelItem) || !censer.has(ModComponents.CENSER_COMPONENT)) {
            return InteractionResult.FAIL;
        }

        CenserComponent component = censer.get(ModComponents.CENSER_COMPONENT);

        float diameter = 2 * component.range();
        List<LivingEntity> entitiesList = world.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(user.position(), diameter, diameter, diameter), livingEntity -> livingEntity.isAlive() && livingEntity != user && !livingEntity.is(ModTags.EntityTypes.IGNORES_CENSER));
        for (LivingEntity targetEntity : entitiesList) {
            applyIncense(targetEntity, censer);
        }
        censer.hurtAndBreak(1, user, EquipmentSlot.MAINHAND);
        flintAndSteel.hurtAndBreak(1, user, EquipmentSlot.OFFHAND);
        world.playSound(user, user.blockPosition(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
        world.playSound(user, user.blockPosition(), ModSoundEvents.ITEM_CENSER_BURN, SoundSource.PLAYERS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);

        world.gameEvent(user, GameEvent.ITEM_INTERACT_FINISH, user.blockPosition());
        if (!world.isClientSide()) {
            ((ServerLevel) world).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, user.getX(), user.getY() + 1, user.getZ(), 8, 0.625, 0.25, 0.625, 0);
            user.awardStat(Stats.ITEM_USED.get(user.getItemInHand(hand).getItem()));
        }
        return InteractionResult.SUCCESS;
    }

    public void applyIncense(LivingEntity entity, ItemStack stack) {
        stack.get(ModComponents.CENSER_COMPONENT).applyIncense(entity, stack);
    }
}
