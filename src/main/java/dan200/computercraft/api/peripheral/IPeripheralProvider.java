/*
 * This file is part of the public ComputerCraft API - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2021. This API may be redistributed unmodified and in full only.
 * For help using the API, and posting your mods, visit the forums at computercraft.info.
 */

package dan200.computercraft.api.peripheral;

import java.util.Optional;

import javax.annotation.Nonnull;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

/**
 * This interface is used to create peripheral implementations for blocks.
 *
 * If you have a {@link BlockEntity} which acts as a peripheral, you may alternatively expose the {@link IPeripheral} capability.
 *
 * @see dan200.computercraft.api.ComputerCraftAPI#registerPeripheralProvider(IPeripheralProvider)
 */
@FunctionalInterface
public interface IPeripheralProvider {
    /**
     * Produce an peripheral implementation from a block location.
     *
     * @param world The world the block is in.
     * @param pos The position the block is at.
     * @param side The side to get the peripheral from.
     * @return A peripheral, or {@link Optional#empty()} if there is not a peripheral here you'd like to handle.
     * @see dan200.computercraft.api.ComputerCraftAPI#registerPeripheralProvider(IPeripheralProvider)
     */
    @Nonnull
    IPeripheral getPeripheral(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull Direction side);
}
