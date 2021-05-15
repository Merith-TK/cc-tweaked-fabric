/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2021. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.computercraft.shared;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import dan200.computercraft.ComputerCraft;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;

import dan200.computercraft.shared.peripheral.generic.GenericPeripheralProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public final class Peripherals {
    private static final Collection<IPeripheralProvider> providers = new LinkedHashSet<>();

    private Peripherals() {}

    public static synchronized void register(@Nonnull IPeripheralProvider provider) {
        Objects.requireNonNull(provider, "provider cannot be null");
        providers.add(provider);
    }

    @Nullable
    public static IPeripheral getPeripheral(World world, BlockPos pos, Direction side) {
        return World.method_24794(pos) && !world.isClient ? getPeripheralAt(world, pos, side) : null;
    }

    @Nullable
    private static IPeripheral getPeripheralAt(World world, BlockPos pos, Direction side) {
        BlockEntity block = world.getBlockEntity(pos);

        // Try the handlers in order:
        for (IPeripheralProvider peripheralProvider : providers) {
            try {
                IPeripheral peripheral = peripheralProvider.getPeripheral(world, pos, side);
                if (peripheral != null) {
                    return peripheral;
                }
            } catch (Exception e) {
                ComputerCraft.log.error("Peripheral provider " + peripheralProvider + " errored.", e);
            }
        }

        return GenericPeripheralProvider.getPeripheral(world, pos, side);
    }

}
