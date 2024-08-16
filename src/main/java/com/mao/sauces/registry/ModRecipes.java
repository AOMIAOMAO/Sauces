package com.mao.sauces.registry;

import com.mao.sauces.Sauces;
import com.mao.sauces.common.recipe.SauceMakerRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipes {
    private static void recipe(RecipeSerializer<?> serializer, RecipeType<?> type, String id) {
        Registry.register(Registries.RECIPE_TYPE, Sauces.id(id), type);
        Registry.register(Registries.RECIPE_SERIALIZER, Sauces.id(id), serializer);
    }

    public static void registerRecipes() {
        recipe(SauceMakerRecipe.Serializer.INSTANCE, SauceMakerRecipe.Type.INSTANCE, "sauce_maker");
    }
}
