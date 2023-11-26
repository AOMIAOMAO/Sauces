package com.mao.sauces.client;

import com.mao.sauces.block.SauceMakingMachineBlock;
import com.mao.sauces.block.blockentity.SauceMakingMachineBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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

import java.util.Objects;

public class SauceMakingMachineBlockEntityRenderer implements BlockEntityRenderer<SauceMakingMachineBlockEntity> {

    public static final ItemRenderer renderItem = MinecraftClient.getInstance().getItemRenderer();

    public SauceMakingMachineBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(SauceMakingMachineBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack stack = entity.getStack(0);
        ItemStack bottle = entity.getBottleItem();

        BlockState state = Objects.requireNonNull(entity.getWorld()).getBlockState(entity.getPos());

        Direction direction = entity.getItemDirection();
        int renderPos = (int) entity.getPos().asLong();

        if (!stack.isEmpty()) {
            int itemRenderCount = this.getModelCount(stack);
            for (int i = 0; i < itemRenderCount; ++i) {
                matrices.push();

                matrices.translate(0.5, 0.275 + 0.03 * (double) (i + 1), 0.5);
                float f = -direction.asRotation();
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(f));
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0f));
                if (entity.isProcessing()) {
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((entity.getProcessTime() + tickDelta) * 150));
                }
                matrices.scale(0.3f, 0.3f, 0.3f);

                renderItem.renderItem(stack, ModelTransformationMode.FIXED, light, overlay, matrices, vertexConsumers, entity.getWorld(), renderPos);

                matrices.pop();
            }
        }

        if (!bottle.isEmpty() && state != Blocks.AIR.getDefaultState()){
            switch (state.get(SauceMakingMachineBlock.FACING)){
                case NORTH -> renderItemForFacing(0.5, 0.15, false, matrices, bottle, vertexConsumers, light, overlay, entity);
                case SOUTH -> renderItemForFacing(0.5, 0.85,false, matrices, bottle, vertexConsumers, light, overlay, entity);
                case WEST -> renderItemForFacing(0.15, 0.5, true, matrices, bottle, vertexConsumers, light, overlay, entity);
                case EAST -> renderItemForFacing(0.85, 0.5, true, matrices, bottle, vertexConsumers, light, overlay, entity);
            }
        }
    }

    protected int getModelCount(ItemStack stack) {
        if (stack.getCount() > 48) {
            return 12;
        } else if (stack.getCount() > 32) {
            return 10;
        } else if (stack.getCount() >= 16) {
            return 8;
        } else if (stack.getCount() >= 6) {
            return 6;
        } else if (stack.getCount() >= 2) {
            return 2;
        } else {
            return 1;
        }
    }

    protected void renderItemForFacing(double x, double z, boolean ew, MatrixStack matrices, ItemStack stack, VertexConsumerProvider vertexConsumers, int light, int overlay, SauceMakingMachineBlockEntity entity){
        matrices.push();

        matrices.translate(x, 0.25, z);
        matrices.scale(0.5f, 0.5f, 0.5f);

        if (ew){
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0f));
        }

        renderItem.renderItem(stack, ModelTransformationMode.FIXED, light, overlay, matrices, vertexConsumers, entity.getWorld(), (int) entity.getPos().asLong());

        matrices.pop();
    }
}
