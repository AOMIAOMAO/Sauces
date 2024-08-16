package com.mao.sauces.common.block.client;

import com.mao.sauces.common.block.blockentity.PlateBlockEntity;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

import java.util.Objects;

public class PlateRenderer implements BlockEntityRenderer<PlateBlockEntity> {
    public PlateRenderer(BlockEntityRendererFactory.Context context){}


    @Override
    public void render(PlateBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        DefaultedList<ItemStack> inventory = entity.getItems();
        int intPos = (int) entity.getPos().asLong();
        ItemStack stack = inventory.get(0);
        if (!stack.isEmpty()) {
            Direction direction = entity.getCachedState().get(HorizontalFacingBlock.FACING).getOpposite();

            matrices.push();

            matrices.translate(0.5, 0.075, 0.5);
            float angle = -direction.asRotation();
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(angle));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
            matrices.scale(0.5f, 0.5f, 0.5f);
            int lightAbove = WorldRenderer.getLightmapCoordinates(Objects.requireNonNull(entity.getWorld()), entity.getPos().up());
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformationMode.FIXED, lightAbove, overlay, matrices, vertexConsumers, entity.getWorld(), intPos);

            matrices.pop();
        }
    }
}
