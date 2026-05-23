package io.github.mintynoura.mintyblends.compat.rrv;

import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.registry.MintyBlendsBlocks;
import io.github.mintynoura.mintyblends.registry.MintyBlendsItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;


import java.util.List;

public class KettleBrewingClientRecipeType implements ReliableClientRecipeType {

    public static final ReliableClientRecipeType INSTANCE = new KettleBrewingClientRecipeType();

    @Override
    public Component getDisplayName() {
        return Component.translatableWithFallback("recipe.mintyblends.kettle_brewing", "Kettle Brewing");
    }

    @Override
    public int getDisplayWidth() {
        return 109;
    }

    @Override
    public int getDisplayHeight() {
        return 51;
    }

    @Override
    public Identifier getGuiTexture() {
        return Identifier.fromNamespaceAndPath(MintyBlends.ID, "textures/gui/recipe/kettle.png");
    }

    @Override
    public int getSlotCount() {
        return 6;
    }

    @Override
    public void placeSlots(RecipeViewMenu.SlotDefinition slotDefinition) {
        slotDefinition.addItemSlot(0, 4, 4);
        slotDefinition.addItemSlot(1, 22, 4);
        slotDefinition.addItemSlot(2, 4, 22);
        slotDefinition.addItemSlot(3, 22, 22);
        // container here
        slotDefinition.addItemSlot(4, 53, 13);
        // result here
        slotDefinition.addItemSlot(5, 89, 13);
    }

    @Override
    public Identifier getId() {
        return Identifier.fromNamespaceAndPath(MintyBlends.ID, "kettle_brewing");
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(MintyBlendsBlocks.KETTLE);
    }

    @Override
    public List<ItemStack> getCraftReferences() {
        return List.of(new ItemStack(MintyBlendsBlocks.KETTLE), new ItemStack(MintyBlendsItems.HERBAL_BREW));
    }
}