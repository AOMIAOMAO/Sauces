package com.mao.sauces.registry;

import com.mao.sauces.Sauces;
import com.mao.sauces.block.PlateBlock;
import com.mao.sauces.block.SauceMakerBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class BlocksRegistry {
    public static final PlateBlock PLATE_BLOCK = (PlateBlock) registerBlocks("plate_block", new PlateBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.BAMBOO).strength(0.0f)));
    public static final SauceMakerBlock SAUCE_MAKER_BLOCK = (SauceMakerBlock) registerBlocks("sauce_maker", new SauceMakerBlock(AbstractBlock.Settings.create().strength(1.5f).sounds(BlockSoundGroup.STONE).nonOpaque().requiresTool()));
    private static Block registerBlocks(String id, Block block) {
        return Registry.register(Registries.BLOCK, Sauces.asID(id), block);
    }

    public static void registerModBlocks() {
        Sauces.LOGGER.debug("Registering Mod Blocks For" + Sauces.MOD_ID);
    }
}
