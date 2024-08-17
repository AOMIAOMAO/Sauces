package com.mao.sauces.registry;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.village.TradeOffers;

import java.util.Random;

public class ModTrades {

   public static void registerTrades(){
       Random random = new Random();
       int price = random.nextInt(3, 4);
       int ePrice = random.nextInt(7, 9);

       TradeOfferHelper.registerVillagerOffers(ModVillagers.SAUCE_CHEF, 1, factories -> {
           factories.add(new TradeOffers.SellItemFactory(ModItems.KETCHUP, price, 1, 6, 4));
           factories.add(new TradeOffers.SellItemFactory(ModItems.CHILLI_SAUCE, price, 1, 6, 4));
       });

       TradeOfferHelper.registerVillagerOffers(ModVillagers.SAUCE_CHEF, 2, factories -> {
           factories.add(new TradeOffers.SellItemFactory(ModItems.CREAMY_PEPPERCORN_SAUCE, price, 1, 6, 6));
           factories.add(new TradeOffers.SellItemFactory(ModItems.PEANUT_BUTTER, price, 1, 6, 6));
       });

       TradeOfferHelper.registerVillagerOffers(ModVillagers.SAUCE_CHEF, 3, factories -> {
           factories.add(new TradeOffers.SellItemFactory(ModItems.SAUCE_MAKER, ePrice, 1, 6, 7));
           factories.add(new TradeOffers.SellItemFactory(ModItems.PLATE, ePrice, 1, 6, 7));
       });
   }
}
