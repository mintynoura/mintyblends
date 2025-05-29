package io.github.mintynoura.mintyblends.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MintLeavesItem extends Item {
    public MintLeavesItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ConsumableComponent consumableComponent = stack.get(DataComponentTypes.CONSUMABLE);
        if (!world.isClient) {
            if (user.getFireTicks() > 0) {
                user.extinguishWithSound();
            }
        }
        return consumableComponent != null ? consumableComponent.finishConsumption(world, user, stack) : stack;
    }
}
