package io.github.mintynoura.mintyblends.item;

import io.github.mintynoura.mintyblends.item.component.CenserComponent;
import io.github.mintynoura.mintyblends.registry.MintyBlendsComponents;
import io.github.mintynoura.mintyblends.registry.MintyBlendsSoundEvents;
import io.github.mintynoura.mintyblends.util.MintyBlendsTags;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
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
    public final CenserComponent EMPTY = new CenserComponent(0, 5f, List.of(), List.of(), List.of());
    public final int BAR_COLOR = ARGB.colorFromFloat(1.0f, 0.0f, 0.75f, 0.77f);
    private final int MAX_USES;
    public CenserItem(int maxUses, Properties properties) {
        super(properties);
        this.MAX_USES = maxUses;
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack itemStack = super.getDefaultInstance();
        itemStack.set(MintyBlendsComponents.CENSER, EMPTY);
        return itemStack;
    }

    public int getMaxUses() {
        return MAX_USES;
    }

    @Override
    public InteractionResult use(Level level, Player user, InteractionHand hand) {
        ItemStack censer = user.getItemInHand(hand);
        ItemStack flintAndSteel = user.getOffhandItem();
        CenserComponent component = censer.getOrDefault(MintyBlendsComponents.CENSER, EMPTY);

        if (!(flintAndSteel.getItem() instanceof FlintAndSteelItem) || !censer.has(MintyBlendsComponents.CENSER)) {
            return InteractionResult.PASS;
        }
        if (component.uses() == 0) {
            return InteractionResult.PASS;
        }


        float diameter = 2 * component.range();
        List<LivingEntity> entitiesList = level.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(user.position(), diameter, diameter, diameter), livingEntity -> livingEntity.isAlive() && livingEntity != user && !livingEntity.is(MintyBlendsTags.EntityTypes.IGNORES_CENSER));
        for (LivingEntity targetEntity : entitiesList) {
            applyIncense(targetEntity, censer);
        }
        component.decrementUse(censer, component, 1);
        flintAndSteel.hurtAndBreak(1, user, EquipmentSlot.OFFHAND);
        level.playSound(user, user.blockPosition(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
        level.playSound(user, user.blockPosition(), MintyBlendsSoundEvents.ITEM_CENSER_BURN, SoundSource.PLAYERS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);

        level.gameEvent(user, GameEvent.ITEM_INTERACT_FINISH, user.blockPosition());
        if (!level.isClientSide()) {
            ((ServerLevel) level).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, user.getX(), user.getY(0.5), user.getZ(), 8, 0.625, 0.25, 0.625, 0);
            user.awardStat(Stats.ITEM_USED.get(user.getItemInHand(hand).getItem()));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        CenserComponent component = stack.getOrDefault(MintyBlendsComponents.CENSER, EMPTY);
        return component.uses() > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        CenserComponent component = stack.getOrDefault(MintyBlendsComponents.CENSER, EMPTY);
        return Mth.clamp(Math.round(component.uses() * 13.0f / getMaxUses()), 0, 13);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return BAR_COLOR;
    }

    public void applyIncense(LivingEntity entity, ItemStack stack) {
        stack.getOrDefault(MintyBlendsComponents.CENSER, EMPTY).applyIncense(entity, stack);
    }
}
