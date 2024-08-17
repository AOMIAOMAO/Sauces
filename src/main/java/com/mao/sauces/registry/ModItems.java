package com.mao.sauces.registry;

import com.mao.sauces.Sauces;
import com.mao.sauces.common.item.SauceItem;
import com.mao.sauces.common.util.ModSauces;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class ModItems {
    public static final Item PLATE = register("plate", new BlockItem(ModBlocks.PLATE, new Item.Settings()));
    public static final Item SAUCE_MAKER = register("sauce_maker", new BlockItem(ModBlocks.SAUCE_MAKER, new Item.Settings()));

    public static final Item JAM = register("jam", new SauceItem(ModSauces.JAM));
    public static final Item KETCHUP = register("ketchup", new SauceItem(ModSauces.KETCHUP));
    public static final Item MAYONNAISE = register("mayonnaise", new SauceItem(ModSauces.MAYONNAISE));
    public static final Item CHOCOLATE_SAUCE = register("chocolate_sauce", new SauceItem(ModSauces.CHOCOLATE_SAUCE));
    public static final Item CREAMY_PEPPERCORN_SAUCE = register("creamy_peppercorn_sauce", new SauceItem(ModSauces.CREAMY_PEPPERCORN_SAUCE));
    public static final Item CHILLI_SAUCE = register("chilli_sauce", new SauceItem(ModSauces.CHILLI_SAUCE));
    public static final Item CREAM = register("cream", new SauceItem(ModSauces.CREAM));
    public static final Item PEANUT_BUTTER = register("peanut_butter", new SauceItem(ModSauces.PEANUT_BUTTER));

    private static Item register(String id, Item item) {
        Item i =  Items.register(Sauces.id(id), item);
        ItemGroupEvents.modifyEntriesEvent(ModItemGroup.ITEM_GROUP_KEY).register(entries -> entries.add(i));
        return i;
    }

    public static void registerItems() {
        Sauces.LOGGER.debug("Register Mod Items");
    }
}
