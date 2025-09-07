package io.github.mintynoura.mintyblends.compat.eiv;

import de.crafty.eiv.common.api.recipe.IEivRecipeViewType;
import de.crafty.eiv.common.api.recipe.IEivViewRecipe;
import de.crafty.eiv.common.recipe.inventory.RecipeViewMenu;
import de.crafty.eiv.common.recipe.inventory.RecipeViewScreen;
import de.crafty.eiv.common.recipe.inventory.SlotContent;
import de.crafty.eiv.common.recipe.rendering.AnimationTicker;
import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.screen.KettleScreen;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KettleBrewingViewRecipe implements IEivViewRecipe {

    private static final Identifier PROGRESS_TEXTURE = Identifier.of(MintyBlends.MOD_ID, "textures/gui/sprites/container/kettle/progress.png");

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

        this.brewingTicker = AnimationTicker.create(Identifier.of(MintyBlends.MOD_ID, "brewing_ticker"), this.brewingTime);
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
    public List<AnimationTicker> getAnimationTickers() {
        return List.of(this.brewingTicker);
    }

    @Override
    public void renderRecipe(RecipeViewScreen screen, RecipePosition recipePosition, DrawContext guiGraphics, int mouseX, int mouseY, float partialTicks) {
        int brewProgress = Math.round(this.brewingTicker.getProgress() * 24.0F);
        guiGraphics.drawTexture(RenderPipelines.GUI_TEXTURED, PROGRESS_TEXTURE, 77, 20, 0, 0, brewProgress, 16, 24, 16);
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