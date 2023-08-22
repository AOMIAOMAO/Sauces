package com.mao.sauces.registry;

import com.mao.sauces.Sauces;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

public class VillageTradesRegistry {

    public static void registerModTrades(){
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 2, factories -> factories.add((entity, random) -> new TradeOffer(
                new ItemStack(Items.EMERALD, random.nextBetween(2, 3)),
                new ItemStack(ItemsRegistry.CHILLI_SAUCES),
                3, 2, 0.8f
        )));

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.BUTCHER, 2, factories -> factories.add((entity, random) -> new TradeOffer(
                new ItemStack(Items.EMERALD, random.nextBetween(2, 4)),
                new ItemStack(ItemsRegistry.BLACK_PEPPER_SAUCES),
                3, 2, 0.8f
        )));
    }
}
