package com.mao.sauces.registry;

import com.mao.sauces.Sauces;
import com.mao.sauces.block.blockentity.PlateBlockEntity;
import com.mao.sauces.block.blockentity.SauceMakerBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EntityTypesRegistry {
    public static final BlockEntityType<PlateBlockEntity> PLATE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Sauces.asID("plate_block_entity"),
            FabricBlockEntityTypeBuilder.create(PlateBlockEntity::new, BlocksRegistry.PLATE_BLOCK).build()
    );

    public static final BlockEntityType<SauceMakerBlockEntity> SAUCE_MAKER_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Sauces.asID("sauce_maker_block_entity"),
            FabricBlockEntityTypeBuilder.create(SauceMakerBlockEntity::new, BlocksRegistry.SAUCE_MAKER_BLOCK).build()
    );

    public static void registerModEntities() {
        Sauces.LOGGER.debug("Registering Mod Entities For" + Sauces.MOD_ID);
    }
}
