/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2021. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.computercraft.shared.peripheral.printer;

import javax.annotation.Nonnull;

import dan200.computercraft.shared.ComputerCraftRegistry;
import dan200.computercraft.shared.util.SingleIntArray;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ContainerPrinter extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate properties;

    public ContainerPrinter(int id, PlayerInventory player) {
        this(id, player, new SimpleInventory(TilePrinter.SLOTS), new ArrayPropertyDelegate(1));
    }

    private ContainerPrinter(int id, PlayerInventory player, Inventory inventory, PropertyDelegate properties) {
        super(ComputerCraftRegistry.ModContainers.PRINTER, id);
        this.properties = properties;
        this.inventory = inventory;

        this.addProperties(properties);

        // Ink slot
        this.addSlot(new Slot(inventory, 0, 13, 35));

        // In-tray
        for (int x = 0; x < 6; x++) {
            this.addSlot(new Slot(inventory, x + 1, 61 + x * 18, 22));
        }

        // Out-tray
        for (int x = 0; x < 6; x++) {
            this.addSlot(new Slot(inventory, x + 7, 61 + x * 18, 49));
        }

        // Player inv
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlot(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        // Player hotbar
        for (int x = 0; x < 9; x++) {
            this.addSlot(new Slot(player, x, 8 + x * 18, 142));
        }
    }

    public ContainerPrinter(int id, PlayerInventory player, TilePrinter printer) {
        this(id, player, printer, (SingleIntArray) (() -> printer.isPrinting() ? 1 : 0));
    }

    public boolean isPrinting() {
        return this.properties.get(0) != 0;
    }

    @Nonnull
    @Override
    public ItemStack transferSlot(@Nonnull PlayerEntity player, int index) {
        Slot slot = this.slots.get(index);
        if (slot == null || !slot.hasStack()) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = slot.getStack();
        ItemStack result = stack.copy();
        if (index < 13) {
            // Transfer from printer to inventory
            if (!this.insertItem(stack, 13, 49, true)) {
                return ItemStack.EMPTY;
            }
        } else {
            // Transfer from inventory to printer
            if( TilePrinter.isInk( stack ) ) {
                if (!this.insertItem(stack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else //if is paper
            {
                if (!this.insertItem(stack, 1, 13, false)) {
                    return ItemStack.EMPTY;
                }
            }
        }

        if (stack.isEmpty()) {
            slot.setStack(ItemStack.EMPTY);
        } else {
            slot.markDirty();
        }

        if (stack.getCount() == result.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTakeItem(player, stack);
        return result;
    }

    @Override
    public boolean canUse(@Nonnull PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
