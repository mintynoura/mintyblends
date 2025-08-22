package io.github.mintynoura.mintyblends.compat.eiv;

import de.crafty.eiv.common.api.recipe.IEivRecipeViewType;
import de.crafty.eiv.common.api.recipe.IEivViewRecipe;
import de.crafty.eiv.common.recipe.inventory.RecipeViewMenu;
import de.crafty.eiv.common.recipe.inventory.SlotContent;
import io.github.mintynoura.mintyblends.screen.KettleScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KettleBrewingViewRecipe implements IEivViewRecipe {

    private final List<SlotContent> ingredients;
    private final SlotContent container;
    private final SlotContent result;
    private final int brewingTime;


    public KettleBrewingViewRecipe(KettleBrewingServerRecipe serverRecipe) {
        this.ingredients = new ArrayList<>();

        serverRecipe.getIngredients().forEach(ingredient ->
                ingredients.add(SlotContent.of(ingredient)));

        this.container = SlotContent.of(serverRecipe.getContainer());
        this.result = SlotContent.of(serverRecipe.getResult());
        this.brewingTime = serverRecipe.getBrewingTime();
    }

    @Override
    public IEivRecipeViewType getViewType() {
        return KettleBrewingViewType.INSTANCE;
    }

    @Override
    public void bindSlots(RecipeViewMenu.SlotFillContext slotFillContext) {
        for (int i = 0; i < ingredients.size() && i < this.getViewType().getSlotCount() - 1; i++) {
            slotFillContext.bindSlot(i, ingredients.get(i));
        }

        slotFillContext.bindSlot(4, container);
        slotFillContext.bindSlot(5, result);
    }

    @Override
    public List<SlotContent> getIngredients() {
        return this.ingredients;
    }

    @Override
    public List<SlotContent> getResults() {
        return List.of(this.result);
    }

    @Override
    public boolean supportsItemTransfer() {
        return true;
    }

    @Override
    public List<Class<? extends HandledScreen<?>>> getTransferClasses() {
        return Collections.singletonList(KettleScreen.class);
    }

    @Override
    public void mapRecipeItems(RecipeTransferMap transferMap, HandledScreen<?> screen) {
        for (int i = 0; i < this.ingredients.size() && i < this.getViewType().getSlotCount() - 1; i++) {
            transferMap.linkSlots(i, i);
        }
        transferMap.linkSlots(4, 4);
    }
}
