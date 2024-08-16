package com.mao.sauces;

import com.mao.sauces.common.block.client.PlateRenderer;
import com.mao.sauces.common.block.client.SauceMakerRenderer;
import com.mao.sauces.registry.ModBlockEntityTypes;
import com.mao.sauces.registry.ModBlocks;
import com.mao.sauces.registry.ModEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

@Environment(EnvType.CLIENT)
public class SaucesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SAUCE_MAKER, RenderLayer.getTranslucent());
        BlockEntityRendererFactories.register(ModBlockEntityTypes.PLATE, PlateRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntityTypes.SAUCE_MAKER, SauceMakerRenderer::new);
        ///Event
        ModEvents.registerClientEvents();
    }
}
