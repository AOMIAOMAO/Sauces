package com.mao.sauces;

import com.mao.sauces.client.PlateBlockEntityRenderer;
import com.mao.sauces.client.SauceMakingMachineBlockEntityRenderer;
import com.mao.sauces.registry.BlocksRegistry;
import com.mao.sauces.registry.EntityTypesRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class SaucesClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.CHILLI_CROP_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.SAUCE_MAKING_MACHINE_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.PEANUT_CROP_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.TOMATO_CROP_BLOCK, RenderLayer.getCutout());

        BlockEntityRendererFactories.register(EntityTypesRegistry.PLATE_BLOCK_ENTITY, PlateBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(EntityTypesRegistry.SAUCE_MAKING_MACHINE_BLOCK_ENTITY, SauceMakingMachineBlockEntityRenderer::new);
    }
}
