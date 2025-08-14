package io.github.mintynoura.mintyblends.screen;

import io.github.mintynoura.mintyblends.MintyBlends;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class KettleScreen extends HandledScreen<KettleScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(MintyBlends.MOD_ID, "textures/gui/container/kettle.png");
    private static final Identifier PROGRESS_TEXTURE = Identifier.of(MintyBlends.MOD_ID, "textures/gui/sprites/container/kettle/progress.png");
    private static final Identifier LIT = Identifier.of(MintyBlends.MOD_ID, "textures/gui/sprites/container/kettle/lit.png");

    public KettleScreen(KettleScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    public void init() {
        super.init();
        this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        if (handler.isBrewing()) {
            context.drawTexture(RenderLayer::getGuiTextured, PROGRESS_TEXTURE, x + 79, y + 34, 0, 0, handler.getArrowProgress(), 16, 24, 16);
        }
        if (handler.getLitUses() > 0) {
            int currentHeight = handler.getLitProgress() - 1;
            context.drawTexture(RenderLayer::getGuiTextured, LIT, x + 83, y + 50 + 16 - currentHeight, 0, 16 - currentHeight, 16, currentHeight, 16, 16);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        renderBackground(context, mouseX, mouseY, deltaTicks);
        super.render(context, mouseX, mouseY, deltaTicks);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
