package io.github.mintynoura.mintyblends.compat.rrv;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewScreen;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import cc.cassian.rrv.common.recipe.rendering.AnimationTicker;
import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.item.component.HerbalBrewComponent;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipe;
import io.github.mintynoura.mintyblends.registry.MintyBlendsComponents;
import io.github.mintynoura.mintyblends.screen.KettleScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KettleBrewingClientRecipe implements ReliableClientRecipe {

    private static final Identifier PROGRESS_TEXTURE = Identifier.fromNamespaceAndPath(MintyBlends.ID, "textures/gui/sprites/container/kettle/progress.png");

    private final Identifier id;
    private final List<SlotContent> ingredients;
    private final SlotContent container;
    private final SlotContent result;
    private final int brewingTime;
    private final AnimationTicker brewingTicker;

    public KettleBrewingClientRecipe(RecipeHolder<KettleBrewingRecipe> recipeHolder) {
        this.id = recipeHolder.id().identifier();
        this.ingredients = new ArrayList<>();
        recipeHolder.value().getIngredients().forEach(ingredient -> this.ingredients.add(SlotContent.of(ingredient)));
        this.container = SlotContent.of(recipeHolder.value().getContainer());
        this.result = SlotContent.of(recipeHolder.value().getResult());
        this.brewingTime = recipeHolder.value().getBrewingTime();
        this.brewingTicker = AnimationTicker.create(Identifier.fromNamespaceAndPath(MintyBlends.ID, "brewing_ticker"), this.brewingTime);
    }

    @Override
    public ReliableClientRecipeType getType() {
        return KettleBrewingClientRecipeType.INSTANCE;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public void bindSlots(RecipeViewMenu.SlotFillContext slotFillContext) {
        for (int i = 0; i < this.ingredients.size() && i < this.getType().getSlotCount() - 1; i++) {
            slotFillContext.bindSlot(i, this.ingredients.get(i));
        }

        slotFillContext.bindSlot(4, this.container);
        slotFillContext.bindSlot(5, this.result);
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
        for (int i = 0; i < this.ingredients.size() && i < this.getType().getSlotCount() - 1; i++) {
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
        return ReliableClientRecipe.super.redirectsAsIngredient(stack) && redirectCheck(stack, this.getIngredients());
    }

    @Override
    public boolean redirectsAsResult(ItemStack stack) {
        return ReliableClientRecipe.super.redirectsAsResult(stack) && redirectCheck(stack, this.getResults());
    }
}