package com.mao.sauces.common.block.client;

import com.mao.sauces.common.block.blockentity.SauceMakerBlockEntity;
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

public class SauceMakerRenderer implements BlockEntityRenderer<SauceMakerBlockEntity> {
    public SauceMakerRenderer(BlockEntityRendererFactory.Context context){}

    @Override
    public void render(SauceMakerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        DefaultedList<ItemStack> inventory = entity.getItems();
        int intPos = (int) entity.getPos().asLong();
        for (int i = 0; i < inventory.size(); ++i) {
            if (i == 3) break;
            ItemStack itemStack = inventory.get(i);
            if (!itemStack.isEmpty()) {
                Direction direction = entity.getCachedState().get(HorizontalFacingBlock.FACING).getOpposite();

                matrices.push();

                matrices.translate(0.5f, 0.25f + 0.03 * (i + 1), 0.5f);
                float angle = -direction.asRotation();
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(angle));
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(entity.isMaking() ? (entity.getRotationTime() *150): 0));
                matrices.scale(0.375f, 0.375f, 0.375f);
                int lightAbove = WorldRenderer.getLightmapCoordinates(Objects.requireNonNull(entity.getWorld()), entity.getPos().up());
                MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformationMode.FIXED, lightAbove, overlay, matrices, vertexConsumers, entity.getWorld(), intPos);

                matrices.pop();
            }
        }

        ItemStack container = inventory.get(3);
        if (!container.isEmpty()) {
            Direction facing = entity.getCachedState().get(HorizontalFacingBlock.FACING);
            Direction.Axis axis = facing.getAxis();
            double x = 0, z = 0;
            boolean ew = false;
            switch (axis) {
                case X:
                    x = facing == Direction.EAST ? 0.92 : 0.08;
                    z = 0.5;
                    ew = true;
                    break;
                case Z:
                    x = 0.5;
                    z = facing == Direction.SOUTH ? 0.92 : 0.08;
                    break;
                default:
            }
            renderItemForFacing(x, z, ew, matrices, container, vertexConsumers, overlay, entity);
        }
    }

    protected void renderItemForFacing(double x, double z, boolean ew, MatrixStack matrices, ItemStack stack, VertexConsumerProvider vertexConsumers, int overlay, SauceMakerBlockEntity entity){
        matrices.push();

        matrices.translate(x, 0.25, z);
        matrices.scale(0.5f, 0.5f, 0.5f);

        if (ew){
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0f));
        }

        int lightAbove = WorldRenderer.getLightmapCoordinates(Objects.requireNonNull(entity.getWorld()), entity.getPos().up());
        MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformationMode.FIXED, lightAbove, overlay, matrices, vertexConsumers, entity.getWorld(), (int) entity.getPos().asLong());

        matrices.pop();
    }
}
