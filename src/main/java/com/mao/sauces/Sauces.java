package com.mao.sauces;

import com.mao.sauces.registry.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sauces implements ModInitializer {
    public static final String MODID = "sauces";
    public static final Logger LOGGER = LoggerFactory.getLogger(Sauces.class);

    @Override
    public void onInitialize() {
        ModItems.registerItems();
        ModBlocks.registerBlocks();
        ModRecipes.registerRecipes();
        ModEvents.registerEvents();
        ModVillagers.registerVillage();
        ModTrades.registerTrades();
        ModEffects.registerEffect();
        ModItemGroup.registerItemGroup();
        ModBlockEntityTypes.registerBlockEntityTypes();
    }

    public static Identifier id(String id){
        return Identifier.of(MODID, id);
    }
}
