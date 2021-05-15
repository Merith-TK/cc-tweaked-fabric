/*
 * This file is part of the public ComputerCraft API - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2021. This API may be redistributed unmodified and in full only.
 * For help using the API, and posting your mods, visit the forums at computercraft.info.
 */

package dan200.computercraft.api.pocket;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

/**
 * A base class for {@link IPocketUpgrade}s.
 *
 * One does not have to use this, but it does provide a convenient template.
 */
public abstract class AbstractPocketUpgrade implements IPocketUpgrade {
    private final Identifier id;
    private final String adjective;
    private final ItemStack stack;

    protected AbstractPocketUpgrade(Identifier id, ItemConvertible item) {
        this(id, Util.createTranslationKey("upgrade", id) + ".adjective", item);
    }

    protected AbstractPocketUpgrade(Identifier id, String adjective, ItemConvertible item) {
        this.id = id;
        this.adjective = adjective;
        this.stack = new ItemStack(item);
    }

    protected AbstractPocketUpgrade(Identifier id, String adjective, ItemStack stack) {
        this.id = id;
        this.adjective = adjective;
        this.stack = stack;
    }


    @Nonnull
    @Override
    public final Identifier getUpgradeID() {
        return this.id;
    }

    @Nonnull
    @Override
    public final String getUnlocalisedAdjective() {
        return this.adjective;
    }

    @Nonnull
    @Override
    public final ItemStack getCraftingItem() {
        return this.stack;
    }
}
