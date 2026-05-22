package io.github.mintynoura.mintyblends.screen;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.block.entity.KettleBlockEntity;
import io.github.mintynoura.mintyblends.registry.MintyBlendsMenus;
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
    private final ContainerData containerData;
    private static final Identifier EMPTY_CONTAINER_SLOT_TEXTURE = Identifier.fromNamespaceAndPath(MintyBlends.ID, "container/kettle/empty_container");


    public KettleMenu(int containerId, Inventory inventory, BlockPos pos) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(pos), new SimpleContainerData(3));
    }

    public KettleMenu(int containerId, Inventory inventory, BlockEntity kettleBlockEntity, ContainerData containerData) {
        super(MintyBlendsMenus.KETTLE_MENU, containerId);
        this.inventory = (Container) kettleBlockEntity;
        this.kettleBlockEntity = (KettleBlockEntity) kettleBlockEntity;
        this.containerData = containerData;

        this.addSlot(new Slot(this.inventory, 0, 31, 26));
        this.addSlot(new Slot(this.inventory, 1, 49, 26));
        this.addSlot(new Slot(this.inventory, 2, 31, 44));
        this.addSlot(new Slot(this.inventory, 3, 49, 44));
        this.addSlot(new ContainerSlot(this.inventory, 4, 116, 35));

        int m;
        int l;
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(inventory, m, 8 + m * 18, 142));
        }

        addDataSlots(containerData);
    }

    public boolean isBrewing(){
        return containerData.get(0) > 0;
    }

    public boolean isLit() {
        return this.kettleBlockEntity.isLit();
    }

    public int getArrowProgress() {
        int progress = this.containerData.get(0);
        int brewTime = this.containerData.get(1);
        int arrowPixelSize = 24;

        return brewTime != 0 && progress != 0 ? progress * arrowPixelSize / brewTime : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            if (slotIndex < this.inventory.getContainerSize()) {
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
