package com.mao.sauces;

import com.mao.sauces.client.PlateBlockEntityRenderer;
import com.mao.sauces.client.SauceMakerBlockEntityRenderer;
import com.mao.sauces.registry.BlocksRegistry;
import com.mao.sauces.registry.EntityTypesRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class SaucesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.SAUCE_MAKER_BLOCK, RenderLayer.getTranslucent());

        BlockEntityRendererFactories.register(EntityTypesRegistry.PLATE_BLOCK_ENTITY, PlateBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(EntityTypesRegistry.SAUCE_MAKER_BLOCK_ENTITY, SauceMakerBlockEntityRenderer::new);
    }
}
