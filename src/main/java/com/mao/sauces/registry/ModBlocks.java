package com.mao.sauces.registry;

import com.mao.sauces.Sauces;
import com.mao.sauces.common.block.PlateBlock;
import com.mao.sauces.common.block.SauceMakerBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlocks {
    public static final Block PLATE = register("plate", new PlateBlock());
    public static final Block SAUCE_MAKER = register("sauce_maker", new SauceMakerBlock());

    private static Block register(String id, Block block){
        return Registry.register(Registries.BLOCK, Sauces.id(id), block);
    }

    public static void registerBlocks(){
        Sauces.LOGGER.debug("Register Mod Blocks");
    }
}
