/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2021. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.computercraft.shared.util;

import java.util.Collections;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;

/**
 * A {@link BlockEntityType} whose supplier uses itself as an argument.
 *
 * @param <T> The type of the produced tile entity.
 */
public final class FixedPointTileEntityType<T extends BlockEntity> extends BlockEntityType<T> {
    private final Supplier<Block> block;

    private FixedPointTileEntityType(Supplier<Block> block, Supplier<T> builder) {
        super(builder, Collections.emptySet(), null);
        this.block = block;
    }

    public static <T extends BlockEntity> FixedPointTileEntityType<T> create(Supplier<Block> block, Function<BlockEntityType<T>, T> builder) {
        return new FixedPointSupplier<>(block, builder).factory;
    }

    @Override
    public boolean supports(@Nonnull Block block) {
        return block == this.block.get();
    }

    private static final class FixedPointSupplier<T extends BlockEntity> implements Supplier<T> {
        final FixedPointTileEntityType<T> factory;
        private final Function<BlockEntityType<T>, T> builder;

        private FixedPointSupplier(Supplier<Block> block, Function<BlockEntityType<T>, T> builder) {
            this.factory = new FixedPointTileEntityType<>(block, this);
            this.builder = builder;
        }

        @Override
        public T get() {
            return this.builder.apply(this.factory);
        }
    }
}
