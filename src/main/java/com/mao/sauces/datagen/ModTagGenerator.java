package com.mao.sauces.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModTagGenerator extends FabricTagProvider.ItemTagProvider {
    public ModTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    private static final TagKey<Item> TAG_KEY = TagKey.of(RegistryKeys.ITEM, new Identifier("sauces:mushrooms"));

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
       getOrCreateTagBuilder(TAG_KEY)
               .add(Items.BROWN_MUSHROOM)
               .add(Items.RED_MUSHROOM);
    }
}
