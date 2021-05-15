/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2021. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.computercraft.shared.network.server;

import javax.annotation.Nonnull;

import dan200.computercraft.ComputerCraft;
import dan200.computercraft.shared.computer.core.IContainerComputer;
import dan200.computercraft.shared.computer.core.ServerComputer;
import dan200.computercraft.shared.network.NetworkMessage;

import net.minecraft.network.PacketByteBuf;

import net.fabricmc.fabric.api.network.PacketContext;

/**
 * A packet, which performs an action on a {@link ServerComputer}.
 *
 * This requires that the sending player is interacting with that computer via a {@link IContainerComputer}.
 */
public abstract class ComputerServerMessage implements NetworkMessage {
    private int instanceId;

    public ComputerServerMessage(int instanceId) {
        this.instanceId = instanceId;
    }

    public ComputerServerMessage() {
    }

    @Override
    public void toBytes(@Nonnull PacketByteBuf buf) {
        buf.writeVarInt(this.instanceId);
    }

    @Override
    public void fromBytes(@Nonnull PacketByteBuf buf) {
        this.instanceId = buf.readVarInt();
    }

    @Override
    public void handle(PacketContext context) {
        ServerComputer computer = ComputerCraft.serverComputerRegistry.get(this.instanceId);
        if (computer == null) {
            return;
        }

        IContainerComputer container = computer.getContainer(context.getPlayer());
        if (container == null) {
            return;
        }

        this.handle(computer, container);
    }

    protected abstract void handle(@Nonnull ServerComputer computer, @Nonnull IContainerComputer container);
}
