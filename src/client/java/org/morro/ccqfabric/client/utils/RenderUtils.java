package org.morro.ccqfabric.client.utils;


import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderUtils {

    public static void renderOutline(WorldRenderContext context, BlockPos pos) {
        ClientWorld world = context.world();
        BlockState state = world.getBlockState(pos);

        Color color = Color.BLACK;
        float[] colorComponents = new float[]{color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F};

        float cameraX = (float) context.camera().getPos().getX();
        float cameraY = (float) context.camera().getPos().getY();
        float cameraZ = (float) context.camera().getPos().getZ();

        state.getOutlineShape(world, pos).forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> {
            // We make the cube a tiny bit larger to avoid z fighting
            double zFightingOffset = 0.001;
            Box box = new Box(
                    (float) (pos.getX() + minX - cameraX - zFightingOffset),
                    (float) (pos.getY() + minY - cameraY - zFightingOffset),
                    (float) (pos.getZ() + minZ - cameraZ - zFightingOffset),
                    (float) (pos.getX() + maxX - cameraX + zFightingOffset),
                    (float) (pos.getY() + maxY - cameraY + zFightingOffset),
                    (float) (pos.getZ() + maxZ - cameraZ + zFightingOffset)
            ).offset(context.camera().getPos());

            renderOutlineHelper(context, box, colorComponents, 2f, true);
        });

    }

    public static void renderOutlineHelper(WorldRenderContext context, Box box, float[] colorComponents, float lineWidth, boolean throughWalls) {
        MatrixStack matrices = context.matrixStack();
        Vec3d camera = context.camera().getPos();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();

        RenderSystem.setShader(GameRenderer::getRenderTypeLinesProgram);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.lineWidth(lineWidth);
        RenderSystem.disableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(throughWalls ? GL11.GL_ALWAYS : GL11.GL_LEQUAL);

        assert matrices != null;
        matrices.push();
        matrices.translate(-camera.getX(), -camera.getY(), -camera.getZ());

        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.LINES, VertexFormats.LINES);

        WorldRenderer.drawBox(matrices, buffer, box, colorComponents[0], colorComponents[1], colorComponents[2], colorComponents[3]);

        BufferRenderer.drawWithGlobalProgram(buffer.end());

        matrices.pop();
        RenderSystem.lineWidth(1F);
        RenderSystem.enableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
    }


    public static void renderHitbox(WorldRenderContext context, Entity entity, float red, float green, float blue) {
        // Use the entity's actual bounding box without offsetting
        Box box = entity.getBoundingBox();
        renderOutlineHelper(context, box, new float[] {red, green, blue, 1f}, 1f, true);


    }



}
