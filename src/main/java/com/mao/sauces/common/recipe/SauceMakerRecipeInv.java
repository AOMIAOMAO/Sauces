package com.mao.sauces.common.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface SauceMakerRecipeInv extends Inventory {
    @NotNull
    List<ItemStack> getIngredients();

    @NotNull
    ItemStack getContainer();
}
