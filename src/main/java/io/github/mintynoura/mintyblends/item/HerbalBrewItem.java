package io.github.mintynoura.mintyblends.item;

import io.github.mintynoura.mintyblends.item.component.HerbalBrewComponent;
import io.github.mintynoura.mintyblends.registry.ModComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class HerbalBrewItem extends Item {
    public HerbalBrewItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack itemStack = super.getDefaultStack();
        itemStack.set(ModComponents.HERBAL_BREW_COMPONENT, new HerbalBrewComponent(List.of(), List.of()));
        return itemStack;
    }
}
