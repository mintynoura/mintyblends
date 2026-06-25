package io.github.mintynoura.mintyblends.mixin.client;

import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GhostSlots.class)
public interface GhostSlotsInvoker {
    @Invoker("setResult")
    void mintyBlends$invokeSetResults(Slot slot, ContextMap context, SlotDisplay contents);

    @Invoker("setInput")
    void mintyBlends$invokeSetInput(Slot slot, ContextMap context, SlotDisplay contents);
}
