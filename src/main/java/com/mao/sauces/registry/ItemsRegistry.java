package com.mao.sauces.registry;

import com.mao.sauces.Sauces;
import com.mao.sauces.item.SaucesItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ItemsRegistry {
    public static final Item PLATE = registerItems("plate", new BlockItem(BlocksRegistry.PLATE_BLOCK, new Item.Settings().maxCount(1)), ItemGroupsRegistry.ITEM_GROUP);
    public static final Item SAUCE_MAKING_MACHINE = registerItems("sauce_making_machine", new BlockItem(BlocksRegistry.SAUCE_MAKING_MACHINE_BLOCK, new Item.Settings()), ItemGroupsRegistry.ITEM_GROUP);

    public static final Item EMPTY_SAUCE_BOTTLE = registerItems("empty_sauce_bottle", new Item(new Item.Settings().maxCount(32)), ItemGroupsRegistry.ITEM_GROUP);
    public static final SaucesItem SALAD_DRESSING = (SaucesItem) registerItems("salad_dressing", new SaucesItem("salad_dressing"), ItemGroupsRegistry.ITEM_GROUP);
    public static final SaucesItem CREAM = (SaucesItem) registerItems("cream", new SaucesItem("cream"), ItemGroupsRegistry.ITEM_GROUP);
    public static final SaucesItem CHOCOLATE_SAUCES = (SaucesItem) registerItems("chocolate_sauce", new SaucesItem("chocolate_sauce"), ItemGroupsRegistry.ITEM_GROUP);
    public static final SaucesItem KETCHUP = (SaucesItem) registerItems("ketchup", new SaucesItem("ketchup"), ItemGroupsRegistry.ITEM_GROUP);
    public static final SaucesItem JAM = (SaucesItem) registerItems("jam", new SaucesItem("jam"), ItemGroupsRegistry.ITEM_GROUP);
    public static final SaucesItem BLACK_PEPPER_SAUCES = (SaucesItem) registerItems("black_pepper_sauce", new SaucesItem("black_pepper_sauce"), ItemGroupsRegistry.ITEM_GROUP);
    public static final SaucesItem MUSHROOM_PASTE = (SaucesItem) registerItems("mushroom_paste", new SaucesItem("mushroom_paste"), ItemGroupsRegistry.ITEM_GROUP);
    public static final SaucesItem PEANUT_BUTTER = (SaucesItem) registerItems("peanut_butter", new SaucesItem("peanut_butter"), ItemGroupsRegistry.ITEM_GROUP);
    public static final SaucesItem CHILLI_SAUCES = (SaucesItem) registerItems("chilli_sauce", new SaucesItem("chilli_sauce"), ItemGroupsRegistry.ITEM_GROUP);

    private static Item registerItems(String id, Item item, RegistryKey<ItemGroup> itemGroup) {
        Item i = Registry.register(Registries.ITEM, new Identifier(Sauces.MOD_ID, id), item);
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.add(i));
        return i;
    }

    public static void registerModItems() {
        Sauces.LOGGER.debug("Registering Mod Items For" + Sauces.MOD_ID);
    }
}
