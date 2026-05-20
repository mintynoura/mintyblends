package io.github.mintynoura.mintyblends.screen;

import io.github.mintynoura.mintyblends.MintyBlends;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class KettleScreen extends AbstractContainerScreen<KettleMenu> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MintyBlends.ID, "textures/gui/container/kettle.png");
    private static final Identifier PROGRESS_TEXTURE = Identifier.fromNamespaceAndPath(MintyBlends.ID, "textures/gui/sprites/container/kettle/progress.png");
    private static final Identifier LIT = Identifier.fromNamespaceAndPath(MintyBlends.ID, "textures/gui/sprites/container/kettle/lit.png");

    public KettleScreen(KettleMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    public void init() {
        super.init();
        this.titleLabelX = (imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        if (menu.isBrewing()) {
            context.blit(RenderPipelines.GUI_TEXTURED, PROGRESS_TEXTURE, x + 79, y + 34, 0, 0, menu.getArrowProgress(), 16, 24, 16);
        }
        if (menu.getLitUses() > 0) {
            int currentHeight = menu.getLitProgress() - 1;
            context.blit(RenderPipelines.GUI_TEXTURED, LIT, x + 83, y + 50 + 16 - currentHeight, 0, 16 - currentHeight, 16, currentHeight, 16, 16);
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks) {
        super.extractBackground(context, mouseX, mouseY, deltaTicks);
        extractBackground(context, mouseX, mouseY, deltaTicks);
        super.extractRenderState(context, mouseX, mouseY, deltaTicks);
        extractTooltip(context, mouseX, mouseY);
    }
}
