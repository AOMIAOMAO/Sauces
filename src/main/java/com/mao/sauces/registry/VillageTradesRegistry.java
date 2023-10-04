package com.mao.sauces.registry;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

import java.util.Random;

public class VillageTradesRegistry {
    public static void registerModTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.BUTCHER, 2, factories -> factories.add((entity, random) -> new TradeOffer(
                new ItemStack(Items.EMERALD, random.nextBetween(2, 4)),
                new ItemStack(ItemsRegistry.BLACK_PEPPER_SAUCES),
                3, 2, 0.8f
        )));

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> factories.add((entity, random) -> new TradeOffer(
                new ItemStack(Items.EMERALD, random.nextBetween(1, 3)),
                new ItemStack(ItemsRegistry.CHILLI_SEEDS),
                8, 2, 0.8f
        )));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> factories.add((entity, random) -> new TradeOffer(
                new ItemStack(Items.EMERALD, random.nextBetween(1, 3)),
                new ItemStack(ItemsRegistry.TOMATO_SEEDS),
                8, 2, 0.8f
        )));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> factories.add((entity, random) -> new TradeOffer(
                new ItemStack(Items.EMERALD, random.nextBetween(1, 3)),
                new ItemStack(ItemsRegistry.PEANUT_KERNELS),
                8, 2, 0.8f
        )));
    }
}
