package com.mao.sauces.client;

import com.mao.sauces.block.blockentity.PlateBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

public class PlateBlockEntityRenderer implements BlockEntityRenderer<PlateBlockEntity> {
    public PlateBlockEntityRenderer(BlockEntityRendererFactory.Context context){}


    @Override
    public void render(PlateBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack stack = entity.getStack(0);

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        int renderPos = (int) entity.getPos().asLong();
        Direction direction = entity.getItemDirection();

        if (!stack.isEmpty()) {
            matrices.push();

            matrices.translate(0.5d, 0.1d, 0.5d);
            float f = -direction.asRotation();
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(f));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0f));
            matrices.scale(0.6f, 0.6f, 0.6f);

            itemRenderer.renderItem(stack, ModelTransformationMode.FIXED, light, overlay, matrices, vertexConsumers, entity.getWorld(), renderPos);

            matrices.pop();
        }
    }
}
