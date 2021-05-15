/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2021. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.computercraft.shared.turtle.items;

import javax.annotation.Nonnull;

import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.shared.ComputerCraftRegistry;
import dan200.computercraft.shared.computer.core.ComputerFamily;
import dan200.computercraft.shared.turtle.blocks.ITurtleTile;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public final class TurtleItemFactory {
    private TurtleItemFactory() {}

    @Nonnull
    public static ItemStack create(ITurtleTile turtle) {
        ITurtleAccess access = turtle.getAccess();

        return create(turtle.getComputerID(),
                      turtle.getLabel(),
                      turtle.getColour(),
                      turtle.getFamily(),
                      access.getUpgrade(TurtleSide.LEFT),
                      access.getUpgrade(TurtleSide.RIGHT),
                      access.getFuelLevel(),
                      turtle.getOverlay());
    }

    @Nonnull
    public static ItemStack create(int id, String label, int colour, ComputerFamily family, ITurtleUpgrade leftUpgrade, ITurtleUpgrade rightUpgrade,
                                   int fuelLevel, Identifier overlay) {
        switch (family) {
        case NORMAL:
            return ComputerCraftRegistry.ModItems.TURTLE_NORMAL.create(id, label, colour, leftUpgrade, rightUpgrade, fuelLevel, overlay);
        case ADVANCED:
            return ComputerCraftRegistry.ModItems.TURTLE_ADVANCED.create(id, label, colour, leftUpgrade, rightUpgrade, fuelLevel, overlay);
        default:
            return ItemStack.EMPTY;
        }
    }
}
