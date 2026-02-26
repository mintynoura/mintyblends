package io.github.mintynoura.mintyblends.screen;

import io.github.mintynoura.mintyblends.MintyBlends;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class KettleScreen extends AbstractContainerScreen {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "textures/gui/container/kettle.png");
    private static final Identifier PROGRESS_TEXTURE = Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "textures/gui/sprites/container/kettle/progress.png");
    private static final Identifier LIT = Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "textures/gui/sprites/container/kettle/lit.png");

    public KettleScreen(KettleScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    public void init() {
        super.init();
        this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics context, float deltaTicks, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        if (handler.isBrewing()) {
            context.blit(RenderPipelines.GUI_TEXTURED, PROGRESS_TEXTURE, x + 79, y + 34, 0, 0, handler.getArrowProgress(), 16, 24, 16);
        }
        if (handler.getLitUses() > 0) {
            int currentHeight = handler.getLitProgress() - 1;
            context.blit(RenderPipelines.GUI_TEXTURED, LIT, x + 83, y + 50 + 16 - currentHeight, 0, 16 - currentHeight, 16, currentHeight, 16, 16);
        }
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float deltaTicks) {
        renderBackground(context, mouseX, mouseY, deltaTicks);
        super.render(context, mouseX, mouseY, deltaTicks);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
