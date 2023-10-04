package com.mao.sauces.client;

import com.mao.sauces.block.blockentity.SauceMakingMachineBlockEntity;
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

public class SauceMakingMachineBlockEntityRenderer implements BlockEntityRenderer<SauceMakingMachineBlockEntity> {
    public SauceMakingMachineBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(SauceMakingMachineBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack stack = entity.getStack(0);

        Direction direction = entity.getItemDirection();
        int renderPos = (int) entity.getPos().asLong();
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

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

                itemRenderer.renderItem(stack, ModelTransformationMode.FIXED, light, overlay, matrices, vertexConsumers, entity.getWorld(), renderPos);

                matrices.pop();
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
            return 3;
        } else {
            return 1;
        }
    }
}
