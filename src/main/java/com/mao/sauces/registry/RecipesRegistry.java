package com.mao.sauces.registry;

import com.mao.sauces.Sauces;
import com.mao.sauces.recipe.SauceMakingMachineRecipe;
import com.mao.sauces.recipe.SauceMakingMachineRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RecipesRegistry {

    private static void recipe(RecipeSerializer<?> serializer, RecipeType<?> type, String id) {
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Sauces.MOD_ID, id), type);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Sauces.MOD_ID, id), serializer);
    }

    public static void registerModRecipes(){
        recipe(SauceMakingMachineRecipeSerializer.INSTANCE, SauceMakingMachineRecipe.Type.INSTANCE, "sauce_making_machine");
    }
}
