package io.github.mintynoura.mintyblends.recipe;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.mixin.client.GhostSlotsInvoker;
import io.github.mintynoura.mintyblends.screen.KettleMenu;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

import java.util.List;

public class KettleBrewingRecipeBookComponent extends RecipeBookComponent<KettleMenu> {
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
            Identifier.fromNamespaceAndPath(MintyBlends.ID, "recipe_book/kettle_filter_enabled"),
            Identifier.fromNamespaceAndPath(MintyBlends.ID,"recipe_book/kettle_filter_disabled"),
            Identifier.fromNamespaceAndPath(MintyBlends.ID,"recipe_book/kettle_filter_enabled_highlighted"),
            Identifier.fromNamespaceAndPath(MintyBlends.ID,"recipe_book/kettle_filter_disabled_highlighted")
    );

    public KettleBrewingRecipeBookComponent(KettleMenu menu, List<TabInfo> tabInfos) {
        super(menu, tabInfos);
    }

    @Override
    protected WidgetSprites getFilterButtonTextures() {
        return FILTER_SPRITES;
    }

    @Override
    protected boolean isCraftingSlot(Slot slot) {
        return switch (slot.index) {
            case 0,1,2,3,4 -> true;
            default -> false;
        };
    }

    @Override
    protected void selectMatchingRecipes(RecipeCollection collection, StackedItemContents stackedContents) {
        collection.selectRecipes(stackedContents, display -> display instanceof KettleBrewingRecipeDisplay);
    }

    @Override
    protected Component getRecipeFilterName() {
        return Component.translatableWithFallback("mintyblends.gui.recipebook.toggleRecipes.brewable", "Showing Brewable");
    }

    @Override
    protected void fillGhostRecipe(GhostSlots ghostSlots, RecipeDisplay recipe, ContextMap context) {
        ((GhostSlotsInvoker) ghostSlots).mintyBlends$invokeSetResults(this.menu.getResultSlot(), context, recipe.result());
        if (recipe instanceof KettleBrewingRecipeDisplay kettleRecipe) {
            for (int i = 0; i < kettleRecipe.ingredients().size(); i++) {
                ((GhostSlotsInvoker) ghostSlots).mintyBlends$invokeSetInput(this.menu.getSlot(i), context, kettleRecipe.ingredients().get(i));
            }
            ((GhostSlotsInvoker) ghostSlots).mintyBlends$invokeSetInput(this.menu.getSlot(4), context, kettleRecipe.container());
        }
    }
}
