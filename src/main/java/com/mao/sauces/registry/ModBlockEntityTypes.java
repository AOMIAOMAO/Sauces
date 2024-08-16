package com.mao.sauces.registry;

import com.mao.sauces.Sauces;
import com.mao.sauces.common.block.blockentity.PlateBlockEntity;
import com.mao.sauces.common.block.blockentity.SauceMakerBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntityTypes {
    public static final BlockEntityType<PlateBlockEntity> PLATE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Sauces.id("plate"),
            BlockEntityType.Builder.create(PlateBlockEntity::new, ModBlocks.PLATE).build(null)
    );

    public static final BlockEntityType<SauceMakerBlockEntity> SAUCE_MAKER = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Sauces.id("sauce_maker"),
            BlockEntityType.Builder.create(SauceMakerBlockEntity::new, ModBlocks.SAUCE_MAKER).build(null)
    );

    public static void registerBlockEntityTypes(){
        Sauces.LOGGER.debug("Registering Mod BlockEntityTypes");
    }
}
