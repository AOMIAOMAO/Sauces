package com.mao.sauces.registry;

import com.mao.sauces.Sauces;
import com.mao.sauces.block.blockentity.PlateBlockEntity;
import com.mao.sauces.block.blockentity.SauceMakingMachineBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EntityTypesRegistry {
    public static final BlockEntityType<PlateBlockEntity> PLATE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(Sauces.MOD_ID, "plate_block_entity"),
            FabricBlockEntityTypeBuilder.create(PlateBlockEntity::new, BlocksRegistry.PLATE_BLOCK).build()
    );

    public static final BlockEntityType<SauceMakingMachineBlockEntity> SAUCE_MAKING_MACHINE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(Sauces.MOD_ID, "sauce_making_machine_block_entity"),
            FabricBlockEntityTypeBuilder.create(SauceMakingMachineBlockEntity::new, BlocksRegistry.SAUCE_MAKING_MACHINE_BLOCK).build()
    );

    public static void registerModEntities(){
        Sauces.LOGGER.debug("Registering Mod Entities For" + Sauces.MOD_ID);
    }
}
