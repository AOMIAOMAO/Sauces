package com.mao.sauces.registry;

import com.mao.sauces.Sauces;
import com.mao.sauces.item.SaucesItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ItemsRegistry {
    public static final Item SAUCE_MAKER = registerItems("sauce_maker", new BlockItem(BlocksRegistry.SAUCE_MAKER_BLOCK, new Item.Settings()));
    public static final Item PLATE = registerItems("plate", new BlockItem(BlocksRegistry.PLATE_BLOCK, new Item.Settings().maxCount(1)));

    public static final Item EMPTY_SAUCE_BOTTLE = registerItems("empty_sauce_bottle", new Item(new Item.Settings().maxCount(32)));
    public static final SaucesItem SALAD_DRESSING = (SaucesItem) registerItems("salad_dressing", new SaucesItem("salad_dressing", StatusEffects.NIGHT_VISION));
    public static final SaucesItem CREAM = (SaucesItem) registerItems("cream", new SaucesItem("cream", StatusEffects.JUMP_BOOST));
    public static final SaucesItem CHOCOLATE_SAUCES = (SaucesItem) registerItems("chocolate_sauce", new SaucesItem("chocolate_sauce", StatusEffects.REGENERATION));
    public static final SaucesItem KETCHUP = (SaucesItem) registerItems("ketchup", new SaucesItem("ketchup", StatusEffects.SATURATION));
    public static final SaucesItem JAM = (SaucesItem) registerItems("jam", new SaucesItem("jam", StatusEffects.SPEED));
    public static final SaucesItem BLACK_PEPPER_SAUCES = (SaucesItem) registerItems("black_pepper_sauce", new SaucesItem("black_pepper_sauce", StatusEffects.STRENGTH));
    public static final SaucesItem MUSHROOM_PASTE = (SaucesItem) registerItems("mushroom_paste", new SaucesItem("mushroom_paste", StatusEffects.ABSORPTION));
    public static final SaucesItem PEANUT_BUTTER = (SaucesItem) registerItems("peanut_butter", new SaucesItem("peanut_butter", StatusEffects.SLOW_FALLING));
    public static final SaucesItem CHILLI_SAUCES = (SaucesItem) registerItems("chilli_sauce", new SaucesItem("chilli_sauce", StatusEffects.HASTE));
    public static final SaucesItem PITCHER_POD_SAUCE = (SaucesItem) registerItems("pitcher_pod_sauce", new SaucesItem("pitcher_pod_sauce", StatusEffects.WATER_BREATHING));

    private static Item registerItems(String id, Item item) {
        Item i = Registry.register(Registries.ITEM, Sauces.asID(id), item);
        ItemGroupEvents.modifyEntriesEvent(ItemGroupsRegistry.ITEM_GROUP).register(entries -> entries.add(i));
        return i;
    }

    public static void registerModItems() {
        Sauces.LOGGER.debug("Registering Mod Items For" + Sauces.MOD_ID);
    }
}
