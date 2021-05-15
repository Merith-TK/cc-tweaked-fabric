/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2021. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.computercraft.client.render;

import dan200.computercraft.shared.ComputerCraftRegistry;
import dan200.computercraft.shared.peripheral.modem.wired.BlockCable;
import dan200.computercraft.shared.peripheral.modem.wired.CableShapes;
import dan200.computercraft.shared.util.WorldUtil;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment (EnvType.CLIENT)
public final class CableHighlightRenderer {
    private CableHighlightRenderer() {
    }

    /**
     * Draw an outline for a specific part of a cable "Multipart".
     */
    public static boolean drawHighlight(MatrixStack stack, VertexConsumer consumer, Entity entity, double d, double e, double f, BlockPos pos,
                                        BlockState state) {
        World world = entity.getEntityWorld();
        Camera info = MinecraftClient.getInstance().gameRenderer.getCamera();

        // We only care about instances with both cable and modem.
        if (state.getBlock() != ComputerCraftRegistry.ModBlocks.CABLE || state.get(BlockCable.MODEM)
                                                                              .getFacing() == null || !state.get(BlockCable.CABLE)) {
            return false;
        }

        VoxelShape shape = WorldUtil.isVecInside(CableShapes.getModemShape(state),
                                                 new Vec3d(d, e, f).subtract(pos.getX(),
                                                                             pos.getY(),
                                                                             pos.getZ())) ? CableShapes.getModemShape(state) : CableShapes.getCableShape(
            state);

        Vec3d cameraPos = info.getPos();
        double xOffset = pos.getX() - cameraPos.getX();
        double yOffset = pos.getY() - cameraPos.getY();
        double zOffset = pos.getZ() - cameraPos.getZ();
        Matrix4f matrix4f = stack.peek()
                                 .getModel();
        shape.forEachEdge((x1, y1, z1, x2, y2, z2) -> {
            consumer.vertex(matrix4f, (float) (x1 + xOffset), (float) (y1 + yOffset), (float) (z1 + zOffset))
                    .color(0, 0, 0, 0.4f)
                    .next();
            consumer.vertex(matrix4f, (float) (x2 + xOffset), (float) (y2 + yOffset), (float) (z2 + zOffset))
                    .color(0, 0, 0, 0.4f)
                    .next();
        });

        return true;
    }
}
