package com.mao.sauces.registry;

import com.mao.sauces.Sauces;
import com.mao.sauces.recipe.SauceMakerRecipe;
import com.mao.sauces.recipe.SauceMakerRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RecipesRegistry {

    private static void recipe(RecipeSerializer<?> serializer, RecipeType<?> type, String id) {
        Registry.register(Registries.RECIPE_TYPE, Sauces.asID(id), type);
        Registry.register(Registries.RECIPE_SERIALIZER, Sauces.asID(id), serializer);
    }

    public static void registerModRecipes() {
        recipe(SauceMakerRecipeSerializer.INSTANCE, SauceMakerRecipe.Type.INSTANCE, "sauce_maker");
    }
}
