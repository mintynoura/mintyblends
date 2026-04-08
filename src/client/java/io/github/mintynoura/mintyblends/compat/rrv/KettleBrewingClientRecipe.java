package io.github.mintynoura.mintyblends.compat.rrv;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewScreen;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import cc.cassian.rrv.common.recipe.rendering.AnimationTicker;
import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.screen.KettleScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KettleBrewingClientRecipe implements ReliableClientRecipe {

    private static final Identifier PROGRESS_TEXTURE = Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "textures/gui/sprites/container/kettle/progress.png");

    private final List<SlotContent> ingredients;
    private final SlotContent container;
    private final SlotContent result;
    private final int brewingTime;
    private final AnimationTicker brewingTicker;


    public KettleBrewingClientRecipe(KettleBrewingServerRecipe serverRecipe) {
        this.ingredients = new ArrayList<>();

        serverRecipe.getIngredients().forEach(ingredient ->
                ingredients.add(SlotContent.of(ingredient)));

        this.container = SlotContent.of(serverRecipe.getContainer());
        this.result = SlotContent.of(serverRecipe.getResult());
        this.brewingTime = serverRecipe.getBrewingTime();

        this.brewingTicker = AnimationTicker.create(Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "brewing_ticker"), this.brewingTime);
    }

    @Override
    public ReliableClientRecipeType getViewType() {
        return KettleBrewingClientRecipeType.INSTANCE;
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
    public List<AnimationTicker> getAnimationTickers() {
        return List.of(this.brewingTicker);
    }

    @Override
    public void renderRecipe(RecipeViewScreen screen, RecipePosition recipePosition, GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        int brewProgress = Math.round(this.brewingTicker.getProgress() * 24.0F);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, PROGRESS_TEXTURE, 77, 20, 0, 0, brewProgress, 16, 24, 16);
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
}