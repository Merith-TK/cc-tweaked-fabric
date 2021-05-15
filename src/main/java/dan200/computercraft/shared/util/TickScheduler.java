/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2021. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.computercraft.shared.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.MapMaker;
import dan200.computercraft.shared.common.TileGeneric;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * We use this when modems and other peripherals change a block in a different thread.
 */
public final class TickScheduler {
    private static final Set<BlockEntity> toTick = Collections.newSetFromMap(new MapMaker().weakKeys()
                                                                                           .makeMap());

    private TickScheduler() {
    }

    public static void schedule(TileGeneric tile) {
        World world = tile.getWorld();
        if (world != null && !world.isClient) {
            toTick.add(tile);
        }
    }

    public static void tick() {
        Iterator<BlockEntity> iterator = toTick.iterator();
        while (iterator.hasNext()) {
            BlockEntity tile = iterator.next();
            iterator.remove();

            World world = tile.getWorld();
            BlockPos pos = tile.getPos();

            if (world != null && pos != null && world.isChunkLoaded(pos) && world.getBlockEntity(pos) == tile) {
                world.getBlockTickScheduler()
                     .schedule(pos,
                               tile.getCachedState()
                                   .getBlock(),
                               0);
            }
        }
    }
}
