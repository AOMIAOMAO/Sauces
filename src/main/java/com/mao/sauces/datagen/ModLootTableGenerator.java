package com.mao.sauces.datagen;

import com.mao.sauces.block.crop.ChilliCropBlock;
import com.mao.sauces.registry.BlocksRegistry;
import com.mao.sauces.registry.ItemsRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.predicate.StatePredicate;

public class ModLootTableGenerator extends FabricBlockLootTableProvider {
    protected ModLootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        BlockStatePropertyLootCondition.Builder builder1 = BlockStatePropertyLootCondition.builder(BlocksRegistry.PEANUT_CROP_BLOCK).properties(StatePredicate.Builder.create()
                .exactMatch(ChilliCropBlock.AGE, 5));
        addDrop(BlocksRegistry.PEANUT_CROP_BLOCK, cropDrops(BlocksRegistry.PEANUT_CROP_BLOCK, ItemsRegistry.PEANUT, ItemsRegistry.PEANUT_KERNELS, builder1));
    }
}
