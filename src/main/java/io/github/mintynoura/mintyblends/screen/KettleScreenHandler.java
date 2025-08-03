package io.github.mintynoura.mintyblends.screen;

import io.github.mintynoura.mintyblends.MintyBlends;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class KettleScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final KettleBlockEntity kettleBlockEntity;
    private final PropertyDelegate propertyDelegate;
    private static final Identifier EMPTY_CONTAINER_SLOT_TEXTURE = Identifier.of(MintyBlends.MOD_ID, "container/kettle/empty_container");


    public KettleScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(pos), new ArrayPropertyDelegate(3));
    }

    public KettleScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity kettleBlockEntity, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.KETTLE_SCREEN_HANDLER, syncId);
        this.inventory = (Inventory) kettleBlockEntity;
        this.kettleBlockEntity = (KettleBlockEntity) kettleBlockEntity;
        this.propertyDelegate = propertyDelegate;

        this.addSlot(new Slot(inventory, 0, 31, 26));
        this.addSlot(new Slot(inventory, 1, 49, 26));
        this.addSlot(new Slot(inventory, 2, 31, 44));
        this.addSlot(new Slot(inventory, 3, 49, 44));
        this.addSlot(new ContainerSlot(inventory, 4, 116, 35));

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

    public int getLitUses() {
        return this.propertyDelegate.get(2);
    }

    public int getArrowProgress() {
        int progress = this.propertyDelegate.get(0);
        int brewTime = this.propertyDelegate.get(1);
        int arrowPixelSize = 24;

        return brewTime != 0 && progress != 0 ? progress * arrowPixelSize / brewTime : 0;
    }

    public int getLitProgress() {
        int litUses = this.propertyDelegate.get(2);
        int litPixelSize = 16;

        return litUses != 0 ? litUses * litPixelSize / KettleBlockEntity.maxLitUses : 0;
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

    static class ContainerSlot extends Slot {
        public ContainerSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public @Nullable Identifier getBackgroundSprite() {
            return EMPTY_CONTAINER_SLOT_TEXTURE;
        }
    }
}
