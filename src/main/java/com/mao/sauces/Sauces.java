package com.mao.sauces;

import com.mao.sauces.event.UseOnBlockEvent;
import com.mao.sauces.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sauces implements ModInitializer {
    public static final String MOD_ID = "sauces";
    public static final Logger LOGGER = LoggerFactory.getLogger(Sauces.class);

    @Override
    public void onInitialize() {
        ItemsRegistry.registerModItems();
        ItemGroupsRegistry.registerModItemGroups();
        BlocksRegistry.registerModBlocks();
        EntityTypesRegistry.registerModEntities();
        SoundsRegistry.registerModSounds();
        RecipesRegistry.registerModRecipes();
        VillagersRegistry.registerVillagers();
        VillageTradesRegistry.registerModTrades();

        UseBlockCallback.EVENT.register(new UseOnBlockEvent());

        LOGGER.info("Sauces mod registration completed!");
    }

    public static Identifier asID(String id){
        return new Identifier(MOD_ID, id);
    }
}
