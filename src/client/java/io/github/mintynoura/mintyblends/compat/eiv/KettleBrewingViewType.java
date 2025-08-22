package io.github.mintynoura.mintyblends.compat.eiv;

import de.crafty.eiv.common.api.recipe.IEivRecipeViewType;
import de.crafty.eiv.common.recipe.inventory.RecipeViewMenu;
import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.registry.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class KettleBrewingViewType implements IEivRecipeViewType {

    public static final IEivRecipeViewType INSTANCE = new KettleBrewingViewType();

    @Override
    public Text getDisplayName() {
        return Text.translatableWithFallback("recipe.mintyblends.kettle_brewing", "Kettle Brewing");
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
        return Identifier.of(MintyBlends.MOD_ID, "textures/gui/container/kettle.png");
    }

    @Override
    public int getSlotCount() {
        return 5;
    }

    @Override
    public void placeSlots(RecipeViewMenu.SlotDefinition slotDefinition) {
        slotDefinition.addItemSlot(0, 31, 26);
        slotDefinition.addItemSlot(1, 46, 26);
        slotDefinition.addItemSlot(2, 31, 44);
        slotDefinition.addItemSlot(3, 46, 44);
        // container here
        slotDefinition.addItemSlot(4, 116, 35);
        // result here
        slotDefinition.addItemSlot(5, 131, 35);
    }

    @Override
    public Identifier getId() {
        return Identifier.of(MintyBlends.MOD_ID, "kettle_brewing");
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.KETTLE);
    }

    @Override
    public List<ItemStack> getCraftReferences() {
        return List.of(new ItemStack(ModBlocks.KETTLE));
    }
}
