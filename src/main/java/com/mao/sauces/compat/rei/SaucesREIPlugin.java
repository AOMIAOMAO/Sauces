package com.mao.sauces.compat.rei;

import com.mao.sauces.Sauces;
import com.mao.sauces.compat.rei.category.SMMREICategory;
import com.mao.sauces.compat.rei.display.SMMREIDisplay;
import com.mao.sauces.recipe.SauceMakingMachineRecipe;
import com.mao.sauces.registry.ItemsRegistry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;

public class SaucesREIPlugin implements REIClientPlugin {
    public static final CategoryIdentifier<SMMREIDisplay> SMMREI_DISPLAY_CATEGORY = CategoryIdentifier.of(Sauces.MOD_ID, "sauce_making_machine");

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(SauceMakingMachineRecipe.class, SauceMakingMachineRecipe.Type.INSTANCE, SMMREIDisplay::new);
    }

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new SMMREICategory());
        registry.addWorkstations(SMMREI_DISPLAY_CATEGORY, EntryStacks.of(ItemsRegistry.SAUCE_MAKING_MACHINE));
    }
}
