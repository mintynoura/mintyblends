package io.github.mintynoura.mintyblends.compat.eiv;

import de.crafty.eiv.common.api.recipe.IEivRecipeViewType;
import de.crafty.eiv.common.api.recipe.IEivViewRecipe;
import de.crafty.eiv.common.recipe.inventory.RecipeViewMenu;
import de.crafty.eiv.common.recipe.inventory.RecipeViewScreen;
import de.crafty.eiv.common.recipe.inventory.SlotContent;
import de.crafty.eiv.common.recipe.rendering.AnimationTicker;
import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.item.component.HerbalBrewComponent;
import io.github.mintynoura.mintyblends.registry.MintyBlendsComponents;
import io.github.mintynoura.mintyblends.screen.KettleScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KettleBrewingViewRecipe implements IEivViewRecipe {

    private static final Identifier PROGRESS_TEXTURE = Identifier.fromNamespaceAndPath(MintyBlends.ID, "textures/gui/sprites/container/kettle/progress.png");

    private final List<SlotContent> ingredients;
    private final SlotContent container;
    private final SlotContent result;
    private final int brewingTime;
    private final AnimationTicker brewingTicker;


    public KettleBrewingViewRecipe(KettleBrewingServerRecipe serverRecipe) {
        this.ingredients = new ArrayList<>();

        serverRecipe.getIngredients().forEach(ingredient ->
                ingredients.add(SlotContent.of(ingredient)));

        this.container = SlotContent.of(serverRecipe.getContainer());
        this.result = SlotContent.of(serverRecipe.getResult());
        this.brewingTime = serverRecipe.getBrewingTime();

        this.brewingTicker = AnimationTicker.create(Identifier.fromNamespaceAndPath(MintyBlends.ID, "brewing_ticker"), this.brewingTime);
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
        List<SlotContent> list = new ArrayList<>(5);
        list.addAll(this.ingredients);
        list.add(this.container);
        return list;
    }

    @Override
    public List<SlotContent> getResults() {
        return List.of(this.result);
    }

    @Override
    public List<AnimationTicker> getAnimationTickers() {
        return List.of(this.brewingTicker);
    }

    @Override
    public void renderRecipe(RecipeViewScreen screen, RecipePosition recipePosition, GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        int brewProgress = Math.round(this.brewingTicker.getProgress() * 44);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, PROGRESS_TEXTURE, 42, 8, 0, 0, brewProgress, 25, 44, 25);
    }

    @Override
    public boolean supportsItemTransfer() {
        return true;
    }

    @Override
    public List<Class<? extends AbstractContainerScreen<?>>> getTransferClasses() {
        return Collections.singletonList(KettleScreen.class);
    }

    @Override
    public void mapRecipeItems(RecipeTransferMap transferMap, AbstractContainerScreen<?> screen) {
        for (int i = 0; i < this.ingredients.size() && i < this.getViewType().getSlotCount() - 1; i++) {
            transferMap.linkSlots(i, i);
        }
        transferMap.linkSlots(4, 4);
    }


    public boolean redirectCheck(ItemStack stack, List<SlotContent> slotContents) {
        for (SlotContent slotContent : slotContents) {
            for (ItemStack validStack : slotContent.getValidContents()) {
                if (!stack.is(validStack.getItem()))
                    continue;

                if (makeBrewCheck(stack, validStack))
                    return true;
            }
        }

        return false;
    }

    public boolean makeBrewCheck(ItemStack stack1, ItemStack stack2) {
        if (!(stack1.has(MintyBlendsComponents.HERBAL_BREW) && stack2.has(MintyBlendsComponents.HERBAL_BREW)))
            return true;

        return stack1.getOrDefault(MintyBlendsComponents.HERBAL_BREW, HerbalBrewComponent.EMPTY).ingredients().equals(stack2.getOrDefault(MintyBlendsComponents.HERBAL_BREW, HerbalBrewComponent.EMPTY).ingredients());
    }

    @Override
    public boolean redirectsAsIngredient(ItemStack stack) {
        return IEivViewRecipe.super.redirectsAsIngredient(stack) && redirectCheck(stack, this.getIngredients());
    }

    @Override
    public boolean redirectsAsResult(ItemStack stack) {
        return IEivViewRecipe.super.redirectsAsResult(stack) && redirectCheck(stack, this.getResults());
    }
}