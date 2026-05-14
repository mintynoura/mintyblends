package io.github.mintynoura.mintyblends.item;

import io.github.mintynoura.mintyblends.item.component.HerbalBrewComponent;
import io.github.mintynoura.mintyblends.registry.MintyBlendsComponents;
import java.util.List;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class HerbalBrewItem extends Item {
    public HerbalBrewItem(Properties settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack itemStack = super.getDefaultInstance();
        itemStack.set(MintyBlendsComponents.HERBAL_BREW_COMPONENT, new HerbalBrewComponent(List.of(), List.of(), List.of()));
        return itemStack;
    }
}
