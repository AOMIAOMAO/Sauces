package com.mao.sauces.registry;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;

public class VillageTradesRegistry {
    public static void registerModTrades() {
        int price = 5;

        TradeOfferHelper.registerVillagerOffers(VillagersRegistry.SAUCE_CHEF, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, random.nextBetween(price - 1, price + 1)),
                    new ItemStack(ItemsRegistry.PLATE),
                    12, 4, 0.05f
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, random.nextBetween(price - 1, price + 1)),
                    new ItemStack(ItemsRegistry.SAUCE_MAKER),
                    12, 4, 0.05f));
        });

        TradeOfferHelper.registerVillagerOffers(VillagersRegistry.SAUCE_CHEF, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, random.nextBetween(price, price + 2)),
                    new ItemStack(ItemsRegistry.KETCHUP),
                    12, 8, 0.05f
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, random.nextBetween(price, price + 2)),
                    new ItemStack(ItemsRegistry.CHILLI_SAUCES),
                    12, 8, 0.05f));
        });

        TradeOfferHelper.registerVillagerOffers(VillagersRegistry.SAUCE_CHEF, 3, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, random.nextBetween(price, price + 3)),
                    new ItemStack(ItemsRegistry.BLACK_PEPPER_SAUCES),
                        12, 10, 0.05f
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, random.nextBetween(price, price + 3)),
                    new ItemStack(ItemsRegistry.PEANUT_BUTTER),
                    12, 10, 0.05f));
        });
    }
}
