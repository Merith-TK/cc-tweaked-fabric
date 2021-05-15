/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2021. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.computercraft.shared.peripheral.speaker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralTile;
import dan200.computercraft.shared.common.TileGeneric;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TileSpeaker extends TileGeneric implements Tickable, IPeripheralTile {
    public static final int MIN_TICKS_BETWEEN_SOUNDS = 1;

    private final SpeakerPeripheral peripheral;

    public TileSpeaker(BlockEntityType<TileSpeaker> type) {
        super(type);
        this.peripheral = new Peripheral(this);
    }

    @Override
    public void tick() {
        this.peripheral.update();
    }

    @Nonnull
    @Override
    public IPeripheral getPeripheral(Direction side) {
        return this.peripheral;
    }

    private static final class Peripheral extends SpeakerPeripheral {
        private final TileSpeaker speaker;

        private Peripheral(TileSpeaker speaker) {
            this.speaker = speaker;
        }

        @Override
        public World getWorld() {
            return this.speaker.getWorld();
        }

        @Override
        public Vec3d getPosition() {
            BlockPos pos = this.speaker.getPos();
            return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        }

        @Override
        public boolean equals(@Nullable IPeripheral other) {
            return this == other || (other instanceof Peripheral && this.speaker == ((Peripheral) other).speaker);
        }
    }
}
