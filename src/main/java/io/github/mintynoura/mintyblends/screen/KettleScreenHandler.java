package io.github.mintynoura.mintyblends.screen;

import io.github.mintynoura.mintyblends.block.entity.KettleBlockEntity;
import io.github.mintynoura.mintyblends.registry.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class KettleScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final KettleBlockEntity kettleBlockEntity;
    private final PropertyDelegate propertyDelegate;

    public KettleScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(pos), new ArrayPropertyDelegate(2));
    }

    public KettleScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity kettleBlockEntity, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.KETTLE_SCREEN_HANDLER, syncId);
        this.inventory = (Inventory) kettleBlockEntity;
        this.kettleBlockEntity = (KettleBlockEntity) kettleBlockEntity;
        this.propertyDelegate = propertyDelegate;

        this.addSlot(new Slot(inventory, 0, 50, 40));
        this.addSlot(new Slot(inventory, 1, 50, 20));
        this.addSlot(new Slot(inventory, 2, 50, 0));
        this.addSlot(new Slot(inventory, 3, 20, 0));
        this.addSlot(new Slot(inventory, 4, 100, 0));

        int m;
        int l;
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }

        addProperties(propertyDelegate);
    }

    public boolean isBrewing(){
        return propertyDelegate.get(0) > 0;
    }

    public int getArrowProgress() {
        int progress = this.propertyDelegate.get(0);
        int brewTime = this.propertyDelegate.get(1);
        int arrowPixelSize = 24;

        return brewTime != 0 && progress != 0 ? progress * arrowPixelSize / brewTime : 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
