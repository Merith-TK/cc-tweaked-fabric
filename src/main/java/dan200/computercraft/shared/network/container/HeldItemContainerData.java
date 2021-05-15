/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2021. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.computercraft.shared.network.container;

import javax.annotation.Nonnull;

import dan200.computercraft.shared.common.ContainerHeldItem;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;

/**
 * Opens a printout GUI based on the currently held item.
 *
 * @see ContainerHeldItem
 * @see dan200.computercraft.shared.media.items.ItemPrintout
 */
public class HeldItemContainerData implements ContainerData {
    private final Hand hand;

    public HeldItemContainerData(Hand hand) {
        this.hand = hand;
    }

    public HeldItemContainerData(PacketByteBuf buffer) {
        this.hand = buffer.readEnumConstant(Hand.class);
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeEnumConstant(this.hand);
    }

    @Nonnull
    public Hand getHand() {
        return this.hand;
    }
}
