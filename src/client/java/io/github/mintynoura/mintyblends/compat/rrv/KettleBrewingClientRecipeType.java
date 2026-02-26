package io.github.mintynoura.mintyblends.compat.rrv;

import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.registry.ModBlocks;
import io.github.mintynoura.mintyblends.registry.ModItems;
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
        return 133;
    }

    @Override
    public int getDisplayHeight() {
        return 61;
    }

    @Override
    public Identifier getGuiTexture() {
        return Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "textures/gui/eiv/kettle.png");
    }

    @Override
    public int getSlotCount() {
        return 6;
    }

    @Override
    public void placeSlots(RecipeViewMenu.SlotDefinition slotDefinition) {
        slotDefinition.addItemSlot(0, 29, 12);
        slotDefinition.addItemSlot(1, 47, 12);
        slotDefinition.addItemSlot(2, 29, 30);
        slotDefinition.addItemSlot(3, 47, 30);
        // container here
        slotDefinition.addItemSlot(4, 112, 21);
        // result here
        slotDefinition.addItemSlot(5, 1, 2);
    }

    @Override
    public Identifier getId() {
        return Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "kettle_brewing");
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.KETTLE);
    }

    @Override
    public List<ItemStack> getCraftReferences() {
        return List.of(new ItemStack(ModBlocks.KETTLE), new ItemStack(ModItems.HERBAL_BREW));
    }
}