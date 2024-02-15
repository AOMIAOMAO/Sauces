package com.mao.sauces.compat.rei.display;

import com.mao.sauces.compat.rei.SaucesREIPlugin;
import com.mao.sauces.recipe.SauceMakerRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.ItemStack;

import java.util.Collections;

public class SMMREIDisplay extends BasicDisplay {
    private final int inputCount;
    private final int processtime;
    private final EntryIngredient container;

    public SMMREIDisplay(SauceMakerRecipe recipe) {
        super(EntryIngredients.ofIngredients(recipe.getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.getOutput())));
        this.container = EntryIngredients.of(recipe.getBottle());
        this.inputCount = recipe.getInputCount();
        this.processtime = recipe.getProcesstime();
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SaucesREIPlugin.SMMREI_DISPLAY_CATEGORY;
    }

    public int getInputCount() {
        return inputCount;
    }

    public int getProcesstime() {
        return processtime;
    }

    public EntryIngredient getContainer() {
        return container;
    }
}
