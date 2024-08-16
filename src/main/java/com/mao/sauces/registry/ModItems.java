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

    public static final Item JAM = registerSauceItem(ModSauces.JAM);

    private static Item registerSauceItem(ModSauces sauces){
        return register(sauces.name(), new SauceItem(sauces));
    }

    private static Item register(String id, Item item) {
        Item i =  Items.register(Sauces.id(id), item);
        ItemGroupEvents.modifyEntriesEvent(ModItemGroup.ITEM_GROUP_KEY).register(entries -> entries.add(i));
        return i;
    }

    public static void registerItems() {
        Sauces.LOGGER.debug("Register Mod Items");
    }
}
