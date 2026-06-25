package io.github.mintynoura.mintyblends.screen;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.recipe.KettleBrewingRecipeBookComponent;
import io.github.mintynoura.mintyblends.registry.MintyBlendsItems;
import io.github.mintynoura.mintyblends.registry.MintyBlendsRecipes;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.SearchRecipeBookCategory;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class KettleScreen extends AbstractRecipeBookScreen<KettleMenu> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MintyBlends.ID, "textures/gui/container/kettle.png");
    private static final Identifier PROGRESS_TEXTURE = Identifier.fromNamespaceAndPath(MintyBlends.ID, "textures/gui/sprites/container/kettle/progress.png");
    private static final Identifier LIT = Identifier.fromNamespaceAndPath(MintyBlends.ID, "textures/gui/sprites/container/kettle/lit.png");
    private static final List<RecipeBookComponent.TabInfo> TABS = List.of(
            new RecipeBookComponent.TabInfo(SearchRecipeBookCategory.MINTYBLENDS_KETTLE_BREWING), new RecipeBookComponent.TabInfo(MintyBlendsItems.HERBAL_BREW, MintyBlendsRecipes.KETTLE_BREWING_RECIPE_CATEGORY)
    );
    public KettleScreen(KettleMenu menu, Inventory inventory, Component title) {
        super(menu, new KettleBrewingRecipeBookComponent(menu, TABS), inventory, title);
    }

    @Override
    public void init() {
        super.init();
        this.titleLabelX = (imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    protected ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + 140, this.height / 2 - 49);
    }

    @Override
    protected boolean isBiggerResultSlot() {
        return false;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int x = this.leftPos;
        int y = this.topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        if (menu.isBrewing()) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, PROGRESS_TEXTURE, x + 69, y + 30, 0, 0, menu.getArrowProgress(), 25, 44, 25);
        }
        if (menu.isLit()) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, LIT, x + 83, y + 58, 0, 0, 10, 12, 10, 12);
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        extractBackground(graphics, mouseX, mouseY, a);
        super.extractRenderState(graphics, mouseX, mouseY, a);
        extractTooltip(graphics, mouseX, mouseY);
    }
}
