package io.github.mintynoura.mintyblends.screen;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.block.entity.KettleBlockEntity;
import io.github.mintynoura.mintyblends.registry.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class KettleMenu extends AbstractContainerMenu {
    private final Container inventory;
    public final KettleBlockEntity kettleBlockEntity;
    private final ContainerData propertyDelegate;
    private static final Identifier EMPTY_CONTAINER_SLOT_TEXTURE = Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "container/kettle/empty_container");


    public KettleMenu(int syncId, Inventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, playerInventory.player.level().getBlockEntity(pos), new SimpleContainerData(3));
    }

    public KettleMenu(int syncId, Inventory playerInventory, BlockEntity kettleBlockEntity, ContainerData propertyDelegate) {
        super(ModMenus.KETTLE_MENU, syncId);
        this.inventory = (Container) kettleBlockEntity;
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

        addDataSlots(propertyDelegate);
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
    public ItemStack quickMoveStack(Player player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.getContainerSize()) {
                if (!this.moveItemStackTo(originalStack, this.inventory.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, this.inventory.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return newStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    static class ContainerSlot extends Slot {
        public ContainerSlot(Container inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public @Nullable Identifier getNoItemIcon() {
            return EMPTY_CONTAINER_SLOT_TEXTURE;
        }
    }
}
