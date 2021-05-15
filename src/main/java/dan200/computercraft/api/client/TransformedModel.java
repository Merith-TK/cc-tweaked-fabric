/*
 * This file is part of the public ComputerCraft API - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2021. This API may be redistributed unmodified and in full only.
 * For help using the API, and posting your mods, visit the forums at computercraft.info.
 */

package dan200.computercraft.api.client;

import java.util.Objects;

import javax.annotation.Nonnull;

import dan200.computercraft.mixin.AffineTransformationAccess;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.AffineTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * A model to render, combined with a transformation matrix to apply.
 */
@Environment (EnvType.CLIENT)
public final class TransformedModel {
    private final BakedModel model;
    private final AffineTransformation matrix;

    public TransformedModel(@Nonnull BakedModel model, @Nonnull AffineTransformation matrix) {
        this.model = Objects.requireNonNull(model);
        this.matrix = Objects.requireNonNull(matrix);
    }

    public TransformedModel(@Nonnull BakedModel model) {
        this.model = Objects.requireNonNull(model);
        this.matrix = AffineTransformation.identity();
    }

    public static TransformedModel of(@Nonnull ModelIdentifier location) {
        BakedModelManager modelManager = MinecraftClient.getInstance()
                                                        .getBakedModelManager();
        return new TransformedModel(modelManager.getModel(location));
    }

    public static TransformedModel of(@Nonnull ItemStack item, @Nonnull AffineTransformation transform) {
        BakedModel model = MinecraftClient.getInstance()
                                          .getItemRenderer()
                                          .getModels()
                                          .getModel(item);
        return new TransformedModel(model, transform);
    }

    @Nonnull
    public BakedModel getModel() {
        return this.model;
    }

    @Nonnull
    public AffineTransformation getMatrix() {
        return this.matrix;
    }

    public void push(MatrixStack matrixStack) {
        matrixStack.push();

        AffineTransformationAccess access = (AffineTransformationAccess) (Object) this.matrix;
        if (access.getTranslation() != null)
            matrixStack.translate(access.getTranslation().getX(), access.getTranslation().getY(), access.getTranslation().getZ());

        matrixStack.multiply(this.matrix.getRotation2());

        if (access.getScale() != null)
            matrixStack.scale(access.getScale().getX(), access.getScale().getY(), access.getScale().getZ());

        if (access.getRotation1() != null)
            matrixStack.multiply(access.getRotation1());
    }
}
