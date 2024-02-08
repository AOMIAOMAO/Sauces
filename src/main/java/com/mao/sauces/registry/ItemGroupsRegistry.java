package com.mao.sauces.registry;

import com.mao.sauces.Sauces;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemGroupsRegistry {
    public static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(Sauces.MOD_ID, "main"));

    public static void registerModItemGroups() {
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
                .displayName(Text.translatable("itemGroup.sauces.main"))
                .icon(ItemsRegistry.SAUCE_MAKER::getDefaultStack)
                .build()
        );
    }
}
